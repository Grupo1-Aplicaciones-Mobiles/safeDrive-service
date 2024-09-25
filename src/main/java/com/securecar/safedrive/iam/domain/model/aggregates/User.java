package com.securecar.safedrive.iam.domain.model.aggregates;

import com.securecar.safedrive.iam.domain.model.entities.Role;
import com.securecar.safedrive.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.securecar.safedrive.shared.domain.model.aggregates.valueobjects.Coordinates;
import com.securecar.safedrive.vehicles.domain.model.aggregates.Vehicle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * User aggregate root
 * This class represents the aggregate root for the User entity.
 *
 * @see AuditableAbstractAggregateRoot
 */
@Getter
@Setter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 50)
    private String phoneNumber;

    @Embedded
    private Coordinates coordinates;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Vehicle> vehicles = new HashSet<>();

    // Método para agregar un vehículo al usuario
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        vehicle.setUser(this);  // Asegurar que el vehículo conoce a su propietario
    }

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() {
        this.roles = new HashSet<>();
    }
    public User(String username, String password, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = new HashSet<>();
    }

    public User(String username, String password, String phoneNumber, Coordinates coordinates) {
        this(username, password, phoneNumber);
        this.coordinates = coordinates;
    }

    public User(String username, String password, String phoneNumber, Coordinates coordinates, List<Role> roles) {
        this(username, password, phoneNumber, coordinates);
        addRoles(roles);
    }



    /**
     * Update the coordinates of the user
     * @param latitude the new latitude
     * @param longitude the new longitude
     */
    public void updateCoordinates(double latitude, double longitude) {
        this.coordinates = new Coordinates(latitude, longitude);
    }

    /**
     * Add a role to the user
     * @param role the role to add
     * @return the user with the added role
     */
    public User addRole(Role role) {
        this.roles.add(role);
        return this;
    }

    /**
     * Add a list of roles to the user
     * @param roles the list of roles to add
     * @return the user with the added roles
     */
    public User addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.roles.addAll(validatedRoleSet);
        return this;
    }

}
