import React, { useContext } from "react";
import { DateObject } from "react-multi-date-picker";
import datesContext from "../../context/datesContext";
import "./BookingDetail.css";

function BookingDetail({
  img,
  category,
  title,
  streetName,
  streetNumbre,
  city,
  country,
  id,
  errors,
}) {
  const { stateDates } = useContext(datesContext);

  return (
    <div className="ContainerBookingDetail">
      <div>
        <h2 className="TitleBookingDetail">Detalle de la reserva</h2>
        <img src={img} alt="Imagen de hotel" className="ImageBookingDetail" />
      </div>
      <div className="ContainerDescriptionBooking">
        <p className="CategoryBookingDetail">{category}</p>
        <h2 className="TitleNameBookingDetail">{title}</h2>
        <div className="StarsContainerBookingDetail">
          <i className="fas fa-star"></i>
          <i className="fas fa-star"></i>
          <i className="fas fa-star"></i>
          <i className="fas fa-star"></i>
          <i className="fas fa-star"></i>
        </div>
        <div className="ContainerLocationBookingDetail">
          <i className="fas fa-map-marker-alt"></i>
          <p>
            {streetName} {streetNumbre}, {city}, {country}
          </p>
        </div>
        <div className="CheckBookingDetail" style={errors.checkIn && {borderBottom: "1px solid red"}}>
          <h4>Check in</h4>
          <input
            type=""
            required
            readOnly
            value={
              stateDates.length > 1 &&
              stateDates[0].format("DD/MM/YYYY") ===
                new DateObject().format("DD/MM/YYYY") &&
              stateDates[1].format("DD/MM/YYYY") ===
                new DateObject().format("DD/MM/YYYY")
                ? ""
                : stateDates[0].format("DD/MM/YYYY")
            }
            className="CheckBookingDetailInput"
          />
        </div>
        {errors.checkIn && (
          <p className="input-error-check">{errors.checkIn}</p>
        )}

        <div className="CheckBookingDetail" style={errors.checkOut && {borderBottom: "1px solid red"}}>
          <h4>Check out</h4>
          <input
            type=""
            required
            readOnly
            value={
              stateDates.length > 1 &&
              stateDates[1].format("DD/MM/YYYY") !==
                new DateObject().format("DD/MM/YYYY")
                ? stateDates[1].format("DD/MM/YYYY")
                : ""
            }
            className="CheckBookingDetailInput"
          />
        </div>
        {errors.checkOut && (
          <p className="input-error-check">{errors.checkOut}</p>
        )}

        <button className="ButtonBookingDetail">Confirmar reserva</button>
      </div>
    </div>
  );
}

export { BookingDetail };
