package com.securecar.safedrive.iam.interfaces.rest.resources;

import java.util.List;

public record UserResource(Long id, String name, String username, String phoneNumber, String imageUrl, Double latitude, Double longitude, List<String> roles) {
}
