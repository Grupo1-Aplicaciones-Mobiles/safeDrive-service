package com.securecar.safedrive.iam.domain.model.commands;

import com.securecar.safedrive.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String username, String password, String phoneNumber ,List<Role> roles) {
}
