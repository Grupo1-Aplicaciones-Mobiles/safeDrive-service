package com.securecar.safedrive.vehicles.domain.model.commands;

public record UpdateVehicleCommand(Long id, String marca, String modelo, String color, String placa, String imageUri) {
}
