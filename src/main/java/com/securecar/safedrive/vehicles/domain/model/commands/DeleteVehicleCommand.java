package com.securecar.safedrive.vehicles.domain.model.commands;

public record DeleteVehicleCommand(Long id) {
    public DeleteVehicleCommand {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
    }
}
