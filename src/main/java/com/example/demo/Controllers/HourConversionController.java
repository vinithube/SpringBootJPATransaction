package com.example.demo.Controllers;


import com.example.demo.Payload.ProvidedHrs;
import com.example.demo.Services.RestoOpenService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HourConversionController {

    Logger logger = LoggerFactory.getLogger(HourConversionController.class);

    @Autowired
    RestoOpenService restoOpenService;

    @ApiOperation(value = "Get the JSON format of the opening hours of the restaurant" +
            "and returns it in a more human friendly format")

    @PostMapping("/opening-hours")
    public ResponseEntity<?> hotelTiming(@Valid @RequestBody ProvidedHrs inputTime) {
        logger.info("Processing Request");
        return new ResponseEntity<>(restoOpenService.doConversion(inputTime), HttpStatus.OK);
    }
}