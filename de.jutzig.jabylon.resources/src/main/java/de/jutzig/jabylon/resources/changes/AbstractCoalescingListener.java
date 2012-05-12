/**
 * 
 */
package de.jutzig.jabylon.resources.changes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;

/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 * 
 */
public abstract class AbstractCoalescingListener implements PropertiesListener, ActionListener {

	private volatile CDOTransaction transaction;
	private Timer timer;

	public AbstractCoalescingListener() {
		timer = new Timer(2000, this);
		timer.setRepeats(false);
	}

	protected synchronized CDOTransaction retrieveTransaction(CDOObject o) {
		if (transaction != null)
			return transaction;
		CDOView cdoView = o.cdoView();
		transaction = cdoView.getSession().openTransaction();
		return transaction;
	}

	protected void process() {
		timer.restart();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		commit();

	}

	private synchronized void commit() {
		if (transaction == null || !transaction.isDirty())
			return;
		CDOTransaction theTransaction = transaction;
		transaction = null;
		try {
			if (theTransaction.isDirty())
				theTransaction.commit();
		} catch (CommitException e) {
			handleException(e);
		} finally {
			theTransaction.close();
		}

	}

	protected void handleException(Exception e) {
		e.printStackTrace();
	}

}
