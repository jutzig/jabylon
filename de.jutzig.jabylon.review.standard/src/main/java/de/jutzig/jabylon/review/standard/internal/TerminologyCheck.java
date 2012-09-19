/**
 * 
 */
package de.jutzig.jabylon.review.standard.internal;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;

import com.google.common.base.Function;
import com.google.common.collect.MapMaker;

import de.jutzig.jabylon.properties.ProjectLocale;
import de.jutzig.jabylon.properties.ProjectVersion;
import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.Property;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Review;
import de.jutzig.jabylon.properties.ReviewState;
import de.jutzig.jabylon.properties.Severity;
import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.ui.review.ReviewParticipant;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public class TerminologyCheck extends AdapterImpl implements ReviewParticipant {

	
	private static final String TERMINOLOGY_DELIMITER = " \t\n\r\f.,;:(){}\"'<>?-";

	private static ThreadLocal<PropertyFileDescriptor> currentDescriptor = new ThreadLocal<PropertyFileDescriptor>(); 	
	
	private Map<Locale, Map<String, Property>> terminologyCache;
	
	/**
	 * 
	 */
	public TerminologyCheck() {

		MapMaker mapMaker = new MapMaker();
		mapMaker.concurrencyLevel(1).softValues().expiration(60, TimeUnit.SECONDS);
		terminologyCache = mapMaker.makeComputingMap(new Function<Locale, Map<String, Property>>() {

			@Override
			public Map<String, Property> apply(Locale from) {
				PropertyFileDescriptor descriptor = currentDescriptor.get();
				if(descriptor==null)
					return Collections.emptyMap();
				PropertyFileDescriptor terminology = getTerminology(descriptor);
				if(terminology==null)
					return Collections.emptyMap();
				return terminology.loadProperties().asMap();
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see de.jutzig.jabylon.ui.review.ReviewParticipant#review(de.jutzig.jabylon.properties.PropertyFileDescriptor, de.jutzig.jabylon.properties.Property, de.jutzig.jabylon.properties.Property)
	 */
	@Override
	public Review review(PropertyFileDescriptor descriptor, Property master, Property slave) {
		Locale variant = descriptor.getVariant();
		currentDescriptor.set(descriptor);
		if(variant==null)
			return null;
		Map<String, Property> terminology = terminologyCache.get(variant);
		return analyze(master, slave, terminology);
		
	}

	
	private Review analyze(Property master, Property slave, Map<String, Property> terminology) {
		
		if(master==null || slave==null)
			return null;
		String masterValue = master.getValue();
		String slaveValue = slave.getValue();
		if(masterValue==null || slaveValue==null)
			return null;
		
		Map<String,String> mustFinds = new HashMap<String,String>(); 
		StringTokenizer tokenizer = new StringTokenizer(masterValue, TERMINOLOGY_DELIMITER);
		while(tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();
			Property property = terminology.get(token);
			if(property!=null)
				mustFinds.put(property.getValue(),property.getKey());			
		}
		if(!mustFinds.isEmpty())
		{
			tokenizer = new StringTokenizer(slaveValue, TERMINOLOGY_DELIMITER);
			while(tokenizer.hasMoreTokens())
			{
				String token = tokenizer.nextToken();
				mustFinds.remove(token);
			}
		}
		if(!mustFinds.isEmpty())
		{
			Entry<String, String> next = mustFinds.entrySet().iterator().next();
			Review review = PropertiesFactory.eINSTANCE.createReview();
			review.setState(ReviewState.OPEN);
			review.setSeverity(Severity.ERROR);
			String message = "Template language contained the term ''{0}'' but the terminology translation ''{1}'' is missing";
			message = MessageFormat.format(message, next.getValue(),next.getKey());
			review.setMessage(message);
			return review;			
		}
		
		return null;
		
	}
	
	private PropertyFileDescriptor getTerminology(PropertyFileDescriptor descriptor)
	{
		Locale locale = descriptor.getProjectLocale().getLocale();
		Workspace workspace = descriptor.getProjectLocale().getParent().getParent().getParent();
		ProjectVersion terminology = workspace.getTerminology();
		if(terminology==null)
			return null;
		ProjectLocale projectLocale = terminology.getProjectLocale(locale);
		if(projectLocale==null)
			return null;
		EList<PropertyFileDescriptor> descriptors = projectLocale.getDescriptors();
		if(descriptors.isEmpty())
			return null;
		return descriptors.get(0);
	}
}
