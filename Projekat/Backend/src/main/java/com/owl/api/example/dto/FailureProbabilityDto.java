package com.owl.api.example.dto;

public class FailureProbabilityDto {
	private String causeName;
	private double probability;

	public FailureProbabilityDto() {
	}
	
	public FailureProbabilityDto(String causeName, double probability) {
		this.causeName = causeName;
		this.probability = probability;
	}

	public String getCauseName() {
		return causeName;
	}

	public void setCauseName(String causeName) {
		this.causeName = causeName;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}
	
}
