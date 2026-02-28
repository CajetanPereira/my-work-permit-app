package com.workpermit.permit.dto;

import com.workpermit.permit.entity.PermitStatus;
import jakarta.validation.constraints.NotNull;

public record UpdatePermitStatusRequest(@NotNull PermitStatus status) {}
