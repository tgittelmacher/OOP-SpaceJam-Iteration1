package model.spells;

import model.entity.Entity;
import model.entity.EntityEffectHandler;

public abstract class BaneSpellSingle extends SpellSingleAffect implements BaneSpell{

	public BaneSpellSingle(Entity entity) {
		super(entity);
	}
	
	public final void apply(Entity entityToAffect){
		if (this.able()){
			EntityEffectHandler.subMP(this.entity, this.getManaRequirement());
			this.applyDamage(entityToAffect);
			this.makeSoundEffect();
		}
		//ELSE DO NOTHING
	}
	
	public void applyDamage(Entity entity){
		EntityEffectHandler.applyDamage(entity, this.getDamage());
	}
}
