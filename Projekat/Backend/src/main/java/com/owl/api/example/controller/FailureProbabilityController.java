package com.owl.api.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owl.api.example.dto.FailureProbabilityDto;
import com.owl.api.example.repository.FailureProbabilityRepository;

@RestController
@RequestMapping(value = "api/failure")
public class FailureProbabilityController {
	 @Autowired
	 FailureProbabilityRepository failureProbabilityRepository;

	 @PostMapping
	 public ResponseEntity<List<FailureProbabilityDto>> getReasons(@RequestBody List<String> failures) throws Exception {
	     return new ResponseEntity<List<FailureProbabilityDto>>(this.failureProbabilityRepository.getFailureCauseProbability(failures), HttpStatus.OK);
	 }
}