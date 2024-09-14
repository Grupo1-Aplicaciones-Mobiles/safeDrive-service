package com.securecar.safedrive.iam.interfaces.rest.transform;

import com.securecar.safedrive.iam.domain.model.commands.SignInCommand;
import com.securecar.safedrive.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource signInResource) {
        return new SignInCommand(signInResource.username(), signInResource.password());
    }
}
