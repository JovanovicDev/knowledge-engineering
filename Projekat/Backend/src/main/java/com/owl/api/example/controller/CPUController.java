package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.CPU;
import com.owl.api.example.repository.CPURepository;

@RestController
@RequestMapping(value = "api/cpu")
public class CPUController {
	@Autowired
	private CPURepository cpuRepository;
	
	@GetMapping()
	public ResponseEntity<List<CPU>> getAllCPUs() {
		return new ResponseEntity<List<CPU>>(this.cpuRepository.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<CPU>> findCPUs(
			@RequestParam int thermicPower, 
			@RequestParam int cores, 
			@RequestParam int threads, 
			@RequestParam int fabricationProcess, 
			@RequestParam double frequency,
			@RequestParam boolean canOverclock, 
			@RequestParam boolean hasIntegratedGraphics) {
		return new ResponseEntity<List<CPU>>(this.cpuRepository.findCPUs(thermicPower, cores, threads, fabricationProcess, frequency, canOverclock, hasIntegratedGraphics), HttpStatus.OK);
	}
}
