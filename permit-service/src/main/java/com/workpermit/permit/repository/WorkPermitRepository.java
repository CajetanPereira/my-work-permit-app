package com.workpermit.permit.repository;

import com.workpermit.permit.entity.WorkPermit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface WorkPermitRepository extends MongoRepository<WorkPermit, String> {
    List<WorkPermit> findByLocation(String location);
}
