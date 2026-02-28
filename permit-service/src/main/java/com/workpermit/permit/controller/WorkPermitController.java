package com.workpermit.permit.controller;

import com.workpermit.permit.dto.CreatePermitRequest;
import com.workpermit.permit.dto.UpdatePermitStatusRequest;
import com.workpermit.permit.entity.WorkPermit;
import com.workpermit.permit.service.WorkPermitService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permits")
public class WorkPermitController {

    private final WorkPermitService service;

    public WorkPermitController(WorkPermitService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('VENDOR', 'ADMIN')")
    public WorkPermit create(@Valid @RequestBody CreatePermitRequest request,
                             @AuthenticationPrincipal(expression = "username") String username) {
        return service.create(request, username);
    }

    @GetMapping("/{id}")
    public WorkPermit getById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping
    public List<WorkPermit> list(@RequestParam(required = false) String location) {
        return service.list(location);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'SECURITY')")
    public WorkPermit updateStatus(@PathVariable String id,
                                   @Valid @RequestBody UpdatePermitStatusRequest request,
                                   @AuthenticationPrincipal(expression = "username") String username) {
        return service.updateStatus(id, request.status(), username);
    }
}
