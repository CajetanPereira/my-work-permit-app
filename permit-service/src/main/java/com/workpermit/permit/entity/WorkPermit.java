package com.workpermit.permit.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "work_permits")
public class WorkPermit {
    @Id
    private String id;
    private String vendorName;
    private String location;
    private String workDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private PermitStatus status;
    private String createdBy;
    private String approvedBy;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getVendorName() { return vendorName; }
    public void setVendorName(String vendorName) { this.vendorName = vendorName; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getWorkDescription() { return workDescription; }
    public void setWorkDescription(String workDescription) { this.workDescription = workDescription; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public PermitStatus getStatus() { return status; }
    public void setStatus(PermitStatus status) { this.status = status; }
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }
}
