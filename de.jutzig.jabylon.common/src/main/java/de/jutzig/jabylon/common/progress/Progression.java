package de.jutzig.jabylon.common.progress;

public interface Progression {
	
	String getTaskName();
	
	String getSubTaskName();
	
	int getCompletion();
	
	boolean isDone();
	
}
