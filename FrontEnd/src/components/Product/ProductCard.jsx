import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./ProductCard.css";

function ProductCard({
  img,
  category,
  title,
  location,
  description,
  id,
  features,
  puntuation,
  dataFavorites,
}) {
  /**
   * Puntuación de cada producto
   */
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

  const scrollTop = () => {
    let element = document.querySelector("main");
    element.scrollTop = 0;
  };

  /**
   * Implementación de favoritos.
   */

  const [stateIdFavorite, setIdFavorite] = useState(() => {
    if (dataFavorites) {
      let arrayFavoritesFilter = dataFavorites.filter(
        (favorite) => favorite.productId === id
      );

      return arrayFavoritesFilter[0] ? arrayFavoritesFilter[0].id : "";
    }
  });

  const URL_API_FAVORITES_POST = `http://localhost:8080/API/favorites/${id}`;
  //const URL_API_FAVORITES_POST = `http://digitalbooking.click:8080/API/favorites/${id}`;
  const URL_API_FAVORITES_DELETE = `http://localhost:8080/API/favorites/delete/${stateIdFavorite}`;
  //const URL_API_FAVORITES_DELETE = `http://digitalbooking.click:8080/API/favorites/delete/${stateIdFavorite}`;

  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  const [stateFavorite, setFavorite] = useState(() => {
    if (dataFavorites) {
      let arrayFavorites = dataFavorites.map((favorite) => favorite.productId);
      return arrayFavorites.includes(id) ? true : false;
    }

    if (localStorage.getItem("arrayFavorites") && !userLoginParse) {
      let arrayLocal = JSON.parse(localStorage.getItem("arrayFavorites"));
      return arrayLocal.includes(id) ? true : false;
    }
  });

  const setStateFavorite = () => {
    let localArray = localStorage.getItem("arrayFavorites");

    //Cambiar estado de favorito en localStorage cuando no hay usuario logueado
    if (!userLoginParse) {
      if (localArray) {
        let newArray = JSON.parse(localArray);

        if (newArray.includes(id)) {
          setFavorite(false);
          let arrayFilter = newArray.filter((element) => element !== id);
          localStorage.setItem("arrayFavorites", JSON.stringify(arrayFilter));
        } else {
          newArray = [...newArray, id];
          localStorage.setItem("arrayFavorites", JSON.stringify(newArray));
          setFavorite(true);
        }
      } else {
        localStorage.setItem("arrayFavorites", JSON.stringify([id]));
        setFavorite(true);
      }
    }

    //Cambiar estado de favorito en localStorage cuando hay un usuario logueado
    stateFavorite ? setFavorite(false) : setFavorite(true);
  };

  useEffect(() => {
    if (userLoginParse) {
      if (
        stateFavorite &&
        !dataFavorites.map((favorite) => favorite.productId).includes(id)
      ) {
        const requestOptions = {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${userLoginParse.jwt}`,
          },
        };

        fetch(URL_API_FAVORITES_POST, requestOptions)
          .then((response) => {
            if (response.status !== 200) {
              throw Error(response.statusText);
            } else {
              return response.json();
            }
          })
          .then((data) => (data ? setIdFavorite(data.id) : ""));
      } else if (!stateFavorite && stateIdFavorite) {
        const requestOptions = {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${userLoginParse.jwt}`,
          },
        };

        fetch(URL_API_FAVORITES_DELETE, requestOptions).then((response) => {
          if (response.status === 200) {
          } else {
            throw Error(response.statusText);
          }
        });
      }
    }
  }, [stateFavorite]);

  return (
    <div className="ContainerCard">
      <div className="ImgContainer">
        <button className="HeartIcon" onClick={setStateFavorite}>
          {!stateFavorite ? (
            <i className="far fa-heart"></i>
          ) : (
            <i className="fas fa-heart"></i>
          )}
        </button>
        <img className="Img" src={img} alt="foto de hotel" />
      </div>
      <div className="ContainerDescription">
        <div className="ContainerCategoryScore">
          <div>
            <div className="ContainerCategory">
              <h4 className="TextCategoryProduct">{category.toUpperCase()}</h4>
              <div className="StarIcon">
                {createArrayStars().map((star, index) =>
                  star === 1 ? (
                    <i className="fas fa-star" key={index}></i>
                  ) : (
                    <i className="far fa-star" key={index}></i>
                  )
                )}
              </div>
            </div>
            <h2 className="Title">{title}</h2>
          </div>
          <div className="ContainerScore">
            <div className="Score">
              <h2 className="ScoreNumber">{score >= 0 ? score : "—"}</h2>
            </div>
            <h4 className="ScoreText">{textScore}</h4>
          </div>
        </div>
        <div className="ContainerLocation">
          <i className="fas fa-map-marker-alt"></i>
          <p className="TextLocation">
            {location} <span className="Span">MOSTRAR EN EL MAPA</span>
          </p>
        </div>
        <div className="ContainerIcons">
          {features.map((feature) => {
            return <i key={feature.id} className={feature.icon}></i>;
          })}
        </div>
        <div className="ContainerDescButton">
          <div className="Description">
            <p className="DescriptionText">{description}</p>
          </div>
          <Link to={`/product/${id}`}>
            <button className="Button" onClick={scrollTop}>
              Ver más
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
}

export default ProductCard;
