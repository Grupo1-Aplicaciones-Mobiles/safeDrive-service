package com.securecar.safedrive.iam.domain.model.queries;

public class GetCoordinatesByUserIdQuery {
    private final Long userId;

    public GetCoordinatesByUserIdQuery(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
