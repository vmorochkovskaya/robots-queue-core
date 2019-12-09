package by.game.core;

import by.game.core.dao.ILogDao;
import by.game.core.dao.LogDaoImpl;
import by.game.core.entity.Log;
import by.game.proxi.IRobot;
import by.game.proxi.ITask;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public abstract class Robot extends Thread implements IRobot {

    private ILogDao logDAO = new LogDaoImpl();

//	@Autowired
//	private ILogDao logDao;

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
		Log log = new Log();
		log.setRobotName(this.name);
		log.setMessage("Robot is dying as killed");
		log.setTime(new java.sql.Time(new java.util.Date().getTime()));
        try {
            logDAO.addLog(log);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Game.gameActorListener().robotIsDied(this);
		Game.toNullRobotTaskQueue(this);
	}

	@Override
	public void free() {
		Game.gameActorListener().taskIsPerformed();
	}

	@Override
	public void nextTask() {
		ITask task=null;
		try {
			TaskQueue queue = Game.taskQueue(this.getIdObject());
			if(queue!=null){
				queue.acquire();
				task = Game.taskQueue(this.getIdObject()).getTask();
				Game.taskQueue(getIdObject()).release();
			}
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
		Log log = new Log();
		log.setRobotName(this.name);
		log.setMessage("Robot is added");
		log.setTime(new java.sql.Time(new java.util.Date().getTime()));
        try {
            logDAO.addLog(log);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(this.alive){
			this.nextTask();
		}
		log.setRobotName(this.name);
		log.setMessage("Robot is dying as life cycle is over");
		log.setTime(new java.sql.Time(new java.util.Date().getTime()));
        try {
            logDAO.addLog(log);
        } catch (SQLException e) {
            e.printStackTrace();
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
