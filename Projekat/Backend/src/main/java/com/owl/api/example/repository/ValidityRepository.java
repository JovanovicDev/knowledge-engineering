package com.owl.api.example.repository;

import org.springframework.stereotype.Repository;

import com.owl.api.example.model.Computer;
import com.owl.api.example.model.Purpose;

import com.owl.api.example.dto.ValidityDto;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Variable;

@Repository
public class ValidityRepository {
	private FIS fuzzy;
	
	public ValidityRepository() {
		this.fuzzy = FIS.load("data/fuzzy_validity.fcl", true);
	}
	
	public ValidityDto getValidities(Computer computer, Purpose purpose) {
		fuzzy.setVariable("cpuCores", computer.getCpu().getCores());
		fuzzy.setVariable("cpuThreads", computer.getCpu().getThreads());
		fuzzy.setVariable("cpuFrequency", computer.getCpu().getFrequency());
		fuzzy.setVariable("gpuMemory", computer.getGpu().getMemoryInGigabytes());
		fuzzy.setVariable("ramCapacity", computer.getRam().getCapacity());
		fuzzy.setVariable("ramFrequency", computer.getRam().getFrequency());
		fuzzy.setVariable("ssdCapacity", computer.getSsd().getCapacity());
		fuzzy.setVariable("ssdWriteSpeed", computer.getSsd().getWriteSpeed());
		fuzzy.setVariable("ssdReadSpeed", computer.getSsd().getReadSpeed());
		fuzzy.setVariable("psuPower", computer.getPowerSupply().getExitPower());
		
		fuzzy.evaluate();

		Variable validityVariable = null;
		switch (purpose) {
			case HOME_USE:
				validityVariable = fuzzy.getVariable("homeUse");
				break;
			case GAMING:
				validityVariable = fuzzy.getVariable("gaming");
				break;
			default:
				break;
		}
		
		double badValidity = validityVariable.getMembership("bad");
		double mediocreValidity = validityVariable.getMembership("mediocre");
		double goodValidity = validityVariable.getMembership("good");
		double maxValidity = Math.max(badValidity, Math.max(mediocreValidity, goodValidity));
		
		ValidityDto retVal = new ValidityDto();
		if (maxValidity == badValidity) {
			retVal.setValue("This configuration is bad for the purpose: " + purpose + ".");
		} else if (maxValidity == mediocreValidity) {
			retVal.setValue("This configuration is mediocre for the purpose: " + purpose + ".");
		} else if (maxValidity == goodValidity) {
			retVal.setValue("This configuration is good for the purpose: " + purpose + ".");
		} else
			retVal.setValue("There was an error.");
		return retVal;
	}
}
