package com.workpermit.auth.dto;

import com.workpermit.auth.entity.Role;

import java.util.Set;

public record UserResponse(Long id, String username, String location, Set<Role> roles) {}
