package com.owl.api.example.model;

public class CPU extends BaseModel {
	private CPUSocket socket;
	private int thermicPower;
	private int cores;
	private int threads;
	private int fabricationProcess;
	private double frequency;
	private boolean canOverclock;
	private boolean hasIntegratedGraphics;
	
	public CPU() {
	}
	
	public CPU(CPUSocket socket, int thermicPower, int cores, int threads, int fabricationProcess, double frequency,
			boolean canOverclock, boolean hasIntegratedGraphics) {
		super();
		this.socket = socket;
		this.thermicPower = thermicPower;
		this.cores = cores;
		this.threads = threads;
		this.fabricationProcess = fabricationProcess;
		this.frequency = frequency;
		this.canOverclock = canOverclock;
		this.hasIntegratedGraphics = hasIntegratedGraphics;
	}

	public CPUSocket getSocket() {
		return socket;
	}

	public void setSocket(CPUSocket socket) {
		this.socket = socket;
	}

	public int getThermicPower() {
		return thermicPower;
	}

	public void setThermicPower(int thermicPower) {
		this.thermicPower = thermicPower;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public int getFabricationProcess() {
		return fabricationProcess;
	}

	public void setFabricationProcess(int fabricationProcess) {
		this.fabricationProcess = fabricationProcess;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public boolean isCanOverclock() {
		return canOverclock;
	}

	public void setCanOverclock(boolean canOverclock) {
		this.canOverclock = canOverclock;
	}

	public boolean isHasIntegratedGraphics() {
		return hasIntegratedGraphics;
	}

	public void setHasIntegratedGraphics(boolean hasIntegratedGraphics) {
		this.hasIntegratedGraphics = hasIntegratedGraphics;
	}
	
}
