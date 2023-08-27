package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.RAM;
import com.owl.api.example.repository.RAMRepository;

@RestController
@RequestMapping(value = "api/ram")
public class RAMController {
	@Autowired
	private RAMRepository ramRepository;
	
	@GetMapping()
	public ResponseEntity<List<RAM>> getAllRAMs() {
		return new ResponseEntity<List<RAM>>(this.ramRepository.getAll(), HttpStatus.OK);
	}
}
