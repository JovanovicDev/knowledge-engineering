package com.owl.api.example.model;

public class Computer {
	private Motherboard motherboard;
	private GPU gpu;
	private CPU cpu;
	private RAM ram;
	private SSD ssd;
	private PowerSupply powerSupply;
	private Case boxCase;
	private Purpose purpose;
	private int price;
	
	public Computer() {
	}
	
	public Computer(Motherboard motherboard, GPU gpu, CPU cpu, RAM ram, SSD ssd, PowerSupply powerSupply, Case boxCase,
			Purpose purpose, int price) {
		super();
		this.motherboard = motherboard;
		this.gpu = gpu;
		this.cpu = cpu;
		this.ram = ram;
		this.ssd = ssd;
		this.powerSupply = powerSupply;
		this.boxCase = boxCase;
		this.purpose = purpose;
		this.price = price;
	}

	public Motherboard getMotherboard() {
		return motherboard;
	}

	public void setMotherboard(Motherboard motherboard) {
		this.motherboard = motherboard;
	}

	public GPU getGpu() {
		return gpu;
	}

	public void setGpu(GPU gpu) {
		this.gpu = gpu;
	}

	public CPU getCpu() {
		return cpu;
	}

	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}

	public RAM getRam() {
		return ram;
	}

	public void setRam(RAM ram) {
		this.ram = ram;
	}

	public SSD getSsd() {
		return ssd;
	}

	public void setSsd(SSD ssd) {
		this.ssd = ssd;
	}

	public PowerSupply getPowerSupply() {
		return powerSupply;
	}

	public void setPowerSupply(PowerSupply powerSupply) {
		this.powerSupply = powerSupply;
	}

	public Case getBoxCase() {
		return boxCase;
	}

	public void setBoxCase(Case boxCase) {
		this.boxCase = boxCase;
	}

	public Purpose getPurpose() {
		return purpose;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
