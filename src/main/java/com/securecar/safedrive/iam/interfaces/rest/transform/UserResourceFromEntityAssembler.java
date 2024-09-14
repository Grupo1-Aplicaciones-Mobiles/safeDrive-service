package com.securecar.safedrive.iam.interfaces.rest.transform;

import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.iam.domain.model.entities.Role;
import com.securecar.safedrive.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User user) {
        var roles = user.getRoles().stream().map(Role::getStringName).toList();
        return new UserResource(user.getId(), user.getUsername(), user.getPhoneNumber(), roles);
    }
}
