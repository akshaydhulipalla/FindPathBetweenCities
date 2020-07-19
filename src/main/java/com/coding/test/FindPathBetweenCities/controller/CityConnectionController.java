package com.coding.test.FindPathBetweenCities.controller;

import com.coding.test.FindPathBetweenCities.service.CityGraphServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CityConnectionController {
    private static Logger LOG = LoggerFactory.getLogger(CityConnectionController.class);

    @Autowired
    CityGraphServiceImpl cityGraphServiceImpl;

    @GetMapping(value="/connected")
    public ResponseEntity<String> connectionExists(@RequestParam(required = true) String origin, @RequestParam(required = true) String destination) {
        ResponseEntity<String> response;
        try {
            String result = cityGraphServiceImpl.checkPathBetweenCities(origin, destination);
            response = new ResponseEntity<>(result, HttpStatus.OK);
        } catch(RuntimeException ex) {
            response = new ResponseEntity<>("Invalid Input", HttpStatus.OK);
        }

        return response;
    }

}
