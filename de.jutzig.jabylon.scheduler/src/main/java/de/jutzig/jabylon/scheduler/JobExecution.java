/**
 *
 */
package de.jutzig.jabylon.scheduler;

import java.util.Map;


/**
 * @author Johannes Utzig (jutzig.dev@googlemail.com)
 *
 */
public interface JobExecution {

    public void run(Map<String, Object> jobContext) throws Exception;

    boolean retryOnError();


}
