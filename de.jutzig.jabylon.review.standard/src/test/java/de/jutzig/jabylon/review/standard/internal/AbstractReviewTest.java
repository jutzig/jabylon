package de.jutzig.jabylon.review.standard.internal;

import org.junit.Before;

import de.jutzig.jabylon.common.review.ReviewParticipant;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;

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
