package com.workpermit.permit.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreatePermitRequest(@NotBlank String vendorName,
                                  @NotBlank String location,
                                  @NotBlank String workDescription,
                                  @NotNull LocalDate startDate,
                                  @NotNull LocalDate endDate) {}
