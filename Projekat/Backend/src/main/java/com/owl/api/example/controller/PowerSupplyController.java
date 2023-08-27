package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.PowerSupply;
import com.owl.api.example.repository.PowerSupplyRepository;

@RestController
@RequestMapping(value = "api/psu")
public class PowerSupplyController {
	@Autowired
	private PowerSupplyRepository psuRepository;
	
	@GetMapping()
	public ResponseEntity<List<PowerSupply>> getAllPSUs() {
		return new ResponseEntity<List<PowerSupply>>(this.psuRepository.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<PowerSupply>> findPSUs(
			@RequestParam int sataConnectors, 
			@RequestParam int molexConnectors, 
			@RequestParam int exitPower, 
			@RequestParam int fanDiameter) {
		return new ResponseEntity<List<PowerSupply>>(this.psuRepository.findPSUs(sataConnectors, molexConnectors, exitPower, fanDiameter), HttpStatus.OK);
	}
}
