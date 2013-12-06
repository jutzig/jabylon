package org.jabylon.review.standard.internal;

import org.junit.Before;

import org.jabylon.common.review.ReviewParticipant;
import org.jabylon.properties.PropertiesFactory;
import org.jabylon.properties.Property;

public abstract class AbstractReviewTest {

    private ReviewParticipant fixture;

    public AbstractReviewTest() {
        super();
    }


    public abstract ReviewParticipant createFixture();

    @Before
    public void setup() {
        fixture = createFixture();
    }

    public ReviewParticipant getFixture() {
        return fixture;
    }

    protected Property createProperty(String key, String value) {
        Property property = PropertiesFactory.eINSTANCE.createProperty();
        property.setKey(key);
        property.setValue(value);
        return property;
    }

}
