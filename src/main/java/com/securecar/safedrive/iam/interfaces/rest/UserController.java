package com.securecar.safedrive.iam.interfaces.rest;

import com.securecar.safedrive.iam.application.internal.commandservices.UserCommandServiceImpl;
import com.securecar.safedrive.iam.domain.model.queries.GetAllUsersQuery;
import com.securecar.safedrive.iam.domain.model.queries.GetCoordinatesByUserIdQuery;
import com.securecar.safedrive.iam.domain.model.queries.GetUserByIdQuery;
import com.securecar.safedrive.iam.domain.services.UserQueryService;
import com.securecar.safedrive.iam.interfaces.rest.resources.UserResource;
import com.securecar.safedrive.iam.interfaces.rest.resources.dtos.CoordinatesDTO;
import com.securecar.safedrive.iam.interfaces.rest.resources.dtos.UpdateCoordinatesDTO;
import com.securecar.safedrive.iam.interfaces.rest.resources.dtos.UpdateImageUrlDTO;
import com.securecar.safedrive.iam.interfaces.rest.resources.dtos.UpdateUserDTO;
import com.securecar.safedrive.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UserController {

    private final UserQueryService userQueryService;
    private final UserCommandServiceImpl userCommandService;

    public UserController(UserQueryService userQueryService, UserCommandServiceImpl userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }



    /**
     * Get all users
     * @return list of user resources
     */
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers(){

        var getAllUsersQuery = new GetAllUsersQuery();
        var users= userQueryService.handle(getAllUsersQuery);
        var userResources= users.stream().map(
                UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(userResources);
    }



    /**
     * this method returns the user with the given id
     * @param userId the user id
     * @return the user resource with the given id
     */
    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {

        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                UserResourceFromEntityAssembler.toResourceFromEntity(user.get())
        );

    }

    @GetMapping("/{userId}/coordinates")
    public Optional<CoordinatesDTO> getUserCoordinates(@PathVariable Long userId) {
        return userQueryService.handle(new GetCoordinatesByUserIdQuery(userId))
                .map(coordinates -> new CoordinatesDTO(coordinates.getLatitude(), coordinates.getLongitude()));
    }


    /**
     * Endpoint to update the user's coordinates
     * @param updateCoordinatesDTO contains the userId, latitude, and longitude
     * @return HTTP 200 OK if the update is successful
     */
    @PutMapping("/coordinates")
    public ResponseEntity<Void> updateCoordinates(@Valid @RequestBody UpdateCoordinatesDTO updateCoordinatesDTO) {
        userCommandService.updateUserCoordinates(
                updateCoordinatesDTO.getUserId(),
                updateCoordinatesDTO.getLatitude(),
                updateCoordinatesDTO.getLongitude()
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/image")
    public ResponseEntity<Void> updateImage(@Valid @RequestBody UpdateImageUrlDTO updateImageDTO) {
        userCommandService.updateUserImageUrl(
                updateImageDTO.getUserId(),
                updateImageDTO.getImageUrl()
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/details")
    public ResponseEntity<Void> updateUserDetails(@Valid @RequestBody UpdateUserDTO updateUserDTO) {
        userCommandService.updateUserInfo(
                updateUserDTO.getUserId(),
                updateUserDTO.getName(),
                updateUserDTO.getUsername(),
                updateUserDTO.getPhoneNumber()
        );

        return ResponseEntity.ok().build();
    }





}