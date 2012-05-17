package de.jutzig.jabylon.ui.memento;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;

import com.vaadin.ui.Table;

public class MementoUtil {

	
	private static final String SORT_PROPERTY = "sort.property";
	private static final String SORT_ORDER = "sort.order";
	private static final String FIRST_ITEM = "first.item";
	
	public static void storeTable(Memento memento, Table table)
	{
		String value = retrieveString(table.getSortContainerPropertyId());
//		memento.put(SORT_PROPERTY, table.getSortContainerPropertyId());
	}
	
	
	private static String retrieveString(Object sortContainerPropertyId) {
		if (sortContainerPropertyId instanceof String) {
			return (String) sortContainerPropertyId;
		}
		if (sortContainerPropertyId instanceof CDOObject) {
			CDOObject cdoObject = (CDOObject) sortContainerPropertyId;
			CDOID cdoID = cdoObject.cdoID();
			StringBuilder builder = new StringBuilder();
			CDOIDUtil.write(builder, cdoID);
			return builder.toString();
			
		}
		return null;
	}


	public static void restoreTable(Memento memento, Table table)
	{
//		memento.put(key, value)
	}
	
}
