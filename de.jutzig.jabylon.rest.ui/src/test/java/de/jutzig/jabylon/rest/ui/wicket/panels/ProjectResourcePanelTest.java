package de.jutzig.jabylon.rest.ui.wicket.panels;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Point;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.tester.WicketTester;
import org.eclipse.emf.common.util.EList;
import org.junit.Before;
import org.junit.Test;

import de.jutzig.jabylon.properties.PropertiesFactory;
import de.jutzig.jabylon.properties.PropertyFileDescriptor;
import de.jutzig.jabylon.properties.Resolvable;
import de.jutzig.jabylon.properties.Review;

public class ProjectResourcePanelTest {

	
	private ProjectResourcePanel fixture;
	
	@Before
	public void createFixture()
	{
		//needed to setup the Thread Context
		new WicketTester();
		fixture = new ProjectResourcePanel(PropertiesFactory.eINSTANCE.createResourceFolder(), new PageParameters());
	}
	
	/**
	 * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void computeProgressBars() {
		Resolvable mock = mock(Resolvable.class);
		when(mock.getPercentComplete()).thenReturn(15);
		Point result = fixture.computeProgressBars(mock);
		assertEquals(new Point(15,0), result);
	}
	
	
	/**
	 * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
	 */
	@Test
	public void computeProgressBarsDescriptorZeroKeys() {
		PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
		when(mock.getKeys()).thenReturn(0);
		when(mock.getPercentComplete()).thenReturn(0);
		Point result = fixture.computeProgressBars(mock);
		assertEquals(new Point(0,0), result);
	}
	
	
	/**
	 * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
	 */
	@Test
	public void computeProgressBarsDescriptorWithReviews() {
		PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
		when(mock.getKeys()).thenReturn(100);
		when(mock.getPercentComplete()).thenReturn(80);
		@SuppressWarnings("unchecked")
		EList<Review> reviews = mock(EList.class);
		when(reviews.size()).thenReturn(10);
		when(mock.getReviews()).thenReturn(reviews);
		Point result = fixture.computeProgressBars(mock);
		assertEquals("Must subtract the 'dirty' keys from the total percentage",new Point(70,10), result);
	}
	
	/**
	 * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
	 */
	@Test
	public void computeProgressBarsDescriptorComplete() {
		PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
		when(mock.getKeys()).thenReturn(100);
		when(mock.getPercentComplete()).thenReturn(100);
		@SuppressWarnings("unchecked")
		EList<Review> reviews = mock(EList.class);
		when(reviews.size()).thenReturn(10);
		when(mock.getReviews()).thenReturn(reviews);
		Point result = fixture.computeProgressBars(mock);
		assertEquals("Must subtract the 'dirty' keys from the total percentage",new Point(90,10), result);
	}
		
	
	
	/**
	 * also see https://github.com/jutzig/jabylon/wiki/Progress-Bar-TestCases
	 */
	@Test
	public void computeProgressBarsDescriptorWithMasterAndReviews() {
		PropertyFileDescriptor mock = mock(PropertyFileDescriptor.class);
		PropertyFileDescriptor master = mock(PropertyFileDescriptor.class);
		when(mock.getKeys()).thenReturn(50);
		when(master.getKeys()).thenReturn(100);
		when(mock.getMaster()).thenReturn(master);
		when(mock.getPercentComplete()).thenReturn(80);
		@SuppressWarnings("unchecked")
		EList<Review> reviews = mock(EList.class);
		when(reviews.size()).thenReturn(10);
		when(mock.getReviews()).thenReturn(reviews);
		Point result = fixture.computeProgressBars(mock);
		assertEquals("Must subtract the 'dirty' keys from the amount of master keys",new Point(70,10), result);
	}

}
