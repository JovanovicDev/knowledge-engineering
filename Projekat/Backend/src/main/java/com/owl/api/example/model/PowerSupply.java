package com.owl.api.example.model;

public class PowerSupply extends BaseModel {
	private PowerSupplyType type;
	private int sataConnectors;
	private int exitPower;
	private int molexConnectors;
	private int fanDiameter;
	
	public PowerSupply() {
	}
	
	public PowerSupply(PowerSupplyType type, int sataConnectors, int exitPower, int molexConnectors, int fanDiameter) {
		super();
		this.type = type;
		this.sataConnectors = sataConnectors;
		this.exitPower = exitPower;
		this.molexConnectors = molexConnectors;
		this.fanDiameter = fanDiameter;
	}

	public PowerSupplyType getType() {
		return type;
	}

	public void setType(PowerSupplyType type) {
		this.type = type;
	}

	public int getSataConnectors() {
		return sataConnectors;
	}

	public void setSataConnectors(int sataConnectors) {
		this.sataConnectors = sataConnectors;
	}

	public int getExitPower() {
		return exitPower;
	}

	public void setExitPower(int exitPower) {
		this.exitPower = exitPower;
	}

	public int getMolexConnectors() {
		return molexConnectors;
	}

	public void setMolexConnectors(int molexConnectors) {
		this.molexConnectors = molexConnectors;
	}

	public int getFanDiameter() {
		return fanDiameter;
	}

	public void setFanDiameter(int fanDiameter) {
		this.fanDiameter = fanDiameter;
	}
	
}
