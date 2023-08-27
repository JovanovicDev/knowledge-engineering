package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.Motherboard;
import com.owl.api.example.repository.MotherboardRepository;

@RestController
@RequestMapping(value = "api/motherboard")
public class MotherboardController {
	@Autowired
	private MotherboardRepository motherboardRepository;
	
	@GetMapping()
	public ResponseEntity<List<Motherboard>> getAllMotherboards() {
		return new ResponseEntity<List<Motherboard>>(this.motherboardRepository.getAll(), HttpStatus.OK);
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<Motherboard>> findMotherboards(
			@RequestParam int pciExpressSlots, @RequestParam int sataSlots, @RequestParam int ramSlots, @RequestParam int m2Slots) {
		return new ResponseEntity<List<Motherboard>>(this.motherboardRepository.findMotherboards(pciExpressSlots, sataSlots, ramSlots, m2Slots), HttpStatus.OK);
	}
	
}
