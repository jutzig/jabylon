/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.properties.util;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * can collator compare 2 eobjects if they have a "name" attribute 
 *
 */
public class EObjectNameComparator<T extends EObject> implements Comparator<T>, Serializable {

	private static final long serialVersionUID = 1406111587217218206L;
	private static final Collator COLLATOR = Collator.getInstance(Locale.ENGLISH);
	private String featureName;
	
	public EObjectNameComparator() {
		this("name");
	}
	
	/**
	 * 
	 * @param featureName the feature to compare with
	 */
	public EObjectNameComparator(String featureName) {
		this.featureName = featureName;
	}
	
	@Override
	public int compare(T arg0, T arg1) {
		if(arg0==null)
			return 1;
		EStructuralFeature feature1 = arg0.eClass().getEStructuralFeature(featureName);
		String n1 = (String) (feature1 != null ? arg0.eGet(feature1) : arg0.toString());
		EStructuralFeature feature2 = arg0.eClass().getEStructuralFeature(featureName);
		String n2 = (String) (feature2 != null ? arg1.eGet(feature2) : arg1.toString());
		if(n1==null && n2==null)
			return 0;
		if(n1==null)
			return 1;
		if(n2==null)
			return -1;
		return COLLATOR.compare(n1, n2);
	}

}
