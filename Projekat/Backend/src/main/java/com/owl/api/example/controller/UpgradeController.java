package com.owl.api.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.Computer;
import com.owl.api.example.repository.CPURepository;
import com.owl.api.example.repository.CaseRepository;
import com.owl.api.example.repository.GPURepository;
import com.owl.api.example.repository.PowerSupplyRepository;
import com.owl.api.example.repository.RAMRepository;
import com.owl.api.example.repository.SSDRepository;

@RestController
@RequestMapping(value = "api/upgrade")
public class UpgradeController {
	
	@Autowired
	private GPURepository gpuRepository;
	
	@Autowired
	private CPURepository cpuRepository;
	
	@Autowired
	private PowerSupplyRepository psuRepository;
	
	@Autowired
	private CaseRepository caseRepository;
	
	@Autowired
	private RAMRepository ramRepository;
	
	@Autowired
	private SSDRepository ssdRepository;
	
	@PostMapping()
    public ResponseEntity<Computer> upgrade(@RequestBody Computer computer, @RequestParam String componentType) {
        switch(componentType) {
        	case "gpu": {
        		computer.setGpu(this.gpuRepository.findUpgrade(computer.getGpu(), computer.getMotherboard()));
        		break;
        	}
			case "cpu": {
				computer.setCpu(this.cpuRepository.findUpgrade(computer.getCpu(), computer.getMotherboard().getCompatibleWithCPUsFrom()));
				break;
			}	
			case "psu": {
				computer.setPowerSupply(this.psuRepository.findUpgrade(computer.getPowerSupply(), computer.getMotherboard()));
				break;
			}
			case "case": {
				computer.setBoxCase(this.caseRepository.findUpgrade(computer.getBoxCase(), computer.getMotherboard()));
				break;
			}
			case "ram": {
				computer.setRam(this.ramRepository.findUpgrade(computer.getRam(), computer.getMotherboard()));
				break;
			}
			case "ssd": {
				computer.setSsd(this.ssdRepository.findUpgrade(computer.getSsd(), computer.getMotherboard()));
				break;
			}
        }
        
        return new ResponseEntity<Computer>(computer, HttpStatus.OK);
    }
	
}