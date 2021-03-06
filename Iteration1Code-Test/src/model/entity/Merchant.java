package model.entity;

import utilities.FriendlyDeathSoundEffect;
import utilities.SoundEffect;
import model.behavior.Barter;
import model.behavior.Patrol;
import model.behavior.Stand;
import model.occupation.MerchantHunter;

public class Merchant extends NPC implements Conversable {
	
	public Merchant() {
		super(new MerchantHunter());
		this.engagedState.setState(new Barter(this));
		this.preferredState.setState(new Stand(this));
		//SoundEffect effect = new FriendlyDeathSoundEffect();
	}

	public String getDialogue() {
		// TODO Auto-generated method stub
		return "Merchant: Hey there! Have a look at my wares.";
	}
	
	public String toString() {
		return "a Merchant";
	}
	
	@Override
	public void makeDeathSoundEffect(){
		soundEffect = new FriendlyDeathSoundEffect();
	}

}
