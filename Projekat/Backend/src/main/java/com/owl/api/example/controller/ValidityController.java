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
import com.owl.api.example.model.Purpose;
import com.owl.api.example.dto.ValidityDto;
import com.owl.api.example.repository.ValidityRepository;

@RestController
@RequestMapping(value = "api/validity")
public class ValidityController {
	@Autowired
	private ValidityRepository validityRepository;
	
	@PostMapping()
    public ResponseEntity<ValidityDto> getValidities(@RequestBody Computer computer, @RequestParam Purpose purpose) {
        return new ResponseEntity<ValidityDto>(this.validityRepository.getValidities(computer, purpose), HttpStatus.OK);
    }
	
}