package com.example.demo.controller;

import com.example.demo.dto.AlertResponse;
import com.example.demo.dto.CreateAlert;
import com.example.demo.entity.Alert;
import com.example.demo.service.AlertService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;


    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @PostMapping
    public AlertResponse addAlert(@RequestBody @Valid CreateAlert createAlert){
        return alertService.addAlert(createAlert);
    }


}
