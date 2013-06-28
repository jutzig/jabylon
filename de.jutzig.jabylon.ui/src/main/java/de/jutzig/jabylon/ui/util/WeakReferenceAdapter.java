package de.jutzig.jabylon.ui.util;

import java.lang.ref.WeakReference;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

public class WeakReferenceAdapter extends WeakReference<Adapter> implements Adapter{

    private Notifier notifier;

    public WeakReferenceAdapter(Adapter referent) {
        super(referent);
    }

    @Override
    public void notifyChanged(Notification notification) {
        Adapter delegate = checkReferent();
        if(delegate==null)
            return;
        delegate.notifyChanged(notification);
    }

    @Override
    public Notifier getTarget() {
        return notifier;
    }

    @Override
    public void setTarget(Notifier newTarget) {
        notifier = newTarget;

    }

    @Override
    public boolean isAdapterForType(Object type) {
        Adapter delegate = checkReferent();
        if(delegate==null)
            return false;
        return delegate.isAdapterForType(type);
    }

    private Adapter checkReferent() {
        Adapter adapter = get();
        if(adapter==null)
            notifier.eAdapters().remove(this);
        return adapter;
    }



}
