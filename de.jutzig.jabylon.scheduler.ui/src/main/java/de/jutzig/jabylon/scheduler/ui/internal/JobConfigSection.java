/**
 * 
 */
package de.jutzig.jabylon.scheduler.ui.internal;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Platform;
import org.osgi.service.prefs.Preferences;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.vaadin.data.Validator;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.jutzig.jabylon.properties.Workspace;
import de.jutzig.jabylon.scheduler.SchedulerActivator;
import de.jutzig.jabylon.scheduler.internal.JobRegistry;
import de.jutzig.jabylon.ui.config.AbstractConfigSection;
import de.jutzig.jabylon.ui.config.ConfigSection;
import de.jutzig.jabylon.ui.util.DelegatingPreferences;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public class JobConfigSection extends AbstractConfigSection<Workspace> implements ConfigSection {

	private VerticalLayout layout;

	private boolean dirty;

	/**
	 * 
	 */
	public JobConfigSection() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component createContents() {
		layout = new VerticalLayout();
		layout.setSizeFull();
		layout.setSpacing(true);
		return layout;
	}

	@Override
	public void apply(Preferences config) {
		super.apply(config);
		if (config instanceof DelegatingPreferences) {
			DelegatingPreferences prefs = (DelegatingPreferences) config;
			dirty = prefs.isDirty();

		}
	}

	@Override
	public void commit(Preferences config) {
		if (!dirty)
			return;
		try {
			SchedulerActivator.getDefault().getJobRegistry().updateJobs();
		} catch (SchedulerException e) {
			throw new RuntimeException("Could not apply job configuration (unexpected exception", e);
		}

	}

	@Override
	protected void init(Preferences config) {
		layout.removeAllComponents();
		Preferences root = config.node(JobRegistry.KEY_JOBS);
		IConfigurationElement[] iConfigurationElements = Platform.getExtensionRegistry().getConfigurationElementsFor(
				SchedulerActivator.PLUGIN_ID, "jobs");
		Scheduler scheduler = SchedulerActivator.getDefault().getJobRegistry().getScheduler();
		for (IConfigurationElement iConfigurationElement : iConfigurationElements) {
			String jobID = iConfigurationElement.getAttribute("id");
			layout.addComponent(new JobRow(iConfigurationElement, scheduler, root.node(jobID)));
		}

	}

}

class JobRow extends HorizontalLayout {
	private IConfigurationElement element;
	private Scheduler scheduler;
	private Preferences node;
	private TextField cronField;
	private Label nextExecution;

	public JobRow(IConfigurationElement element, Scheduler scheduler, Preferences node) {
		setSpacing(true);
		this.element = element;
		this.scheduler = scheduler;
		this.node = node;
		createContents();

	}

	private void createContents() {
		Label name = new Label(element.getAttribute("name"));
		name.setWidth(200, UNITS_PIXELS);
		name.setDescription(element.getAttribute("description"));
		addComponent(name);

		cronField = new TextField();
		String cron = node.get(JobRegistry.KEY_SCHEDULE, element.getAttribute("defaultSchedule"));
		if (cron == null || cron.trim().isEmpty())
			cron = "0 0 0 * * ?";
		cronField.setValue(cron);
		cronField.setImmediate(true);
		cronField.addListener(new TextChangeListener() {

			@Override
			public void textChange(TextChangeEvent event) {
				String cron = event.getText();
				node.put(JobRegistry.KEY_SCHEDULE, cron);
				setNextExecutionCaption(cron);
			}
		});
		cronField.addValidator(new Validator() {

			@Override
			public void validate(Object value) throws InvalidValueException {
				try {
					CronScheduleBuilder.cronSchedule((String) value).build();
				} catch (RuntimeException e) {
					throw new InvalidValueException(e.getCause().getMessage());
				}

			}

			@Override
			public boolean isValid(Object value) {
				try {
					CronScheduleBuilder.cronSchedule((String) value);
				} catch (RuntimeException e) {
					return false;
				}
				return true;
			}
		});

		addComponent(cronField);

		boolean active = node.getBoolean(JobRegistry.KEY_ACTIVE, Boolean.valueOf(element.getAttribute("activeByDefault")));
		final Button activeButton = new Button(active ? "Deactivate" : "Activate");
		activeButton.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				boolean active = node.getBoolean(JobRegistry.KEY_ACTIVE, Boolean.valueOf(element.getAttribute("activeByDefault")));
				activeButton.setCaption(!active ? "Deactivate" : "Activate");
				node.putBoolean(JobRegistry.KEY_ACTIVE, !active);
				String cron = node.get(JobRegistry.KEY_SCHEDULE, element.getAttribute("defaultSchedule"));
				if (cron == null || cron.trim().isEmpty())
					cron = "0 0 0 * * ?";
				setNextExecutionCaption(cron);
			}
		});
		addComponent(activeButton);

		Button runNow = new Button("Run now");
		runNow.addListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				try {
					scheduler.triggerJob(new JobKey(element.getAttribute("id")));
				} catch (InvalidRegistryObjectException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		addComponent(runNow);

		nextExecution = new Label();
		setNextExecutionCaption(cron);

		addComponent(nextExecution);

		setExpandRatio(name, 1f);
		setExpandRatio(activeButton, 0f);
		setExpandRatio(cronField, 0f);
		setExpandRatio(runNow, 0f);

	}

	private void setNextExecutionCaption(String cron) {
		boolean active = node.getBoolean(JobRegistry.KEY_ACTIVE, Boolean.valueOf(element.getAttribute("activeByDefault")));
		if (active) {
			try {
				Trigger trigger = scheduler.getTrigger(new TriggerKey(element.getAttribute("id")));
				Date nextFireTime = trigger.getNextFireTime();
				String caption = "Next execution at {0}";
				nextExecution.setCaption(MessageFormat.format(caption, SimpleDateFormat.getDateTimeInstance().format(nextFireTime)));
				return;
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		nextExecution.setCaption("never executed");
	}
}
