package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.GPU;
import com.owl.api.example.repository.GPURepository;

@RestController
@RequestMapping(value = "api/gpu")
public class GPUController {
	@Autowired
	private GPURepository gpuRepository;
	
	@GetMapping()
	public ResponseEntity<List<GPU>> getAllGPUs() {
		return new ResponseEntity<List<GPU>>(this.gpuRepository.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<GPU>> findGPUs(
			@RequestParam int memoryInGigabytes, 
			@RequestParam int memoryBusInBits, 
			@RequestParam int pciExpressVersion, 
			@RequestParam boolean hasDVIInterface, 
			@RequestParam boolean hasUSBCInterface,
			@RequestParam boolean hasHDMIInterface, 
			@RequestParam boolean hasDisplayPortInterface, 
			@RequestParam boolean hasVGAInterface) {
		return new ResponseEntity<List<GPU>>(this.gpuRepository.findGPUs(memoryInGigabytes, memoryBusInBits, pciExpressVersion, hasDVIInterface, hasUSBCInterface, hasHDMIInterface, hasDisplayPortInterface, hasVGAInterface), HttpStatus.OK);
	}
}
