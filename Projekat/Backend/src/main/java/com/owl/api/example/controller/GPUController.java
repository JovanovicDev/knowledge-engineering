package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
