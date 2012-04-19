package de.jutzig.jabylon.ui.breadcrumb;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.Reindeer;

import de.jutzig.jabylon.ui.applications.MainDashboard;
import de.jutzig.jabylon.ui.config.internal.DynamicConfigPage;

@SuppressWarnings("serial")
public class BreadCrumbImpl extends CustomComponent implements ClickListener,
        BreadCrumb {
    HorizontalLayout layout;

    private List<Button> parts;
    private boolean ignoreDirty;
    private Deque<String> segmentList;
    private List<WeakReference<CrumbListener>> listeners;

    public BreadCrumbImpl() {
        listeners = new ArrayList<WeakReference<CrumbListener>>(1);
        parts = new ArrayList<Button>();
        layout = new HorizontalLayout();
        layout.setSpacing(true);
        segmentList = new ArrayDeque<String>();
        setCompositionRoot(layout);
        setStyleName("breadcrumbs");
        setPath((String[]) null);
    }

    public void setPath(String... segments) {
        // could be optimized: always builds path from scratch
        layout.removeAllComponents();
        CrumbTrail currentTrail = MainDashboard.getCurrent();
        // home
        Button link = addEntry(currentTrail);
        parts.clear();
        parts.add(link);
        walkTo(segments);

    }

    private Button addEntry(CrumbTrail trail) {
        if (!parts.isEmpty()) {
            layout.addComponent(new Label("&raquo;", Label.CONTENT_XHTML));
        }
        Button link = new Button();
        link.setCaption(trail.getTrailCaption());
        link.addListener(this);
        link.setStyleName(Reindeer.BUTTON_LINK);
        link.setData(trail);
        layout.addComponent(link);
        parts.add(link);
        return link;
    }

    @Override
    public void buttonClick(ClickEvent event) {
        int steps = parts.size();
        for (Button button : parts) {
            steps--;
            if (button == event.getButton()) {
                goBack(steps);
                break;
            }
        }

    }

    @Override
    public void goBack() {
        goBack(1);
    }

    private boolean checkDirty() {
        CrumbTrail trail = currentCrumb();
        if(ignoreDirty)
        {
            ignoreDirty = false;
            return false;
        }
        if (trail.isDirty()) {
            final Window subwindow = new Window("Unsafed Changes");
            // ...and make it modal
            subwindow.setModal(true);

            // Configure the windws layout; by default a VerticalLayout
            VerticalLayout layout = (VerticalLayout) subwindow.getContent();
            layout.setMargin(true);
            layout.setSpacing(true);

            // Add some content; a label and a close-button
            Label message = new Label("There are unsafed modifications that will be lost if you proceed.\n Do still you want to proceed?");
            subwindow.addComponent(message);

            Button cancel = new Button("Cancel", new Button.ClickListener() {
                // inline click-listener
                public void buttonClick(ClickEvent event) {
                    // close the window by removing it from the parent window
                    ignoreDirty = false;
                    (subwindow.getParent()).removeWindow(subwindow);
                }
            });

            Button ok = new Button("OK", new Button.ClickListener() {
                // inline click-listener
                public void buttonClick(ClickEvent event) {
                    // close the window by removing it from the parent window
                    ignoreDirty = true;
                    (subwindow.getParent()).removeWindow(subwindow);

                    //TODO: repeat the original request somehow
                }
            });
            // The components added to the window are actually added to the
            // window's
            // layout; you can use either. Alignments are set using the layout
            layout.addComponent(ok);
            layout.addComponent(cancel);

            getWindow().addWindow(subwindow);

            return true;
        }
        return false;
    }

    private CrumbTrail currentCrumb() {
        if (parts == null || parts.isEmpty())
            return MainDashboard.getCurrent();
        Button button = parts.get(parts.size() - 1);
        return (CrumbTrail) button.getData();
    }

    @Override
    public void goBack(int steps) {
        if (checkDirty())
            return;
        for (int i = 0; i < steps; i++) {
            segmentList.removeLast();
        }
        List<Button> subList = new ArrayList<Button>(parts.subList(0,
                parts.size() - steps));
        if (subList.isEmpty()) // never remove the root
            return;
        layout.removeAllComponents();
        parts.clear();
        CrumbTrail trail = null;
        for (Button link : subList) {
            trail = (CrumbTrail) link.getData();
            addEntry(trail);
        }
        if (trail != null) {
            MainDashboard.getCurrent().setMainComponent(trail.getComponent());
            fireCrumbChanged(trail);
        }


    }

    @Override
    public void walkTo(String... steps) {
        if (checkDirty())
            return;

        Button link = null;
        CrumbTrail currentTrail;
        if (parts.isEmpty()) {
            currentTrail = MainDashboard.getCurrent();
            addEntry(currentTrail);

        } else {
            Button button = parts.get(parts.size() - 1);
            button.setEnabled(true);
            currentTrail = (CrumbTrail) button.getData();

        }
        if (steps != null) {
            for (int i = 0; i < steps.length; i++) {


                String step = steps[i];
                segmentList.add(step);
                if(step.equals(CONFIG))
                    currentTrail = new DynamicConfigPage(currentTrail.getDomainObject());
                else
                    currentTrail = currentTrail.walkTo(step);
                link = addEntry(currentTrail);

            }
        }
        if (link != null) {
            link.setEnabled(false);
        }

        MainDashboard.getCurrent().setMainComponent(currentTrail.getComponent());
        fireCrumbChanged(currentTrail);

    }

    @Override
    public Collection<String> currentPath() {
        return Collections.unmodifiableCollection(segmentList);
    }

    @Override
    public CrumbTrail currentTrail() {
        return (CrumbTrail) parts.get(parts.size()-1).getData();
    }

    @Override
    public void addCrumbListener(CrumbListener listener) {
        listeners.add(new WeakReference<CrumbListener>(listener));

    }

    @Override
    public void removeCrumbListener(CrumbListener listener) {
        Iterator<WeakReference<CrumbListener>> it = listeners.iterator();
        while(it.hasNext())
        {
            WeakReference<CrumbListener> existing = it.next();
            if(listener==existing.get())
            {
                it.remove();
                return;
            }
        }
    }
    private void fireCrumbChanged(CrumbTrail current)
    {
        Iterator<WeakReference<CrumbListener>> it = listeners.iterator();
        while(it.hasNext())
        {
            WeakReference<CrumbListener> reference = it.next();
            CrumbListener crumbListener = reference.get();
            if(crumbListener==null)
                it.remove();
            else
                crumbListener.activeCrumbTrailChanged(current);
        }
    }
}
