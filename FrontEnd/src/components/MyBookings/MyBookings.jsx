import React from "react";
import { useParams } from "react-router-dom";
import useFetch from "../../hooks/useFetch";
import ProductCard from "../Product/ProductCard";
import ProductHeading from "../ProductDetail/ProductHeading";

function MyBookings() {
  let { idUser } = useParams();

  //const URL_API = "http://localhost:8080/API/products/allProducts ";
  const URL_API = "http://digitalbooking.click:8080/API/products/allProducts ";
  //const URL_API_BOOKINGS = `http://localhost:8080/API/bookings/byUser/${idUser}`;
  const URL_API_BOOKINGS = `http://digitalbooking.click:8080/API/bookings/byUser/${idUser}`;
  //const URL_API_FAVORITES_GET =
    //"http://localhost:8080/API/favorites/allFavorites";
  const URL_API_FAVORITES_GET =
    "http://digitalbooking.click:8080/API/favorites/allFavorites";
  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  const { data: dataBookingUser } = useFetch(URL_API_BOOKINGS, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${userLoginParse.jwt}`,
    },
  });

  const { data: dataFavorites } = useFetch(URL_API_FAVORITES_GET, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${userLoginParse.jwt}`,
    },
  });

  const { data } = useFetch(URL_API, {});

  const filteredArray =
    data && dataBookingUser
      ? data.filter((product) =>
          dataBookingUser.map((booking) => booking.product).includes(product.id)
        )
      : data;
  return filteredArray ? (
    <>
      <ProductHeading title="Mis reservas" category="" redirectPage="/home" />
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
            <i className="far fa-calendar-times"></i>
            <p>Aún no has efectuado ninguna reserva</p>
          </div>
        )}
      </div>
    </>
  ) : null;
}

export { MyBookings };
