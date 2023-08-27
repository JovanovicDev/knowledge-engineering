package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.Computer;
import com.owl.api.example.repository.ComputerRepository;

@RestController
@RequestMapping(value = "api/computer")
public class ComputerController {
	@Autowired
	private ComputerRepository computerRepository;
	
	@GetMapping()
	public ResponseEntity<List<Computer>> getAllComputers() {
		return new ResponseEntity<List<Computer>>(this.computerRepository.getAll(), HttpStatus.OK);
	}
}
