/**
 * (C) Copyright 2013 Jabylon (http://www.jabylon.org) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.jabylon.common.review;

import org.jabylon.properties.Property;
import org.jabylon.properties.PropertyFileDescriptor;
import org.jabylon.properties.Review;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface ReviewParticipant {

    public Review review(PropertyFileDescriptor descriptor, Property master, Property slave);

    String PROPERTY_ID = "id";

    String getID();

    String getDescription();

    String getName();

	String getReviewType();
}
