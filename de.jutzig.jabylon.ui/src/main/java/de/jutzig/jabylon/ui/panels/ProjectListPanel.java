package de.jutzig.jabylon.ui.panels;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.breadcrumb.CrumbTrail;
import de.jutzig.jabylon.ui.components.StaticProgressIndicator;
import de.jutzig.jabylon.ui.pages.ProjectDashboard;

public class ProjectListPanel extends VerticalLayout {

	public ProjectListPanel() {
		StaticProgressIndicator indicator = new StaticProgressIndicator();
        indicator.setPercentage(100);
        addComponent(indicator);
        
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(90);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(80);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(70);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(60);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(50);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(40);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(30);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(20);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(10);
        addComponent(indicator);
        
        indicator = new StaticProgressIndicator();
        indicator.setPercentage(0);
        addComponent(indicator);
		
	}
	

}
