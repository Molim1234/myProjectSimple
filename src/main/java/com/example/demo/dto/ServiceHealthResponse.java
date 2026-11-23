
package com.example.demo.dto;

import com.example.demo.entity.ServiceHealthStatus;

import java.util.UUID;

public class ServiceHealthResponse {

    private UUID serviceId;
    private String serviceName;
    private ServiceHealthStatus healthStatus;
    private long activeIncidents;

    public ServiceHealthResponse() {
    }

    public ServiceHealthResponse(UUID serviceId, String serviceName,
                                 ServiceHealthStatus healthStatus, long activeIncidents) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.healthStatus = healthStatus;
        this.activeIncidents = activeIncidents;
    }

    public UUID getServiceId() {
        return serviceId;
    }

    public void setServiceId(UUID serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ServiceHealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(ServiceHealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public long getActiveIncidents() {
        return activeIncidents;
    }

    public void setActiveIncidents(long activeIncidents) {
        this.activeIncidents = activeIncidents;
    }
}
