package com.owl.api.example.dto;

import ucm.gaia.jcolibri.cbrcore.Attribute;
import ucm.gaia.jcolibri.cbrcore.CaseComponent;

public class ComputerSimilarityDto implements CaseComponent {
    private String name;
    private int cpuNumOfCores;
    private double cpuFrequency;
    private int gpuMemory;
    private int gpuMemoryBus;
    private int ramCapacity;
    private String ramType;
    private int diskCapacity;
    private double eval;

    public ComputerSimilarityDto() {
    }

    public ComputerSimilarityDto(ComputerSimilarityDto similar, double eval) {
        this.name = similar.name;
        this.cpuNumOfCores = similar.cpuNumOfCores;
        this.cpuFrequency = similar.cpuFrequency;
        this.gpuMemory = similar.gpuMemory;
        this.gpuMemoryBus = similar.gpuMemoryBus;
        this.ramCapacity = similar.ramCapacity;
        this.ramType = similar.ramType;
        this.diskCapacity = similar.diskCapacity;
        this.eval = eval;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCpuNumOfCores() {
        return cpuNumOfCores;
    }

    public void setCpuNumOfCores(int cpuNumOfCores) {
        this.cpuNumOfCores = cpuNumOfCores;
    }

    public double getCpuFrequency() {
        return cpuFrequency;
    }

    public void setCpuFrequency(double cpuFrequency) {
        this.cpuFrequency = cpuFrequency;
    }

    public int getGpuMemory() {
        return gpuMemory;
    }

    public void setGpuMemory(int gpuMemory) {
        this.gpuMemory = gpuMemory;
    }

    public int getGpuMemoryBus() {
        return gpuMemoryBus;
    }

    public void setGpuMemoryBus(int gpuMemoryBus) {
        this.gpuMemoryBus = gpuMemoryBus;
    }

    public int getRamCapacity() {
        return ramCapacity;
    }

    public void setRamCapacity(int ramCapacity) {
        this.ramCapacity = ramCapacity;
    }

    public String getRamType() {
        return ramType;
    }

    public void setRamType(String ramType) {
        this.ramType = ramType;
    }

    public int getDiskCapacity() {
        return diskCapacity;
    }

    public void setDiskCapacity(int diskCapacity) {
        this.diskCapacity = diskCapacity;
    }

    public double getEval() {
        return eval;
    }

    public void setEval(double eval) {
        this.eval = eval;
    }

    @Override
    public Attribute getIdAttribute() {
        return null;
    }
}
