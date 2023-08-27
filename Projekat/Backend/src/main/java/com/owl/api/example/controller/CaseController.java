package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.model.Case;
import com.owl.api.example.repository.CaseRepository;

@RestController
@RequestMapping(value = "api/case")
public class CaseController {
	@Autowired
	private CaseRepository caseRepository;
	
	@GetMapping()
	public ResponseEntity<List<Case>> getAllCases() {
		return new ResponseEntity<List<Case>>(this.caseRepository.getAll(), HttpStatus.OK);
	}
}
