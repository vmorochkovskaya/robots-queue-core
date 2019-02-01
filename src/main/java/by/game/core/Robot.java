package by.game.core;

import by.game.proxi.IRobot;
import by.game.proxi.ITask;

public abstract class Robot extends Thread implements IRobot{
	
	private static Integer COUNT = 0;
	private String name;
	private int id;
	private boolean alive = true;
	
	public Robot(String name){
		synchronized(COUNT){
			this.id = COUNT++;
		}
		if(name==null){
			this.name = "№"+this.id;
		}else this.name = name;
	}
	
	@Override
	public void die() {
		this.alive = false;
		Game.gameActorListener().robotIsDied(this);
	}

	@Override
	public void free() {
		Game.gameActorListener().taskIsPerformed();
	}

	@Override
	public void nextTask() {
		ITask task=null;
		try {
			Game.taskQueue(getIdObject()).acquire();
			task = Game.taskQueue().getTask();
			Game.taskQueue(getIdObject()).release();
			if(task==null){
				Game.taskQueue().acquire();
				task = Game.taskQueue().getTask();
				Game.taskQueue().release();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(task==null)return;
		task.perform(this);
	}

	@Override
	public void say(String message) {
		Game.gameActivityTracker().log(this.getClass().getSimpleName()+" id:"+this.getIdObject()+" : "+message);
	}

	@Override
	public Object getIdObject() {
		// TODO Auto-generated method stub
		return this.name;
	}

	public void run(){
		while(this.alive){
			this.nextTask();
		}
		Game.gameActivityTracker().log(this.name+" : I am dying...");
	}
	
	public void pause(long timeout){
		try {
			sleep(timeout);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
