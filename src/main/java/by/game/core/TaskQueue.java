package by.game.core;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import by.game.proxi.ITask;
import by.game.proxi.ITaskQueue;

public class TaskQueue extends Semaphore implements ITaskQueue{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LinkedList<ITask> queue = new LinkedList<>();
	
	public TaskQueue(){
		super(1, true);
	}
	
	public void addTask(ITask arg0) {
		this.queue.add(arg0);
	}

	public ITask getTask() {
		if(this.queue.size()>0){
			return this.queue.remove();
		}else return null;
	}

}
