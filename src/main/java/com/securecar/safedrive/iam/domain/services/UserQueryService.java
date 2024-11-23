package com.securecar.safedrive.iam.domain.services;

import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.iam.domain.model.queries.GetAllUsersQuery;
import com.securecar.safedrive.iam.domain.model.queries.GetCoordinatesByUserIdQuery;
import com.securecar.safedrive.iam.domain.model.queries.GetUserByIdQuery;
import com.securecar.safedrive.iam.domain.model.queries.GetUserByUsernameQuery;
import com.securecar.safedrive.shared.domain.model.aggregates.valueobjects.Coordinates;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
    Optional<Coordinates> handle(GetCoordinatesByUserIdQuery query);
}
