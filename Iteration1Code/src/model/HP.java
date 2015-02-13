package model;

import java.io.Serializable;

public class HP extends PrimaryStat implements Serializable {

	private Life life;
	
	public HP(int value) {
		super(value);
	}
	
	public void setLife(Life life) {
		this.life = life;
	}

	@Override
	protected boolean updateValue(int value) {
		if (value <= 0) return false; // you are dead!
		this.value = Math.min(life.getValue(), value);
		return true;
	}
	

}
