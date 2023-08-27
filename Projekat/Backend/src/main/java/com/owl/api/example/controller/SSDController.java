package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.SSD;
import com.owl.api.example.repository.SSDRepository;

@RestController
@RequestMapping(value = "api/ssd")
public class SSDController {
	@Autowired
	private SSDRepository ssdRepository;
	
	@GetMapping()
	public ResponseEntity<List<SSD>> getAllSSDs() {
		return new ResponseEntity<List<SSD>>(this.ssdRepository.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<SSD>> findSSDs(
			@RequestParam int thickness, 
			@RequestParam int capacity, 
			@RequestParam int readSpeed, 
			@RequestParam int writeSpeed) {
		return new ResponseEntity<List<SSD>>(this.ssdRepository.findSSDs(thickness, capacity, readSpeed, writeSpeed), HttpStatus.OK);
	}
}
