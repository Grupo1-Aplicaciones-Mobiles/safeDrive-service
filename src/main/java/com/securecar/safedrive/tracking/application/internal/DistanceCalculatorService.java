package com.securecar.safedrive.tracking.application.internal;

import com.securecar.safedrive.shared.domain.model.aggregates.valueobjects.Coordinates;
import org.springframework.stereotype.Service;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import com.securecar.safedrive.tracking.infrastructure.configuration.api.GoogleMapsApiClient;

@Service
public class DistanceCalculatorService {

    private final GoogleMapsApiClient googleMapsApiClient;

    public DistanceCalculatorService(GoogleMapsApiClient googleMapsApiClient) {
        this.googleMapsApiClient = googleMapsApiClient;
    }

    /**
     * Calcula la distancia entre dos coordenadas utilizando la API de Google Distance Matrix.
     * @param userCoordinates Coordenadas del usuario
     * @param vehicleCoordinates Coordenadas del vehículo
     * @return Distancia en kilómetros o metros
     * @throws Exception si ocurre algún error durante la llamada a la API de Google
     */
    public String calculateDistanceAndTime(Coordinates userCoordinates, Coordinates vehicleCoordinates) throws Exception {
        LatLng origin = new LatLng(userCoordinates.getLatitude(), userCoordinates.getLongitude());
        LatLng destination = new LatLng(vehicleCoordinates.getLatitude(), vehicleCoordinates.getLongitude());

        DistanceMatrix distanceMatrix = googleMapsApiClient.getDistance(origin, destination);

        String distance = distanceMatrix.rows[0].elements[0].distance.humanReadable;
        String duration = distanceMatrix.rows[0].elements[0].duration.humanReadable;

        return "Distancia: " + distance + ", Tiempo estimado: " + duration;
    }
}