package com.securecar.safedrive.vehicles.interfaces.rest.resources;

public record CreateVehicleResource( String marca, String modelo, String color, String placa) {
    public CreateVehicleResource {
        if (marca == null || marca.isBlank()) {
            throw new IllegalArgumentException("Marca is required");
        }
        if (modelo == null || modelo.isBlank()) {
            throw new IllegalArgumentException("Modelo is required");
        }
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color is required");
        }
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("Placa is required");
        }
    }
}
