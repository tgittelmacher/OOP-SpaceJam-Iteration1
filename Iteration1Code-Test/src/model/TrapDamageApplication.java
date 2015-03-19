package model;

import model.entity.Entity;

public class TrapDamageApplication {
	
	
	public static void apply(Entity entity, Trap trap){
		entity.setStatValue("HP",(int)(entity.getStatValue("HP") - trap.getDamage())); 
	}
}
