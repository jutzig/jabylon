package de.jutzig.jabylon.ui.forms;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.vaadin.data.Item;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

@SuppressWarnings("serial")
public class FormPojoExample extends VerticalLayout {

    // the 'POJO' we're editing
    Person person;

    private static final String COMMON_FIELD_WIDTH = "12em";

    public FormPojoExample() {

        person = new Person(); // a person POJO
        BeanItem<Person> personItem = new BeanItem<Person>(person); // item from
                                                                    // POJO

        // Create the Form
        final Form personForm = new Form();
        personForm.setCaption("Personal details");
        personForm.setWriteThrough(false); // we want explicit 'apply'
        personForm.setInvalidCommitted(false); // no invalid values in datamodel

        // FieldFactory for customizing the fields and adding validators
        personForm.setFormFieldFactory(new PersonFieldFactory());
        personForm.setItemDataSource(personItem); // bind to POJO via BeanItem

        // Determines which properties are shown, and in which order:
        personForm.setVisibleItemProperties(Arrays.asList(new String[] {
                "firstName", "lastName", "countryCode", "password",
                "birthdate", "shoesize", "uuid" }));

        // Add form to layout
        addComponent(personForm);

        // The cancel / apply buttons
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setSpacing(true);
        Button discardChanges = new Button("Discard changes",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        personForm.discard();
                    }
                });
        discardChanges.setStyleName(BaseTheme.BUTTON_LINK);
        buttons.addComponent(discardChanges);
        buttons.setComponentAlignment(discardChanges, Alignment.MIDDLE_LEFT);

        Button apply = new Button("Apply", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    personForm.commit();
                } catch (Exception e) {
                    // Ignored, we'll let the Form handle the errors
                }
            }
        });
        buttons.addComponent(apply);
        personForm.getFooter().addComponent(buttons);
        personForm.getFooter().setMargin(false, false, true, true);

        // button for showing the internal state of the POJO
        Button showPojoState = new Button("Show POJO internal state",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        showPojoState();
                    }
                });
        addComponent(showPojoState);
    }

    private void showPojoState() {
        Window.Notification n = new Window.Notification("POJO state",
                Window.Notification.TYPE_TRAY_NOTIFICATION);
        n.setPosition(Window.Notification.POSITION_CENTERED);
        n.setDescription("First name: " + person.getFirstName()
                + "<br/>Last name: " + person.getLastName() + "<br/>Country: "
                + person.getCountryCode() + "<br/>Birthdate: "
                + person.getBirthdate() + "<br/>Shoe size: "
                + person.getShoesize() + "<br/>Password: "
                + person.getPassword() + "<br/>UUID: " + person.getUuid());
        getWindow().showNotification(n);
    }

    private class PersonFieldFactory extends DefaultFieldFactory {

        final ComboBox countries = new ComboBox("Country");

        public PersonFieldFactory() {
            countries.setWidth(COMMON_FIELD_WIDTH);
//            countries.setContainerDataSource(ExampleUtil.getISO3166Container());
//            countries
//                    .setItemCaptionPropertyId(ExampleUtil.iso3166_PROPERTY_NAME);
//            countries.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
            countries.setFilteringMode(ComboBox.FILTERINGMODE_STARTSWITH);
        }

        @Override
        public Field createField(Item item, Object propertyId,
                Component uiContext) {
            Field f;
            if ("countryCode".equals(propertyId)) {
                // filtering ComboBox w/ country names
                return countries;
            } else if ("password".equals(propertyId)) {
                // Create a password field so the password is not shown
                f = createPasswordField(propertyId);
            } else {
                // Use the super class to create a suitable field base on the
                // property type.
                f = super.createField(item, propertyId, uiContext);
            }

            if ("firstName".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setRequired(true);
                tf.setRequiredError("Please enter a First Name");
                tf.setWidth(COMMON_FIELD_WIDTH);
                tf.addValidator(new StringLengthValidator(
                        "First Name must be 3-25 characters", 3, 25, false));
            } else if ("lastName".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setRequired(true);
                tf.setRequiredError("Please enter a Last Name");
                tf.setWidth(COMMON_FIELD_WIDTH);
                tf.addValidator(new StringLengthValidator(
                        "Last Name must be 3-50 characters", 3, 50, false));
            } else if ("password".equals(propertyId)) {
                PasswordField pf = (PasswordField) f;
                pf.setRequired(true);
                pf.setRequiredError("Please enter a password");
                pf.setWidth("10em");
                pf.addValidator(new StringLengthValidator(
                        "Password must be 6-20 characters", 6, 20, false));
            } else if ("shoesize".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setNullRepresentation("");
                tf.setNullSettingAllowed(true);
                tf.addValidator(new IntegerValidator(
                        "Shoe size must be an Integer"));
                tf.setWidth("2em");
            } else if ("uuid".equals(propertyId)) {
                TextField tf = (TextField) f;
                tf.setWidth("20em");
            }

            return f;
        }

        private PasswordField createPasswordField(Object propertyId) {
            PasswordField pf = new PasswordField();
            pf.setCaption(DefaultFieldFactory
                    .createCaptionByPropertyId(propertyId));
            return pf;
        }
    }

    public class Person implements Serializable {

        private String firstName = "";
        private String lastName = "";
        private Date birthdate;
        private Integer shoesize = 42;
        private String password = "";
        private UUID uuid;
        private String countryCode = "";

        public Person() {
            uuid = UUID.fromString("3856c3da-ea56-4717-9f58-85f6c5f560a5");
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public Date getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(Date birthdate) {
            this.birthdate = birthdate;
        }

        public Integer getShoesize() {
            return shoesize;
        }

        public void setShoesize(Integer shoesize) {
            this.shoesize = shoesize;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getCountryCode() {
            return countryCode;
        }

        public void setCountryCode(String countryCode) {
            this.countryCode = countryCode;
        }

    }

    public class IntegerValidator implements Validator {

        private String message;

        public IntegerValidator(String message) {
            this.message = message;
        }

        public boolean isValid(Object value) {
            if (value == null || !(value instanceof String)) {
                return false;
            }
            try {
                Integer.parseInt((String) value);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        public void validate(Object value) throws InvalidValueException {
            if (!isValid(value)) {
                throw new InvalidValueException(message);
            }
        }

    }
}