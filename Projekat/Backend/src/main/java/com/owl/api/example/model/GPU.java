package com.owl.api.example.model;

public class GPU extends BaseModel {
	private GPUMemoryType memoryType;
	private Manufacturer chipManufacturer;
	private int memoryInGigabytes;
	private int memoryBusInBits;
	private int pciExpressVersion;
	private boolean hasDVIInterface;
	private boolean hasUSBCInterface;
	private boolean hasHDMIInterface;
	private boolean hasDisplayPortInterface;
	private boolean hasVGAInterface;
	
	public GPU() {
	}

	public GPU(GPUMemoryType memoryType, Manufacturer chipManufacturer, int memoryInGigabytes, int memoryBusInBits,
			int pciExpressVersion, boolean hasDVIInterface, boolean hasUSBCInterface, boolean hasHDMIInterface,
			boolean hasDisplayPortInterface, boolean hasVGAInterface) {
		super();
		this.memoryType = memoryType;
		this.chipManufacturer = chipManufacturer;
		this.memoryInGigabytes = memoryInGigabytes;
		this.memoryBusInBits = memoryBusInBits;
		this.pciExpressVersion = pciExpressVersion;
		this.hasDVIInterface = hasDVIInterface;
		this.hasUSBCInterface = hasUSBCInterface;
		this.hasHDMIInterface = hasHDMIInterface;
		this.hasDisplayPortInterface = hasDisplayPortInterface;
		this.hasVGAInterface = hasVGAInterface;
	}

	public GPUMemoryType getMemoryType() {
		return memoryType;
	}

	public void setMemoryType(GPUMemoryType memoryType) {
		this.memoryType = memoryType;
	}

	public Manufacturer getChipManufacturer() {
		return chipManufacturer;
	}

	public void setChipManufacturer(Manufacturer chipManufacturer) {
		this.chipManufacturer = chipManufacturer;
	}

	public int getMemoryInGigabytes() {
		return memoryInGigabytes;
	}

	public void setMemoryInGigabytes(int memoryInGigabytes) {
		this.memoryInGigabytes = memoryInGigabytes;
	}

	public int getMemoryBusInBits() {
		return memoryBusInBits;
	}

	public void setMemoryBusInBits(int memoryBusInBits) {
		this.memoryBusInBits = memoryBusInBits;
	}

	public int getPciExpressVersion() {
		return pciExpressVersion;
	}

	public void setPciExpressVersion(int pciExpressVersion) {
		this.pciExpressVersion = pciExpressVersion;
	}

	public boolean isHasDVIInterface() {
		return hasDVIInterface;
	}

	public void setHasDVIInterface(boolean hasDVIInterface) {
		this.hasDVIInterface = hasDVIInterface;
	}

	public boolean isHasUSBCInterface() {
		return hasUSBCInterface;
	}

	public void setHasUSBCInterface(boolean hasUSBCInterface) {
		this.hasUSBCInterface = hasUSBCInterface;
	}

	public boolean isHasHDMIInterface() {
		return hasHDMIInterface;
	}

	public void setHasHDMIInterface(boolean hasHDMIInterface) {
		this.hasHDMIInterface = hasHDMIInterface;
	}

	public boolean isHasDisplayPortInterface() {
		return hasDisplayPortInterface;
	}

	public void setHasDisplayPortInterface(boolean hasDisplayPortInterface) {
		this.hasDisplayPortInterface = hasDisplayPortInterface;
	}

	public boolean isHasVGAInterface() {
		return hasVGAInterface;
	}

	public void setHasVGAInterface(boolean hasVGAInterface) {
		this.hasVGAInterface = hasVGAInterface;
	}
	
}
