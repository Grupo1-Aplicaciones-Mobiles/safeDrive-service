package com.securecar.safedrive.iam.interfaces.rest.transform;

import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {
    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(user.getId(), user.getUsername(), token);
    }
}
