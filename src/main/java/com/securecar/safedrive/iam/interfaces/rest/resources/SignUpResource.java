package com.securecar.safedrive.iam.interfaces.rest.resources;

import java.util.List;

public record SignUpResource(String name, String username, String password, String phoneNumber, String imageUrl , List<String> roles) {
}
