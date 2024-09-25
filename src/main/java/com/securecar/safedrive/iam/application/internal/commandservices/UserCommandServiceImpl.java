package com.securecar.safedrive.iam.application.internal.commandservices;

import com.securecar.safedrive.iam.application.internal.outboundservices.hashing.HashingService;
import com.securecar.safedrive.iam.application.internal.outboundservices.tokens.TokenService;
import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.iam.domain.model.commands.SignInCommand;
import com.securecar.safedrive.iam.domain.model.commands.SignUpCommand;
import com.securecar.safedrive.iam.domain.services.UserCommandService;
import com.securecar.safedrive.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.securecar.safedrive.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.securecar.safedrive.shared.domain.model.aggregates.valueobjects.Coordinates;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User command service implementation
 * <p>
 *     This class implements the {@link UserCommandService} interface and provides the implementation for the
 *     {@link SignInCommand} and {@link SignUpCommand} commands.
 * </p>
 * @version 1.0.0
 */

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService, TokenService tokenService, RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;

    }

    /**
     * Handle the sign-in command
     * <p>
     *     This method handles the {@link SignInCommand} command and returns the user and the token.
     *     If the user is not found or the password is invalid, a {@link RuntimeException} will be thrown.
     * </p>
     * @param command the sign-in command containing the username and password
     * @return and optional containing the user matching the username and the generated token
     * @throws RuntimeException if the user is not found or the password is invalid
     */
    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand command) {

        var user = userRepository.findByUsername(command.username());

        if(user.isEmpty())
            throw new RuntimeException("User not found");

        if(!hashingService.matches(command.password(), user.get().getPassword()))
            throw new RuntimeException("Invalid password");

        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    /**
     * Handle the sign-up command
     * <p>
     *     This method handles the {@link SignUpCommand} command and creates a new user.
     * </p>
     * @param command the sign-up command containing the username and password
     * @return an optional containing the created user
     */
    @Override
    public Optional<User> handle(SignUpCommand command) {
        if(userRepository.existsByUsername(command.username())) {
            throw new RuntimeException("Username already exists");
        }
        var roles = command.roles().stream().
                map(role -> roleRepository.findByName(role.getName()).orElseThrow(() -> new RuntimeException("Role not found"))).
                toList();
        var user = new User(command.username(), hashingService.encode(command.password()), command.phoneNumber(), (Coordinates) roles);
        userRepository.save(user);

        return userRepository.findByUsername(command.username());
    }

    /**
     * Update the user's coordinates (latitude and longitude)
     * <p>
     *     This method updates the coordinates (latitude, longitude) of a user.
     * </p>
     * @param userId the ID of the user
     * @param latitude the new latitude
     * @param longitude the new longitude
     */
    public void updateUserCoordinates(Long userId, double latitude, double longitude) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Actualizar las coordenadas del usuario
        user.updateCoordinates(latitude, longitude);

        // Guardar los cambios en el repositorio
        userRepository.save(user);
    }

}
