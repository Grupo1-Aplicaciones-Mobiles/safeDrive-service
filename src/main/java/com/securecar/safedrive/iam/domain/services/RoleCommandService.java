package com.securecar.safedrive.iam.domain.services;

import com.securecar.safedrive.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
