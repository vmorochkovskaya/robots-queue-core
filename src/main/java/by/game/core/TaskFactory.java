package by.game.core;

public class TaskFactory extends Thread{

	public static final long DEFAULT_MAX_PAUSE = 30000;
	private volatile static Long PAUSE = DEFAULT_MAX_PAUSE;
	
	public void run(){
		while(true){
			Game.instance().addTask(new Task());
			try {
				sleep(PAUSE);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	protected void setTaskInterval(long interval){
		synchronized(PAUSE){
			PAUSE=interval;
			}
	}
}
