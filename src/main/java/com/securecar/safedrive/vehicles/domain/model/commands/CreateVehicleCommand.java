package com.securecar.safedrive.vehicles.domain.model.commands;

public record CreateVehicleCommand( String marca, String modelo, String color, String placa, String imageUri) {

    public CreateVehicleCommand {
        if (marca == null || marca.isEmpty())
            throw new IllegalArgumentException("Marca cannot be null or empty");
        if (modelo == null || modelo.isEmpty())
            throw new IllegalArgumentException("Modelo cannot be null or empty");
        if (color == null || color.isEmpty())
            throw new IllegalArgumentException("Color cannot be null or empty");
        if (placa == null || placa.isEmpty())
            throw new IllegalArgumentException("Placa cannot be null or empty");
        if(imageUri == null || imageUri.isEmpty())
            throw new IllegalArgumentException("ImageUri cannot be null or empty");

    }


}
