import React, { useState } from "react";
import useFetch from "../../hooks/useFetch";
import ProductHeading from "../ProductDetail/ProductHeading";
import { useParams } from "react-router-dom";
import { BookingDetail } from "./BookingDetail";
import ProductPolicy from "../ProductDetail/ProductPolicy";
import { BookingInputs } from "./BookingInputs";
import { BookingCalendar } from "./BookingCalendar";
import "./ProductBooking.css";
import { BookingArriveTime } from "./BookingArriveTime";
import datesContext from "../../context/datesContext";
import { DateObject } from "react-multi-date-picker";
import { useEffect } from "react/cjs/react.development";
import { Redirect } from "react-router-dom";
import spinner from "../../icons/Ellipsis.svg";

function ProductBooking() {
  localStorage.removeItem("infoBooking");

  let { id } = useParams();

  //const URL_API = `http://localhost:8080/API/products/${id}`;
  const URL_API = `http://digitalbooking.click:8080/API/products/${id}`;
  //const URL_API_POST = "http://localhost:8080/API/bookings";
  const URL_API_POST = "http://digitalbooking.click:8080/API/bookings";

  const { data } = useFetch(URL_API, {});

  const [stateBooking, setstateBooking] = useState(false);
  const [statusErrors, setStatusErrors] = useState();
  const [submit, setSubmit] = useState(false);
  const [errors, setErrors] = useState({});
  const [classSpinner, setClassSpinner] = useState(
    "SpinnerBooking SpinnerBookingHidden"
  );

  /**
   * Se consulta en localStorage si el usuario ingreso fechas (Check in - Check out) de busqueda en el home
   * y se guardan como array en stateDates.
   */
  const storage = JSON.parse(localStorage.getItem("dates"));
  const storageArray = storage ? storage.split("~") : "";
  const [stateDates, setDates] = useState(
    storageArray.length !== 0
      ? [
          new DateObject(storageArray[0].trim()),
          new DateObject(storageArray[1].trim()),
        ]
      : [new DateObject(), new DateObject()]
  );

  /**
   * Se consulta en local storage la información del usuario logueado
   */
  const userLocal = localStorage.getItem("userLogin")
    ? JSON.parse(localStorage.getItem("userLogin"))
    : null;

  const [payload, setPayload] = useState({
    product: { id: "" },
    startingHour: "",
    checkInDate: "",
    checkOutDate: "",
    user: { id: "" },
  });

  /**
   * Validaciones del formulario de reserva
   */
  const validateFormBooking = (
    startingHour,
    city,
    checkInDate,
    checkOutDate
  ) => {
    let errors = {};

    if (startingHour === "Seleccionar hora de llegada") {
      errors.time = "Este campo es obligatorio";
    }
    if (!city) {
      errors.city = "Este campo es obligatorio";
    }
    if (!checkInDate) {
      errors.checkIn = "Seleccione una fecha de llegada";
    }
    if (!checkOutDate) {
      errors.checkOut = "Seleccione una fecha de salida";
    }

    return errors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    setStatusErrors();

    let startingHour = e.target[4].value;
    let arraySplit = startingHour.split(":");
    let hourToNumber = "";
    let city = e.target[3].value;
    let checkInDate = e.target[5].value;
    let checkOutDate = e.target[6].value;

    if (startingHour.includes("PM")) {
      hourToNumber = Number(arraySplit[0]);
      hourToNumber = hourToNumber + 12;
    } else {
      hourToNumber = Number(arraySplit[0]);
    }

    setErrors(
      validateFormBooking(startingHour, city, checkInDate, checkOutDate)
    );

    setPayload({
      ...payload,
      product: { id: id },
      startingHour: hourToNumber,
      checkInDate: stateDates[0].format("YYYY-MM-DD"),
      checkOutDate: stateDates[1].format("YYYY-MM-DD"),
      user: { id: userLocal.id },
    });

    setSubmit(true);
  };

  useEffect(() => {
    if (submit && Object.keys(errors).length === 0) {
      setClassSpinner("SpinnerBooking");

      const requestOptions = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${userLocal.jwt}`,
        },
        body: JSON.stringify(payload),
      };

      setTimeout(() => {
        fetch(URL_API_POST, requestOptions).then((response) => {
          if (response.status === 201) {
            setstateBooking(true);
            setClassSpinner("SpinnerBooking SpinnerBookingHidden");
          } else {
            setStatusErrors(
              "Lamentablemente la reserva no ha podido realizarse. Por favor, intente más tarde"
            );

            setClassSpinner("SpinnerBooking SpinnerBookingHidden");
          }
        });
      }, 2000);
    }
  }, [errors, submit]);

  return data ? (
    <>
      {stateBooking ? (
        <Redirect to={`/product/${id}/booking/successful`} />
      ) : (
        <>
          <ProductHeading
            category={data.category.name}
            title={data.name}
            redirectPage={`/product/${id}`}
          />

          <datesContext.Provider value={{ stateDates, setDates }}>
            <form className="FormBooking" onSubmit={handleSubmit} noValidate>
              <div className="ContainerFormLeft">
                <BookingInputs errors={errors} />
                <BookingCalendar dates={data.bookingList} />
                <BookingArriveTime errors={errors} />
              </div>

              <BookingDetail
                id={data.id}
                img={data.images[0].url}
                category={data.category.name.toUpperCase()}
                title={data.name}
                streetName={data.streetName}
                streetNumbre={data.streetNumber}
                city={data.city.name}
                country={data.city.country_name}
                errors={errors}
              />
              {statusErrors && (
                <p className="service-error-booking">{statusErrors}</p>
              )}
              <div className={classSpinner}>
                <img src={spinner} alt="logo" />
              </div>
            </form>
          </datesContext.Provider>

          <ProductPolicy houseRulesPolicy={data.houseRulesPolicy} healthAndSafetyPolicy={data.healthAndSafetyPolicy} cancelationPolicy={data.cancelationPolicy}/>
        </>
      )}
    </>
  ) : null;
}

export { ProductBooking };
