package model.entity;

import java.awt.image.BufferedImage;
import java.util.Map;

import model.Point;
import model.Skill;
import model.items.Equipable;
import model.items.TakeableItem;
import model.occupation.Occupation;
import model.slots.Equipment;
import model.slots.Inventory;
import model.slots.InventoryEquipment;
import model.spells.Spellable;
import model.spells.Spells;
import model.stats.EntityStats;

public abstract class Entity {
	protected EntityStats stats; 
	protected Map<String,Skill> skills;
	protected Occupation occupation;
	protected String name;
	protected int direction;
	private String currMap="Main";
	protected InventoryEquipment inventoryEquipment;
	
	//TODO change the spells so that they are only associated with Alchemists
	protected Spells spells = new Spells(this);
	
	public Entity() {}
	
	public Entity(Occupation occupation) {
		this.occupation = occupation;
		occupation.createNecessities();
		this.stats = new EntityStats(occupation.getStats());
		this.skills = occupation.getSkills();
		this.inventoryEquipment = new InventoryEquipment(new Inventory(),occupation.getEquipment());
	}

	public abstract void engage(Avatar avatar);
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getDirection() {
		return direction;
	}
	public int getSkillValue(String key) {
		if (this.skills.containsKey(key)) return this.skills.get(key).getSkillLevel();
		return -1;
	}
	
	
	/**************   INVENTORY  ********************************/
	public Inventory getInventory(){
		return inventoryEquipment.getInventory();
	}
	
	public void setInventory(Inventory inventory){
		this.inventoryEquipment.setInventory(inventory);
	}
	
	public boolean equipInventory(TakeableItem item){
		return this.inventoryEquipment.equipInventory(item);
	}
	
	public TakeableItem unequipInventorySlot(Point slotPoint){
		return this.inventoryEquipment.unequipInventory(slotPoint);
	}
	
	public boolean equipInventory(TakeableItem item, Point point){
		return this.inventoryEquipment.equipInventory(item,point);
	}
	
	/*************  EQUIPMENT *************************/
	public void setEquipment(Equipment equipment){
		this.inventoryEquipment.setEquipment(equipment);
	}
	
	public Equipment getEquipment(){
		return inventoryEquipment.getEquipment();
	}
	
	public void equip(Equipable item){
		this.inventoryEquipment.equipEquipment(item);
	}
	
	public TakeableItem unequipEquipment(Point point){
		return this.inventoryEquipment.unequipEquipment(point);
	}
	
	/********************** STATS ***********************/
	public String getStat(String s){
		return this.stats.getStat(s);
	}
	public EntityStats getStats(){
		return this.stats;
	}
	public void setStatValue(String key, int value) {
		this.stats.setStatValue(key, value);
	}
	
	public int getStatValue(String key) {
		return this.stats.getStatValue(key);
	}
	
	public int getMP(){return this.stats.getMP();}
	
	public void subHP(int change){this.stats.subHP(change);}
	public void subMP(int change){this.stats.subMP(change);}
	
	public void addHP(int change){this.stats.addHP(change);}
	public void addMP(int change){this.stats.addMP(change);}
	
	
	/******************** SPELLS ******************************/
	public Spells getSpells(){
		return this.spells;
	}
	public void setSelectedSpell(Point spell){
		this.spells.setSelectedSpell(spell);
	}
	public Spellable getSelectedSpell(){
		return this.spells.getSelectedSpell();
	}
	
	
	public void incSkillValue(String key) {
		if (this.skills.containsKey(key)) this.skills.get(key).upgradeSkillLevel();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Occupation getOccupation() {
		return occupation;
	}
	
	public abstract BufferedImage getImage();
	
	// -------------------------------------------
	// not 1st iteration stuff
	//TODO  fix this code  it is bad really bad, cause now your attack is fixed to 5
	public int attack(){
		return 5;
	}
	
	private void useAbility() {
		//occupation.useAbility();
	}
	// -------------------------------------------

	public String getCurrMap() {
		return currMap;
	}

	public void setCurrMap(String currMap) {
		this.currMap = currMap;
	}
}
