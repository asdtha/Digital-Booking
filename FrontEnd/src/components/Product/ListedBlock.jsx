import React, { useContext, useEffect, useState } from "react";
import "./ListedBlock.css";
import ProductCard from "./ProductCard";
import useFetch from "../../hooks/useFetch";
import filterContext from "../../context/filterContext";

function ListedBlock() {
  //const URL_API = "http://localhost:8080/API/products/allProducts ";
  const URL_API = "http://digitalbooking.click:8080/API/products/allProducts ";

  const { data } = useFetch(URL_API, {});

  const { stateFilter } = useContext(filterContext);

  const filteredArray =
    data && stateFilter[1] === "category"
      ? data.filter((product) => product.category.name === stateFilter[0])
      : data && stateFilter[1] === "city"
      ? data.filter((product) => product.city.name === stateFilter[0])
      : data && stateFilter[1] === "dates"
      ? data.filter(
          (product) =>
            !product.bookingList
              .map((interval) => stateFilter[0].includes(interval.checkIn))
              .filter((validate) => !(validate !== true))[0]
        )
      : data && stateFilter[2] === "cityAndDates"
      ? data.filter(
          (product) =>
            !product.bookingList
              .map((interval) => stateFilter[1].includes(interval.checkIn))
              .filter((validate) => !(validate !== true))[0] &&
            product.city.name === stateFilter[0]
        )
      : data;

  const [dataFavorites, setDataFavorites] = useState();
  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  //const URL_API_FAVORITES_GET =
    //"http://localhost:8080/API/favorites/allFavorites";
  const URL_API_FAVORITES_GET =
    "http://digitalbooking.click:8080/API/favorites/allFavorites";
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
    <section className="Section">
      <div className="SectionContent">
        <h2 className="TitleSection">Recomendaciones</h2>

       
          {filteredArray.length === 0 && (
            <h3 className="InfoFilter">
              Lo sentimos, no hemos encontrado resultados para tu busqueda.
            </h3>
          )}

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
   
      </div>
    </section>
  ) : null;
}

export default ListedBlock;
