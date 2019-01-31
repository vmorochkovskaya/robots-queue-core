package by.game.core.entityes;

import java.util.HashMap;
import java.util.Map;

import by.game.proxi.IGameActivityTracker;
import by.game.proxi.IGameActorListener;
import by.game.proxi.IGameWorld;
import by.game.proxi.IRobot;
import by.game.proxi.ITask;
import by.game.proxi.ITaskQueue;

public class Game implements IGameWorld{

	private static Game INSTANCE = new Game();
	private TaskFactory taskFactory=new TaskFactory();
	private IGameActivityTracker gameActivityTracker;
	private IGameActorListener gameActorListener = new GameListener();
	private Map<Object,TaskQueue>taskQueeMap = new HashMap<>();
	private TaskQueue commonTaskQueue = new TaskQueue();
	
	private Game(){
		
	}
	
	public static IGameWorld instance(){
		return INSTANCE;
	}

	public void addRobot(IRobot robot) {
		this.gameActorListener.robotIsAdded(robot);
		synchronized(this.taskQueeMap){
			this.taskQueeMap.put(robot.getIdObject(), new TaskQueue());
		}
		((Robot)robot).start();
		this.gameActivityTracker.log("Robot: "+robot.getIdObject().toString()+" successifully added");
		this.gameActorListener.robotIsAdded(robot);
	}

	public void addTask(ITask arg0) {
		synchronized(this.commonTaskQueue){
			this.commonTaskQueue.addTask(arg0);
		}
		this.gameActivityTracker.log(arg0.getTaskValue()+" added");
		this.gameActorListener.taskAdded();
	}

	public void addTask(Object key, ITask arg1) {
		if(key==null)return;
		synchronized(this.taskQueeMap){
			ITaskQueue queue = this.taskQueeMap.get(key);
			if(queue ==null)return;
			queue.addTask(arg1);
		}
		this.gameActivityTracker.log(arg1.getTaskValue()+" added");
		this.gameActorListener.taskAdded();
	}

	public IRobot getById(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public IRobot someRobot() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static TaskQueue taskQueue(Object key){
		return INSTANCE.taskQueeMap.get(key);
	}
	
	public static TaskQueue taskQueue(){
		return INSTANCE.commonTaskQueue;
	}

	public static IGameActivityTracker gameActivityTracker(){
		return INSTANCE.gameActivityTracker;
	}
	
	public static IGameActorListener gameActorListener(){
		return INSTANCE.gameActorListener;
	}
	
	public static void start(){
		INSTANCE.gameActivityTracker().log("I am starting");
		INSTANCE.taskFactory.start();
	}

	@Override
	public void setIGameActivityTracker(IGameActivityTracker arg0) {
		this.gameActivityTracker = arg0;
	}
	
}
