import React from "react";
import "./ProductLocation.css";

function ProductLocation({ city, country, puntuation }) {
  const score = Math.round(puntuation * 10) / 10;
  const puntuationToText = [
    "Sin votos",
    "Aceptable",
    "Aceptable",
    "Aceptable",
    "Aceptable",
    "Aceptable",
    "Aceptable",
    "Agradable",
    "Bien",
    "Muy bien",
    "Fantastico",
    "Excepcional",
  ];
  const textScore = puntuationToText[Math.round(score) + 1];

  const scoreStars = Math.floor(Math.round(score) / 2);

  let arrayStars = [0, 0, 0, 0, 0];

  const createArrayStars = () => {
    return arrayStars.map((star, index) =>
      scoreStars >= index + 1 ? star + 1 : 0
    );
  };

  return (
    <div className="ContainerLocationDetail">
      <div className="TextLocationDetail">
        <i className="fas fa-map-marker-alt"></i>
        <p>
          {city}, {country}
        </p>
      </div>
      <div className="ContainerScoreDetail">
        <div className="ContainerTextStars">
          <h4>{textScore}</h4>
          <div className="ContainerStarsDetail">
            {createArrayStars().map((star, index) =>
              star === 1 ? (
                <i className="fas fa-star" key={index}></i>
              ) : (
                <i className="far fa-star" key={index}></i>
              )
            )}
          </div>
        </div>
        <div className="ScoreDetail">
          <h2>{score >= 0 ? score : "—"}</h2>
        </div>
      </div>
    </div>
  );
}

export default ProductLocation;
