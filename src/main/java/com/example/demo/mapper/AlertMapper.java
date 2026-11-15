package com.example.demo.mapper;

import com.example.demo.dto.AlertResponse;
import com.example.demo.dto.CreateAlert;
import com.example.demo.entity.Alert;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {

    public Alert toAlert(CreateAlert createAlert){
        var alert = new Alert();
        alert.setErrorCode(createAlert.getErrorCode());
        alert.setMessage(createAlert.getMessage());
        return alert;
    }


    public AlertResponse toAlertResponse(Alert alert) {
        return new AlertResponse(
                alert.getId(),
                alert.getErrorCode(),
                alert.getMessage(),
                alert.getReceivedAt(),
                alert.getIncident().getId()
        );
    }



}
