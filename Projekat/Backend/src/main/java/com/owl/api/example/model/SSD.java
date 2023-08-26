package com.owl.api.example.model;

public class SSD extends BaseModel {
	private SSDType type;
	private int readSpeed;
	private int writeSpeed;
	private int capacity;
	private int thickness;
	
	public SSD() {
	}
	
	public SSD(SSDType type, int readSpeed, int writeSpeed, int capacity, int thickness) {
		super();
		this.type = type;
		this.readSpeed = readSpeed;
		this.writeSpeed = writeSpeed;
		this.capacity = capacity;
		this.thickness = thickness;
	}

	public SSDType getType() {
		return type;
	}

	public void setType(SSDType type) {
		this.type = type;
	}

	public int getReadSpeed() {
		return readSpeed;
	}

	public void setReadSpeed(int readSpeed) {
		this.readSpeed = readSpeed;
	}

	public int getWriteSpeed() {
		return writeSpeed;
	}

	public void setWriteSpeed(int writeSpeed) {
		this.writeSpeed = writeSpeed;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getThickness() {
		return thickness;
	}

	public void setThickness(int thickness) {
		this.thickness = thickness;
	}
	
}
