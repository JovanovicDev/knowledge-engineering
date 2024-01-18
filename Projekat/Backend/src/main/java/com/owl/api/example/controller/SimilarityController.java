package com.owl.api.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.owl.api.example.dto.ComputerSimilarityDto;
import com.owl.api.example.model.SimilarComputer;
import com.owl.api.example.repository.SimilarityRepository;

import java.util.List;

@RestController
@RequestMapping(value = "api/similarity")
public class SimilarityController {
    @Autowired
    private SimilarityRepository similarityRepository;

    @PostMapping
    public ResponseEntity<List<SimilarComputer>> getSimilar(@RequestBody ComputerSimilarityDto dto) {
        return new ResponseEntity<>(similarityRepository.getSimilar(dto), HttpStatus.OK);
    }
}
