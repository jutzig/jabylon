package com.vaadin.demo.simpleaddressbook;

import org.eclipse.emf.ecore.EClass;

import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SimpleAddressBook extends Application {

    private static String[] fields = { "First Name", "Last Name", "Company",
            "Mobile Phone", "Work Phone", "Home Phone", "Work Email",
            "Home Email", "Street", "Zip", "City", "State", "Country" };
    private static String[] visibleCols = new String[] { "Last Name",
            "First Name", "Company" };

    private Table contactList = new Table();
    private Form contactEditor = new Form();
    private HorizontalLayout bottomLeftCorner = new HorizontalLayout();
    private Button contactRemovalButton;
    private IndexedContainer addressBookData = createDummyData();

    @Override
    public void init() {
        initLayout();
        initContactAddRemoveButtons();
        initAddressList();
        initFilteringControls();
    }

    private void initLayout() {
        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        setMainWindow(new Window("Address Book", splitPanel));
        VerticalLayout left = new VerticalLayout();
        left.setSizeFull();
        left.addComponent(contactList);
        contactList.setSizeFull();
        left.setExpandRatio(contactList, 1);
        splitPanel.addComponent(left);
        splitPanel.addComponent(contactEditor);
        contactEditor.setCaption("Contact details editor");
        contactEditor.setSizeFull();
        contactEditor.getLayout().setMargin(true);
        contactEditor.setImmediate(true);
        bottomLeftCorner.setWidth("100%");
        left.addComponent(bottomLeftCorner);
    }

    private void initContactAddRemoveButtons() {
        // New item button
        bottomLeftCorner.addComponent(new Button("+",
                new Button.ClickListener() {
                    public void buttonClick(ClickEvent event) {
                        // Add new contact "John Doe" as the first item
                        Object id = ((IndexedContainer) contactList
                              .getContainerDataSource()).addItemAt(0);
                        contactList.getItem(id).getItemProperty("First Name")
                              .setValue("John");
                        contactList.getItem(id).getItemProperty("Last Name")
                              .setValue("Doe");

                        // Select the newly added item and scroll to the item
                        contactList.setValue(id);
                        contactList.setCurrentPageFirstItemId(id);
                    }
                }));

        // Remove item button
        contactRemovalButton = new Button("-", new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                contactList.removeItem(contactList.getValue());
                contactList.select(null);
            }
        });
        contactRemovalButton.setVisible(false);
        bottomLeftCorner.addComponent(contactRemovalButton);
    }

    private void initAddressList() {
        contactList.setContainerDataSource(addressBookData);
        contactList.setVisibleColumns(visibleCols);
        contactList.setSelectable(true);
        contactList.setImmediate(true);
        contactList.addListener(new Property.ValueChangeListener() {
            public void valueChange(ValueChangeEvent event) {
                Object id = contactList.getValue();
                contactEditor.setItemDataSource(id == null ? null : contactList
                        .getItem(id));
                contactRemovalButton.setVisible(id != null);
            }
        });
    }

    private void initFilteringControls() {
        for (final String pn : visibleCols) {
            final TextField sf = new TextField();
            bottomLeftCorner.addComponent(sf);
            sf.setWidth("100%");
            sf.setInputPrompt(pn);
            sf.setImmediate(true);
            bottomLeftCorner.setExpandRatio(sf, 1);
            sf.addListener(new Property.ValueChangeListener() {
                public void valueChange(ValueChangeEvent event) {
                    addressBookData.removeContainerFilters(pn);
                    if (sf.toString().length() > 0 && !pn.equals(sf.toString())) {
                        addressBookData.addContainerFilter(pn, sf.toString(),
                                true, false);
                    }
                    getMainWindow().showNotification(
                            "" + addressBookData.size() + " matches found");
                }
            });
        }
    }

    private static IndexedContainer createDummyData() {

    	
        String[] fnames = { EClass.class.getName(), "Alice", "Joshua", "Mike", "Olivia",
                "Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
                "Lisa", "Marge" };
        String[] lnames = { "Smith", "Gordon", "Simpson", "Brown", "Clavel",
                "Simons", "Verne", "Scott", "Allison", "Gates", "Rowling",
                "Barks", "Ross", "Schneider", "Tate" };

        IndexedContainer ic = new IndexedContainer();

        for (String p : fields) {
            ic.addContainerProperty(p, String.class, "");
        }

        // Create dummy data by randomly combining first and last names
        for (int i = 0; i < 1000; i++) {
            Object id = ic.addItem();
            ic.getContainerProperty(id, "First Name").setValue(
                    fnames[(int) (fnames.length * Math.random())]);
            ic.getContainerProperty(id, "Last Name").setValue(
                    lnames[(int) (lnames.length * Math.random())]);
        }

        return ic;
    }

}

