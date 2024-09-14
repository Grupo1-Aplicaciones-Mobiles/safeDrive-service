package com.securecar.safedrive.iam.domain.model.queries;

import com.securecar.safedrive.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
