package model.behavior;

import utilities.Directions;
import model.entity.Entity;

public class Barter implements Behavior{
	
	public Barter(){}

	public void perform(Entity initiate, Entity receiver) {
		initiate.writeJournal(receiver.getDialogue());
		int oppositeDirection = Directions.getOppositeDirection(initiate.getDirection());
		receiver.setDirection(oppositeDirection);	
	}

	public void getBuffs() {}

}