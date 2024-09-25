package com.securecar.safedrive.tracking.infrastructure.configuration.api;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GoogleMapsApiClient {

    private final GeoApiContext geoApiContext;

    public GoogleMapsApiClient(@Value("${google.api.key}") String apiKey) {
        this.geoApiContext = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }

    /**
     * Llama a la API de Google Distance Matrix para obtener la distancia entre dos puntos.
     * @param origin Coordenadas del origen (usuario)
     * @param destination Coordenadas del destino (vehículo)
     * @return Un objeto DistanceMatrix con la información de la distancia
     */
    public DistanceMatrix getDistance(LatLng origin, LatLng destination) throws Exception {
        return DistanceMatrixApi.newRequest(geoApiContext)
                .origins(origin)
                .destinations(destination)
                .await();
    }
}