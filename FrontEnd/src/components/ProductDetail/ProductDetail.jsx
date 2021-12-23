import React, { useEffect, useState } from "react";
import ProductHeading from "./ProductHeading";
import ProductLocation from "./ProductLocation";
import ProductImages from "./ProductImages";
import ProductDescription from "./ProductDescription";
import ProductFeatures from "./ProductFeatures";
import ProductPolicy from "./ProductPolicy";
import { useParams } from "react-router-dom";
import AvailableProductDates from "./AvailableProductDates";
import Map from "./Map";
import useFetch from "../../hooks/useFetch";

function ProductDetail() {
  let { id } = useParams();

  localStorage.setItem("idProduct", id);

  const URL_API = `http://localhost:8080/API/products/${id}`;
  //const URL_API = `http://digitalbooking.click:8080/API/products/${id}`;
  const { data } = useFetch(URL_API, {});

  const [dataFavorites, setDataFavorites] = useState();
  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  const URL_API_FAVORITES_GET =
    "http://localhost:8080/API/favorites/allFavorites";
  //const URL_API_FAVORITES_GET =
    //"http://digitalbooking.click:8080/API/favorites/allFavorites";
  useEffect(() => {
    if (userLoginParse) {
      
      if (userLoginParse.userType !== "ADMIN") {
        const requestOptions = {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${userLoginParse.jwt}`,
          },
        };
  
        fetch(URL_API_FAVORITES_GET, requestOptions)
          .then((response) => {
            if (response.status !== 200) {
              throw Error(response.statusText);
            } else {
              return response.json();
            }
          })
          .then((data) => (data ? setDataFavorites(data) : null));
      }
    }
  }, []);

  return data ? (
    <>
      <ProductHeading
        category={data.category.name}
        title={data.name}
        redirectPage="/home"
      />
      <ProductLocation city={data.city.name} country={data.city.country_name} puntuation={data.puntuationCounterDTO.average} />
      <ProductImages id={data.id} images={data.images} dataFavorites={dataFavorites} />
      <ProductDescription
        description={data.description}
        city={data.city.name}
      />
      <ProductFeatures features={data.characteristics} />
      <AvailableProductDates id={data.id} dates={data.bookingList} />
      <Map
        latitude={data.latitude}
        longitude={data.longitude}
        hotel={data.name}
        streetName={data.streetName}
        streetNumber={data.streetNumber}
        city={data.city.name}
        country={data.city.country_name}
      />
      <ProductPolicy houseRulesPolicy={data.houseRulesPolicy} healthAndSafetyPolicy={data.healthAndSafetyPolicy} cancelationPolicy={data.cancelationPolicy}/>
    </>
  ) : null;
}

export default ProductDetail;
