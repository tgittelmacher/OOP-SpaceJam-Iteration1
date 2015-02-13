package model;

import java.io.Serializable;

public class WeaponSlot extends BufferSlot<Weapon> implements Serializable{
	public WeaponSlot(DerivedStat offensiveRating){
		addObserver(offensiveRating);
	}
	
	public WeaponSlot(){}

    public static void main(String[] args){
        WeaponSlot slot = new WeaponSlot();
        OffensiveRating stat = new OffensiveRating(0,slot);
    	slot.addObserver(stat);
        Weapon weapon = new Weapon(15);
        //slot.equip(item);
        slot.equip(weapon);
        slot.unequip();
    }
}
