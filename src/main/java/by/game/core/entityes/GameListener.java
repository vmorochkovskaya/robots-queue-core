package by.game.core.entityes;

import by.game.proxi.IGameActorListener;
import by.game.proxi.IRobot;

public class GameListener implements IGameActorListener{
	
	private Integer countA=0;
	private Integer countB=0;
	private Integer countC=0;
	private Integer countTask=0;

	@Override
	public void robotIsAdded(IRobot robot) {
		String type = robot.getClass().getSimpleName();
		switch(type){
		case "ARobot":
			synchronized(this.countA){
				this.countA++;
			}
			break;
		case "BRobot":
			synchronized(this.countB){
				this.countB++;
			}
			break;
		case "CRobot":
			synchronized(this.countC){
				this.countC++;
			}
			break;
		}
		
	}

	@Override
	public void robotIsDied(IRobot robot) {
		String type = robot.getClass().getSimpleName();
		switch(type){
		case "ARobot":
			synchronized(this.countA){
				this.countA--;
			}
			this.checkA();
			break;
		case "BRobot":
			synchronized(this.countB){
				this.countB--;
			}
			this.checkB();
			break;
		case "CRobot":
			synchronized(this.countC){
				this.countC--;
			}
			this.checkC();
			break;
		}
		
	}

	@Override
	public void taskAdded() {
		synchronized(this.countTask){
			this.countTask++;
			this.checkTaskCount();
		}
		
	}

	@Override
	public void taskIsPerformed() {
		synchronized(this.countTask){
			this.countTask--;
		}
	}
	
	private void checkA(){
		boolean add = false;
		synchronized(this.countA){
			if(this.countA<=1)add=true;
		}
		if(add)Game.instance().addRobot(new ARobot(null));
	}
	
	private void checkB(){
		boolean add = false;
		synchronized(this.countB){
			if(this.countB<=1)add=true;
		}
		if(add)Game.instance().addRobot(new BRobot(null));
	}
	
	private void checkC(){
		boolean add = false;
		synchronized(this.countC){
			if(this.countC<=1)add=true;
		}
		if(add)Game.instance().addRobot(new CRobot(null));
	}
	
	private void checkTaskCount(){
			if(this.countTask>Task.THRESHOLD)this.addSomeRobot();
	}

	private void addSomeRobot(){
		int i = (int)(30*Math.random());
		switch(i/10){
		case 0:Game.instance().addRobot(new ARobot(null));
		break;
		case 1:Game.instance().addRobot(new BRobot(null));
		break;
		case 2:Game.instance().addRobot(new CRobot(null));
		}
	}
}
