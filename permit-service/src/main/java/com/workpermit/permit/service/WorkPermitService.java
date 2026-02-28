package com.workpermit.permit.service;

import com.workpermit.permit.dto.CreatePermitRequest;
import com.workpermit.permit.entity.PermitStatus;
import com.workpermit.permit.entity.WorkPermit;
import com.workpermit.permit.repository.WorkPermitRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkPermitService {

    private final WorkPermitRepository repository;

    public WorkPermitService(WorkPermitRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = {"permits", "permit-list"}, allEntries = true)
    public WorkPermit create(CreatePermitRequest request, String createdBy) {
        WorkPermit permit = new WorkPermit();
        permit.setVendorName(request.vendorName());
        permit.setLocation(request.location());
        permit.setWorkDescription(request.workDescription());
        permit.setStartDate(request.startDate());
        permit.setEndDate(request.endDate());
        permit.setStatus(PermitStatus.PENDING);
        permit.setCreatedBy(createdBy);
        return repository.save(permit);
    }

    @Cacheable("permits")
    public WorkPermit getById(String id) {
        return repository.findById(id).orElseThrow();
    }

    @Cacheable(value = "permit-list", key = "#location == null ? 'all' : #location")
    public List<WorkPermit> list(String location) {
        return location == null || location.isBlank() ? repository.findAll() : repository.findByLocation(location);
    }

    @CacheEvict(value = {"permits", "permit-list"}, allEntries = true)
    public WorkPermit updateStatus(String id, PermitStatus status, String approvedBy) {
        WorkPermit permit = repository.findById(id).orElseThrow();
        permit.setStatus(status);
        permit.setApprovedBy(approvedBy);
        return repository.save(permit);
    }
}
