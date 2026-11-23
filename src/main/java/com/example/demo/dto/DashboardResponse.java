
package com.example.demo.dto;

import java.util.List;

public class DashboardResponse {

    private long openIncidents;
    private long acknowledgedIncidents;
    private long resolvedIncidents;
    private long totalServices;

    private List<IncidentResponse> recentIncidents;

    public DashboardResponse() {
    }

    public DashboardResponse(
            long openIncidents,
            long acknowledgedIncidents,
            long resolvedIncidents,
            long totalServices,
            List<IncidentResponse> recentIncidents
    ) {
        this.openIncidents = openIncidents;
        this.acknowledgedIncidents = acknowledgedIncidents;
        this.resolvedIncidents = resolvedIncidents;
        this.totalServices = totalServices;
        this.recentIncidents = recentIncidents;
    }

    public long getOpenIncidents() {
        return openIncidents;
    }

    public void setOpenIncidents(long openIncidents) {
        this.openIncidents = openIncidents;
    }

    public long getAcknowledgedIncidents() {
        return acknowledgedIncidents;
    }

    public void setAcknowledgedIncidents(long acknowledgedIncidents) {
        this.acknowledgedIncidents = acknowledgedIncidents;
    }

    public long getResolvedIncidents() {
        return resolvedIncidents;
    }

    public void setResolvedIncidents(long resolvedIncidents) {
        this.resolvedIncidents = resolvedIncidents;
    }

    public long getTotalServices() {
        return totalServices;
    }

    public void setTotalServices(long totalServices) {
        this.totalServices = totalServices;
    }

    public List<IncidentResponse> getRecentIncidents() {
        return recentIncidents;
    }

    public void setRecentIncidents(List<IncidentResponse> recentIncidents) {
        this.recentIncidents = recentIncidents;
    }
}

