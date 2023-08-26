package com.owl.api.example.model;

public class RAM extends BaseModel {
	private RAMType type;
	private int latency;
	private int capacity;
	private int frequency;
	private double voltage;
	
	public RAM() {
	}
	
	public RAM(RAMType type, int latency, int capacity, int frequency, double voltage) {
		super();
		this.type = type;
		this.latency = latency;
		this.capacity = capacity;
		this.frequency = frequency;
		this.voltage = voltage;
	}

	public RAMType getType() {
		return type;
	}

	public void setType(RAMType type) {
		this.type = type;
	}

	public int getLatency() {
		return latency;
	}

	public void setLatency(int latency) {
		this.latency = latency;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}
	
}
