import React, { useState } from "react";
import ProductCard from "../Product/ProductCard";
import "./FavoriteProductsList.css";
import useFetch from "../../hooks/useFetch";
import ProductHeading from "../ProductDetail/ProductHeading";

function FavoriteProductsList() {
  const URL_API = "http://localhost:8080/API/products/allProducts ";
  //const URL_API = "http://digitalbooking.click:8080/API/products/allProducts ";
 
  const URL_API_FAVORITES_GET =
    "http://localhost:8080/API/favorites/allFavorites";
  //const URL_API_FAVORITES_GET =
    //"http://digitalbooking.click:8080/API/favorites/allFavorites";

  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  const { data: dataFavorites } = useFetch(URL_API_FAVORITES_GET, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${userLoginParse.jwt}`,
    },
  });

  const { data } = useFetch(URL_API, {});

  const filteredArray =
    data && dataFavorites
      ? data.filter((product) =>
          dataFavorites
            .map((favorite) => favorite.productId)
            .includes(product.id)
        )
      : data;


  return filteredArray ? (
    <div className="ContainerGeneralFavorites">
      <ProductHeading title="Mis favoritos" category="" redirectPage="/home" />
      <div className="SectionContent">
        {filteredArray.map((product) => {
          return (
            <ProductCard
              key={product.id}
              img={product.images[0].url}
              category={product.category.name}
              title={product.name}
              location={product.city.name}
              description={product.description}
              features={product.characteristics}
              id={product.id}
              puntuation={product.puntuationCounterDTO.average}
              dataFavorites={dataFavorites}
            />
          );
        })}

        {filteredArray.length === 0 && (
          <div className="MessageNoData">
            <i className="fas fa-heart-broken"></i>
            <p>Aun no tienes favoritos</p>
          </div>
        )}
      </div>
    </div>
  ) : null;
}

export default FavoriteProductsList;
