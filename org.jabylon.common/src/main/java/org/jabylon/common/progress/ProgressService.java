package org.jabylon.common.progress;



public interface ProgressService {

    long schedule(RunnableWithProgress task);

    Progression progressionOf(long id);

    void cancel(long id);

    void shutdown();
}
