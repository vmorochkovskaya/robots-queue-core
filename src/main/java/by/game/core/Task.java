package by.game.core;


import by.game.proxi.IRobot;
import by.game.proxi.ITask;

public class Task implements ITask{

	public static final long DEFAULT_MAX_TASK_TIME = 30000;
	private static Long MAX_TASK_TIME = DEFAULT_MAX_TASK_TIME;
	public static final int DEFAULT_THRESHOLD = 5;
	protected static volatile Integer THRESHOLD = DEFAULT_THRESHOLD;
	private static Integer COUNT = 0;

	private String task;
	private long time;
	
	public Task(String task){
		synchronized(COUNT){
			COUNT++;
		}
		this.task = task;
		synchronized(MAX_TASK_TIME){
			this.time = (long)(MAX_TASK_TIME*Math.random());
		}
	}
	
	public Task(){
		synchronized(COUNT){
			this.task = "TASK №"+(COUNT++);
		}
		synchronized(MAX_TASK_TIME){
			this.time = (long)(MAX_TASK_TIME*Math.random());
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
			((Robot)robot).pause(this.time);
		}
		robot.say(" task "+this.getTaskValue()+" performed");
		robot.free();
	}
	
	protected static void setTaskAmountThreshold(int threshold){
		THRESHOLD = threshold;
	}
	
	protected static void setMaxTaskTime(long time){
		MAX_TASK_TIME = time;
	}

}
