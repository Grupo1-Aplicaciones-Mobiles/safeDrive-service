package com.securecar.safedrive.vehicles.domain.model.aggregates;

import com.securecar.safedrive.iam.domain.model.aggregates.User;
import com.securecar.safedrive.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.securecar.safedrive.shared.domain.model.aggregates.valueobjects.Coordinates;
import com.securecar.safedrive.vehicles.domain.model.commands.CreateVehicleCommand;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Vehicle extends AuditableAbstractAggregateRoot<Vehicle> {
    //los atributos de la clase son marca,modelo, color,placa e imagen
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @NotBlank
    @Size(max = 50)
    @Column
    private String marca;

    @NotBlank
    @Size(max = 50)
    @Column
    private String modelo;

    @NotBlank
    @Size(max = 50)
    @Column
    private String color;

    @NotBlank
    @Size(max = 50)
    @Column(unique = true)
    private String placa;

    @NotBlank
    @Column
    private String imageUri;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // Relación con el usuario

    @Embedded
    private Coordinates coordinates;


    //constructor de la clase
    public Vehicle(String marca, String modelo, String color, String placa, String imageUri) {
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.imageUri = imageUri;
    }


    public Vehicle(CreateVehicleCommand createVehicleCommand) {
        this.marca = createVehicleCommand.marca();
        this.modelo = createVehicleCommand.modelo();
        this.color = createVehicleCommand.color();
        this.placa = createVehicleCommand.placa();
        this.imageUri = createVehicleCommand.imageUri();
    }

    public Vehicle() {

    }

    public Vehicle updateInformation(String marca, String modelo, String color, String placa, String imageUri) {
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.placa = placa;
        this.imageUri = imageUri;
        return this;
    }

    public void updateCoordinates(double latitude, double longitude) {
        this.coordinates = new Coordinates(latitude, longitude);
    }
}
