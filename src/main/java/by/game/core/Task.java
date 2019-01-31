package by.game.core;


import by.game.proxi.IRobot;
import by.game.proxi.ITask;

public class Task implements ITask{

	private static final long MAX_TASK_TIME = 30000;
	public static final int THRESHOLD = 5;
	private static Integer COUNT = 0;

	private String task;
	
	public Task(String task){
		synchronized(COUNT){
			COUNT++;
		}
		this.task = task;
	}
	
	public Task(){
		synchronized(COUNT){
			this.task = "TASK №"+(COUNT++);
		}
	}
	
	public Object getTaskValue() {
		// TODO Auto-generated method stub
		return this.task;
	}

	public void perform(IRobot robot) {
		robot.say(" start to perform task :"+this.getTaskValue().toString());
		if(ITask.KILL_YOURSELF.equals(this.task)){
			robot.die();
		}else{
			((Robot)robot).pause((long)(MAX_TASK_TIME*Math.random()));
		}
		robot.say(" task "+this.getTaskValue()+" performed");
		robot.free();
	}
	
	protected void finalize(){
		System.out.println("finalizing "+this.task+" ...");
	}
	
	

}
