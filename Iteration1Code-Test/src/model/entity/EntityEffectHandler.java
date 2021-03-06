package model.entity;

import utilities.HurtSoundEffect;
import utilities.SoundEffect;

public class EntityEffectHandler {
	
	
	public static void applyDamage(Entity entity,int damage){
		entity.subHP(damage);
	}
	
	public static void applyHeal(Entity entity, int heal){
		entity.addHP(heal);
	}
	
	public static void applyBuff(Entity entity, String stat, int mag){
		entity.tempIncStat(stat,mag);
	}
	
	public static void subMP(Entity entity, int mana){entity.subMP(mana);}
	public static void addMP(Entity entity,int mana){entity.addMP(mana);}

}
