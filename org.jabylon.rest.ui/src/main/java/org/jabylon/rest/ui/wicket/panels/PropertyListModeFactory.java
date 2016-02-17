/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.common.util.PreferencesUtil;
import org.jabylon.properties.Project;
import org.jabylon.properties.Property;
import org.jabylon.properties.Review;
import org.jabylon.rest.ui.model.PropertyPair;
import org.osgi.service.prefs.Preferences;

/**
 * Replaces the old PropertyListMode ENUM.<br>
 * Allows for dynamically adding {@link PropertyListMode}s based on {@link ReviewParticipant}s.<br>
 * The "old" default {@link PropertyListMode}s are still there:<br>
 * {@link #ALL}, {@link #MISSING} and {@link #FUZZY}.<br>
 *
 * @author c.samulski
 */
public final class PropertyListModeFactory {
	/**
	 * No instantiation necessary. No state, purely functional.<br>
	 */
	private PropertyListModeFactory() {}

	/**
	 * Holds the default {@link PropertyListMode}s: {@link: #ALL}, {@link #MISSING} and
	 * {@link #FUZZY}.<br>
	 */
	private static final Map<String, PropertyListMode> MODES = new LinkedHashMap<>();

	/**
	 * Default {@link PropertyListMode} for *ALL*, i.e. no filter at all.<br>
	 */
	public static final PropertyListMode ALL = new PropertyListMode() {
		@Override
		public boolean apply(PropertyPair pair, Collection<Review> reviews) {
			return true;
		}

		@Override
		public String name() {
			return "All";
		}
	};

	/**
	 * Default {@link PropertyListMode} for *MISSING*, i.e. a missing value for either the template,
	 * or the translation {@link Property}.<br>
	 */
	public static final PropertyListMode MISSING = new PropertyListMode() {
		@Override
		public boolean apply(PropertyPair pair, Collection<Review> reviews) {
			return pair.getOriginal() == null || pair.getTranslated() == null || pair.getOriginal().isEmpty()
					|| pair.getTranslated().isEmpty();
		}

		@Override
		public String name() {
			return "Missing";
		}
	};

	/**
	 * Default {@link PropertyListMode} for *FUZZY*. {@link #MISSING} applies, or a {@link Review}
	 * is present for this {@link PropertyPair}.<br>
	 */
	public static final PropertyListMode FUZZY = new PropertyListMode() {
		@Override
		public boolean apply(PropertyPair pair, Collection<Review> reviews) {
			if (MISSING.apply(pair, reviews)) {
				return true;
			}
			return reviews != null && !reviews.isEmpty();
		}

		@Override
		public String name() {
			return "Fuzzy";
		}
	};

	/*
	 * Add defaults. These should always be available.
	 */
	static {
		MODES.put(ALL.name(), ALL);
		MODES.put(MISSING.name(), MISSING);
		MODES.put(FUZZY.name(), FUZZY);
	}

	/**
	 * Creates a {@link PropertyListMode} implementation for the given {@link ReviewParticipant}.<br>
	 */
	public static final PropertyListMode forReviewParticipant(final ReviewParticipant participant) {
		return new PropertyListMode() {

			@Override
			public String name() {
				return participant.getID();
			}

			@Override
			public boolean apply(PropertyPair pair, Collection<Review> reviews) {
				if (reviews == null) {
					return false;
				}
				/* apply call is true if any review for *this* ReviewParticipant exists. */
				for (Review review : reviews) {
					if (review.getReviewType().equals(participant.getReviewType())) {
						return true;
					}
				}
				return false;
			}

			@Override
			public ReviewParticipant getParticipant() {
				return participant;
			}
		};
	}

	/**
	 * Return populated {@link Map} filled with the default {@link PropertyListMode}s AND the passed
	 * {@link ReviewParticipant}s as {@link PropertyListMode}s.<br>
	 *
	 * Note: We subclass {@link LinkedHashMap} to override {@link Map#get(Object)} to allow
	 * returning the default {@link PropertyListModeFactory#ALL} instead of null if
	 * {@link LinkedHashMap#get(Object)} returns null.<br>
	 */
	public static final Map<String, PropertyListMode> allAsMap(final List<ReviewParticipant> participants) {
		return new LinkedHashMap<String, PropertyListMode>() {
			private static final long serialVersionUID = 1L;

			@Override
			public PropertyListMode get(Object key) {
				PropertyListMode mode = super.get(key);
				if (mode == null) {
					return ALL; // default
				}
				return mode;
			}
			/*
			 * Here be initializer. Fill map with defaults and PropertyListModes for the given
			 * ReviewParticipants.
			 */
			{
				putAll(MODES);
				for (ReviewParticipant participant : participants) {
					put(participant.getID(), forReviewParticipant(participant));
				}
			}
		};
	}

	/**
	 * @return populated {@link List} of {@link PropertyListMode} containing the defaults
	 *         {@link #ALL}, {@link #MISSING} and {@link #FUZZY}, plus the ones generated via
	 *         {@link #forReviewParticipant(ReviewParticipant)} for the passed
	 *         {@link ReviewParticipant}s.<br>
	 */
	public static final List<PropertyListMode> all(List<ReviewParticipant> participants) {
		List<PropertyListMode> ret = new ArrayList<>();
		ret.addAll(MODES.values());
		/* Enforce standard sorting. */
		sortReviewParticipants(participants);
		/* Add PropertyListMode implementation for each ReviewParticipant. */
		for (ReviewParticipant participant : participants) {
			ret.add(forReviewParticipant(participant));
		}

		return ret;
	}

	/**
	 * @return populated {@link List} of {@link PropertyListMode} containing the defaults
	 *         {@link #ALL}, {@link #MISSING} and {@link #FUZZY}.<br>
	 */
	public static final List<PropertyListMode> all() {
		List<PropertyListMode> ret = new ArrayList<>();
		ret.addAll(MODES.values());
		return ret;
	}

	/**
	 * For UI Callers we maintain the same sorting of {@link ReviewParticipant}s.<br>
	 */
	private static void sortReviewParticipants(List<ReviewParticipant> participants) {
		Collections.sort(participants, new Comparator<ReviewParticipant>() {
			@Override
			public int compare(ReviewParticipant one, ReviewParticipant two) {
				return one.getReviewType().compareTo(two.getReviewType());
			}
		});
	}

	/**
	 * filters the given participant lists depending on which of them are active for the project in question
	 * @param project
	 * @param participants
	 * @return filtered list of active participants
	 */
    public static List<ReviewParticipant> filterActiveReviews(Project project, List<ReviewParticipant> participants) {
        List<ReviewParticipant> activeParticipants = new ArrayList<ReviewParticipant>();
        Preferences node = PreferencesUtil.scopeFor(project).node(PreferencesUtil.NODE_CHECKS);
        for (ReviewParticipant participant : participants) {
            if (node.getBoolean(participant.getID(), false))
                activeParticipants.add(participant);
        }
        return activeParticipants;
    }
}
