package de.jutzig.jabylon.rest.ui.wicket.panels;

import java.util.Collection;

import com.google.common.base.Predicate;

import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.rest.ui.model.PropertyPair;

enum PropertyListMode implements Predicate<PropertyPair> {

	ALL {
		@Override
		public boolean apply(PropertyPair pair, Collection<Review> reviews) {
			return true;
		}
	},
	MISSING

	{
		@Override
		public boolean apply(PropertyPair pair, Collection<Review> reviews) {
			return pair.getOriginal() == null || pair.getTranslated() == null || pair.getOriginal().isEmpty()
					|| pair.getTranslated().isEmpty();
		}
	},
	FUZZY {
		@Override
		public boolean apply(PropertyPair pair, Collection<Review> reviews) {
			if(MISSING.apply(pair,reviews))
				return true;
			return reviews != null && !reviews.isEmpty();  
		}
	};

	
	public abstract boolean apply(PropertyPair pair, Collection<Review> reviews);
	
	public boolean apply(PropertyPair pair){
		return apply(pair,null);
	}
	
	public static PropertyListMode getByName(String name) {
		if (name == null || name.isEmpty())
			return ALL;
		return valueOf(name.toUpperCase());
	}
}