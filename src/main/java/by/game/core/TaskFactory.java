package by.game.core;

public class TaskFactory extends Thread{

	private static int PAUSE = 10000;
	
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
}
