import React, { useRef, useState } from "react";
import { Calendar, DateObject } from "react-multi-date-picker";
import "./AvailableProductDates.css";
import useMediaQuery from "../../hooks/useMediaQuery";
import { Link } from "react-router-dom";

function AvailableProductDates({ id, dates }) {
  const datePickerRef = useRef();
  const weekDays = ["S", "M", "T", "W", "T", "F", "S"];

  const getDaysArray = function (start, end) {
    for (var arr = [], dt = start; dt <= end; dt.setDate(dt.getDate() + 1)) {
      arr.push(new Date(dt));
    }
    return arr;
  };

  let arrayDate = [];

  dates.map((dateInterval) => {
    let checkIn = new Date(dateInterval.checkIn);
    let startDate = checkIn.setDate(checkIn.getDate() + 1);
    let checkOut = dateInterval.checkOut;

    let daylist = getDaysArray(new Date(startDate), new Date(checkOut));
    return daylist.map(
      (v) => (arrayDate = [...arrayDate, new DateObject(v).format()])
    );
  });

  return (
    <div className="ContainerAvailableDates">
      <h2>Fechas disponibles</h2>
      <div className="ContainerCalendarButton">
        <Calendar
          numberOfMonths={useMediaQuery("(max-width: 706px)") ? 1 : 2}
          disableMonthPicker
          disableYearPicker
          minDate={new Date()}
          hideYear
          weekDays={weekDays}
          ref={datePickerRef}
          mapDays={({ date }) => {
            if (arrayDate.includes(date.format()))
              return {
                disabled: true,
                style: {
                  textDecoration: "line-through",
                  color: "#8798ad",
                },
              };
          }}
        />
        <div className="ContainerStartBooking">
          <p>Agrega tus fechas de viaje para obtener precios exactos</p>
          <Link to={`/product/${id}/booking`}>
            <button className="ContainerStartBookingButton">
              Iniciar reserva
            </button>
          </Link>
        </div>
      </div>
    </div>
  );
}

export default AvailableProductDates;
