package com.owl.api.example.model;

public class Case extends BaseModel {
	private CaseType type;
	private int pciSlots;
	private boolean hasPowerSupply;
	
	public Case() {
	}
	
	public Case(CaseType type, int pciSlots, boolean hasPowerSupply) {
		super();
		this.type = type;
		this.pciSlots = pciSlots;
		this.hasPowerSupply = hasPowerSupply;
	}

	public CaseType getType() {
		return type;
	}

	public void setType(CaseType type) {
		this.type = type;
	}

	public int getPciSlots() {
		return pciSlots;
	}

	public void setPciSlots(int pciSlots) {
		this.pciSlots = pciSlots;
	}

	public boolean isHasPowerSupply() {
		return hasPowerSupply;
	}

	public void setHasPowerSupply(boolean hasPowerSupply) {
		this.hasPowerSupply = hasPowerSupply;
	}

}
