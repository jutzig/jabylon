package de.jutzig.jabylon.common.progress.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Service;
import org.eclipse.core.runtime.IProgressMonitor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.jutzig.jabylon.common.progress.ProgressService;
import de.jutzig.jabylon.common.progress.Progression;
import de.jutzig.jabylon.common.progress.RunnableWithProgress;

@Component
@Service(ProgressService.class)
public class ProgressServiceImpl implements ProgressService {

	private AtomicLong id = new AtomicLong();
	private ExecutorService pool = Executors.newFixedThreadPool(10);
	private Cache<Long, RunnableWrapper> jobs = CacheBuilder.newBuilder().expireAfterAccess(30, TimeUnit.SECONDS).build();

	@Override
	public long schedule(RunnableWithProgress task) {
		long currentID = id.getAndIncrement();
		RunnableWrapper wrapper = new RunnableWrapper(task, new ProgressionImpl());
		jobs.put(currentID, wrapper);
		return currentID;
	}

	@Override
	public Progression progressionOf(long id) {
		RunnableWrapper wrapper = jobs.getIfPresent(id);
		if(wrapper!=null)
			return (Progression) wrapper.getMonitor();
		return null;
	}

	@Override
	public void cancel(long id) {
		RunnableWrapper wrapper = jobs.getIfPresent(id);
		if(wrapper!=null)
			wrapper.getMonitor().setCanceled(true);

	}

	@Override
	@Deactivate
	public void shutdown() {
		pool.shutdown();

	}



	class RunnableWrapper implements Runnable {
		private RunnableWithProgress progressRunnable;

		private IProgressMonitor monitor;


		public RunnableWrapper(RunnableWithProgress progressRunnable, IProgressMonitor monitor) {
			super();
			this.progressRunnable = progressRunnable;
			this.monitor = monitor;
		}

		@Override
		public void run() {
			try {
				progressRunnable.run(monitor);
			} finally {
				monitor.done();
			}

		}
		
		public IProgressMonitor getMonitor() {
			return monitor;
		}

	}
}
