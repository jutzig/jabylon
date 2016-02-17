/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.rest.ui.wicket.panels;

import java.util.Collection;

import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.properties.Review;
import org.jabylon.rest.ui.model.PropertyPair;

import com.google.common.base.Predicate;

public abstract class PropertyListMode implements Predicate<PropertyPair> {

	public abstract String name();

	@Override
	public final boolean apply(PropertyPair pair) {
		return apply(pair, null);
	}

	@Override
	public final String toString() {
		return name();
	}

	public abstract boolean apply(PropertyPair pair, Collection<Review> reviews);

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof PropertyListMode) {
			PropertyListMode other = (PropertyListMode) obj;
			return name().equals(other.name());
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name().hashCode();
	}

	/**
	 *
	 * @return the review particpant that's used to filter or <code>null</code> for  a standard mode like ALL, FUZZY,...
	 */
	public ReviewParticipant getParticipant() {
		return null;
	}
}
