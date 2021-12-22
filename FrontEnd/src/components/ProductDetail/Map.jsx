import React, { useState } from "react";
import GoogleMap from "google-map-react";
import "./Map.css";

function Map({latitude, longitude, hotel, streetName, streetNumber, city, country}) {
  // Datos del lugar
  const [place] = useState({
    lat: latitude,
    lng: longitude,
  });

  // Zoom por default para el mapa de google
  const [zoom] = useState(12);

  // Ventana de información del marker personalizada
  const getInfoWindowString = (place) => `
    <div style="width: 300px;">
      <div style="font-size: 16px; font-weight: 600; color: #383b58; padding-bottom: 10px;">
        ${hotel}
      </div>
 
      <div style="font-size: 14px; color: grey;">
      <span style="font-weight: 700;">Dirección:</span> ${streetName} ${streetNumber}, ${city}, ${country}
      </div>
    </div>`;

  // Icono personalizado del marker
  const image =
    "data:image/png;base64,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";

  // Función para agregar un marker y una ventana de información en el mapa
  const handleApiLoaded = (map, maps, place) => {
    const marker = new maps.Marker({
      position: {
        lat: place.lat,
        lng: place.lng,
      },
      map,
      title: hotel,
      icon: image,
      animation: maps.Animation.BOUNCE,
    });

    const infowindows = new maps.InfoWindow({
      content: getInfoWindowString(place),
    });

    //Escucha al click del marker para abrir o cerrar ventana de información
    marker.addListener("click", () => {
      infowindows.open(map, marker);

      if (marker.getAnimation() !== null) {
        marker.setAnimation(null);
      } else {
        marker.setAnimation(maps.Animation.BOUNCE);
        infowindows.close();
      }
    });

    infowindows.addListener("closeclick", () => {
      marker.setAnimation(maps.Animation.BOUNCE);
    });
  };

  return (
    <div className="ContainerMapGeneral">
      <div className="ContainerTitleMap">
        <h2>¿Dónde vas a estar?</h2>
      </div>
      <p>{city}, {country}</p>
      <div className="MapContainer">
        <GoogleMap
          bootstrapURLKeys={{ key: "AIzaSyBFIXEhFJoeq28Kf21uO5SJJkZtNCdz4GU" }}
          defaultCenter={place}
          defaultZoom={zoom}
          yesIWantToUseGoogleMapApiInternals
          onGoogleApiLoaded={({ map, maps }) =>
            handleApiLoaded(map, maps, place)
          }
        />
      </div>
    </div>
  );
}

export default Map;
