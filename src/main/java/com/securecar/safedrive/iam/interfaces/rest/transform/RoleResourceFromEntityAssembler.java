package com.securecar.safedrive.iam.interfaces.rest.transform;

import com.securecar.safedrive.iam.domain.model.entities.Role;
import com.securecar.safedrive.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {
    public static RoleResource toResourceFromEntity(Role role) {
        return new RoleResource(role.getId(), role.getStringName());
    }
}
