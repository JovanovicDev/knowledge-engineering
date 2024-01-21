package com.owl.api.example.model;

public class Motherboard extends BaseModel {
	private MotherboardChipset chipset;
	private Manufacturer compatibleWithCPUsFrom;
	private GPUMemoryType compatibleWithGPUMemoryType;
	private PowerSupplyType compatibleWithPSUType;
	private CaseType compatibleWithCaseType;
	private RAMType compatibleWithRAMType;
	private SSDType compatibleWithSSDType;
	private int pciExpressSlots;
	private int sataSlots;
	private int ramSlots;
	private int m2Slots;
	
	public Motherboard() {
	}
	
	public Motherboard(MotherboardChipset chipset, Manufacturer compatibleWithCPUsFrom, GPUMemoryType compatibleWithGPUMemoryType, PowerSupplyType compatibleWithPSUType, 
			CaseType compatibleWithCaseType, RAMType compatibleWithRAMType, SSDType compatibleWithSSDType, int pciExpressSlots,
			int sataSlots, int ramSlots, int m2Slots) {
		super();
		this.chipset = chipset;
		this.compatibleWithCPUsFrom = compatibleWithCPUsFrom;
		this.compatibleWithGPUMemoryType = compatibleWithGPUMemoryType;
		this.compatibleWithPSUType = compatibleWithPSUType;
		this.compatibleWithCaseType = compatibleWithCaseType;
		this.compatibleWithRAMType = compatibleWithRAMType;
		this.compatibleWithSSDType = compatibleWithSSDType;
		this.pciExpressSlots = pciExpressSlots;
		this.sataSlots = sataSlots;
		this.ramSlots = ramSlots;
		this.m2Slots = m2Slots;
	}

	public MotherboardChipset getChipset() {
		return chipset;
	}

	public void setChipset(MotherboardChipset chipset) {
		this.chipset = chipset;
	}

	public Manufacturer getCompatibleWithCPUsFrom() {
		return compatibleWithCPUsFrom;
	}

	public void setCompatibleWithCPUsFrom(Manufacturer compatibleWithCPUsFrom) {
		this.compatibleWithCPUsFrom = compatibleWithCPUsFrom;
	}
	
	public GPUMemoryType getCompatibleWithGPUMemoryType() {
		return compatibleWithGPUMemoryType;
	}

	public void setCompatibleWithGPUMemoryType(GPUMemoryType compatibleWithGPUMemoryType) {
		this.compatibleWithGPUMemoryType = compatibleWithGPUMemoryType;
	}

	public PowerSupplyType getCompatibleWithPSUType() {
		return compatibleWithPSUType;
	}

	public void setCompatibleWithPSUType(PowerSupplyType compatibleWithPSUType) {
		this.compatibleWithPSUType = compatibleWithPSUType;
	}

	public CaseType getCompatibleWithCaseType() {
		return compatibleWithCaseType;
	}

	public void setCompatibleWithCaseType(CaseType compatibleWithCaseType) {
		this.compatibleWithCaseType = compatibleWithCaseType;
	}

	public RAMType getCompatibleWithRAMType() {
		return compatibleWithRAMType;
	}

	public void setCompatibleWithRAMType(RAMType compatibleWithRAMType) {
		this.compatibleWithRAMType = compatibleWithRAMType;
	}

	public SSDType getCompatibleWithSSDType() {
		return compatibleWithSSDType;
	}

	public void setCompatibleWithSSDType(SSDType compatibleWithSSDType) {
		this.compatibleWithSSDType = compatibleWithSSDType;
	}

	public int getPciExpressSlots() {
		return pciExpressSlots;
	}

	public void setPciExpressSlots(int pciExpressSlots) {
		this.pciExpressSlots = pciExpressSlots;
	}

	public int getSataSlots() {
		return sataSlots;
	}

	public void setSataSlots(int sataSlots) {
		this.sataSlots = sataSlots;
	}

	public int getRamSlots() {
		return ramSlots;
	}

	public void setRamSlots(int ramSlots) {
		this.ramSlots = ramSlots;
	}

	public int getM2Slots() {
		return m2Slots;
	}

	public void setM2Slots(int m2Slots) {
		this.m2Slots = m2Slots;
	}
	
}
