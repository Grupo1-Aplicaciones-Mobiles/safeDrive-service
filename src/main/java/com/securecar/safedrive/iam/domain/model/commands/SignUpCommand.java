package com.securecar.safedrive.iam.domain.model.commands;

import com.securecar.safedrive.iam.domain.model.entities.Role;

import java.util.List;

public record SignUpCommand(String name,String username, String password, String phoneNumber, String imageUrl ,List<Role> roles) {
}
