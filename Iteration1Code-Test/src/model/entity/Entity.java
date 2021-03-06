package model.entity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Map;

import javax.swing.Timer;

import utilities.DeathSoundEffect;
import utilities.ImageProcessing;
import utilities.RNG;
import utilities.Scaling;
import utilities.SoundEffect;
import utilities.SpriteSheetUtility;
import model.Point;
import model.Skill;
import model.behavior.Behavior;
import model.behavior.State;
import model.items.Equipable;
import model.items.TakeableItem;
import model.occupation.Occupation;
import model.slots.Equipment;
import model.slots.Inventory;
import model.slots.InventoryEquipment;
import model.spells.Spellable;
import model.spells.Spells;
import model.stats.EntityStats;

public abstract class Entity implements Dieable{
	protected EntityStats stats; 
	protected Map<String,Skill> skills;
	protected Occupation occupation;
	protected String name;
	protected int direction;
	protected int gold;
	private String currMap="Main";
	protected InventoryEquipment inventoryEquipment;
	protected State preferredState = new State();
	protected State engagedState = new State();
	protected SoundEffect soundEffect;
	private BufferedImage[] spriteSheet;
	private BufferedImage image;
	
	//TODO change the spells so that they are only associated with Alchemists
	protected Spells spells = new Spells(this);
	
	private Timer buffTime;
	private boolean buffed = false;
	
	public Entity(Occupation occupation) {
		this.occupation = occupation;
		gold = 0;
		occupation.createNecessities();
		this.stats = new EntityStats(occupation.getStats());
		this.skills = occupation.getSkills();
		this.inventoryEquipment = new InventoryEquipment(new Inventory(),occupation.getEquipment());
		SpriteSheetUtility util = occupation.getSpriteSheet();
		this.spriteSheet = (util.getSpriteArray());
	}
	
	public BufferedImage getImage(){
			BufferedImage imageToDisplay = spriteSheet[direction];
			image= ImageProcessing.scaleImage(Scaling.AVATAR_WIDTH, Scaling.AVATAR_HEIGHT, imageToDisplay);
			return image;
	}
	
	public void kill(){
		this.preferredState.kill();
		this.engagedState.kill();
	}
	
	/********************** O BEHAVE ****************************************/
	public void setPrefferedBehavior(Behavior behavior){
		this.preferredState.setState(behavior);
	}
	
	public void setEngageBehavior(Behavior behavior){
		this.engagedState.setState(behavior);
	}
	
	public void idle(){
		//WE FUCKED
		this.preferredState.perform(this, this);
	}

	public void engage(Avatar avatar){
		this.engagedState.perform(this, avatar);
	}

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
	
	public int getGold(){
		return gold;
	}
	
	public void makeGoldTransaction(int amount){
		gold += amount;
	}
	
	
	/******************* INVENTORY  ********************************/
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
	
	public void tempIncStat(String s, int value){
		if(buffed)return;
		buffed = true;
		int old = stats.getStatValue(s);
		this.stats.setStatValue(s, Math.max(0,old + value));
		buffTime = new Timer(500,new BuffTimer(old,s));
		buffTime.start();
	}
	
	public int getMP(){return this.stats.getMP();}
	public int getHP(){return this.stats.getHP();}
	
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
	
	public String observation(int x, int d){
		String s = "Observation: \n";
		String[] info = {"Level","Life","HP", "Agility", "Strength",
				 "Intellect", "MP", "Hardiness"};
		int n = 0;
		for(int i = 0; i < x && i < 7; ++i){
			int offset = 1;
			if(RNG.genRandDouble() > 1/(d + offset)){
				n = RNG.generateRand(0,30);
			}
			else{
				n = stats.getStatValue(info[i]);
			}
			s += (info[i] + ":" + n + "\n");
		}
		s += (getClass().getName().toString() + "@" + Integer.toHexString(hashCode()).toString());
		return s;
	}
	
	public Occupation getOccupation() {
		return occupation;
	}
	
	// -------------------------------------------
	// not 1st iteration stuff
	//TODO  fix this code  it is bad really bad, cause now your attack is fixed to 5
	public int attack(){
		return stats.getStatValue("OffensiveRating");
	}
	
	public int defense() {
		return stats.getStatValue("DefensiveRating");
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

	
	/*************   FIX THIS STUFF ***********************/
	public Object getDialogue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void writeJournal(Object dialogue) {
		// TODO Auto-generated method stub
		
	}
	
	public String diaryEntry() {
		return "This looks like "+toString()+"\nProbably has "+stats.getStatValue("HP")+"HP left\n";
	}
	
	public class BuffTimer implements ActionListener {
		long start = System.currentTimeMillis();
		int value = 0;
		String stat = "";
		public BuffTimer(int value, String stat){
			this.value = value;
			this.stat = stat;
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			long timePassed = System.currentTimeMillis() - start;
			if(timePassed > 5000){
				stats.setStatValue(stat, value);
				buffed = false;
				buffTime.stop();
			}
			
		}

	}
}


//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((name == null) ? 0 : name.hashCode());
//		return result;
//	}

//	@Override
//	public boolean equals(Object obj) {
//		Entity other = (Entity) obj;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		return true;
//	}
//	
//	@Override
//	public void makeDeathSoundEffect(){
//		soundEffect = new DeathSoundEffect();
//	}



