import React, { useState } from "react";
import "./BookingArriveTime.css";

function BookingArriveTime({ errors }) {
  let time = [
    "01:00",
    "02:00",
    "03:00",
    "04:00",
    "05:00",
    "06:00",
    "07:00",
    "08:00",
    "09:00",
    "10:00",
    "11:00",
    "12:00",
  ];

  const [timeArrive, setTimeArrive] = useState("default");

  const handleChange = (event) => {
    setTimeArrive(event.target.value);
  };

  return (
    <div className="ContainerTimeBooking">
      <h2>Tu horario de llegada</h2>
      <div className="ContainerSelectTimeBooking">
        <div className="InfoTimeBooking">
          <i className="far fa-check-circle"></i>
          <p>
            Tu habitación va a estar lista para el check-in entre las 10:00 AM y
            las 11:00 PM
          </p>
        </div>
        <label className="LabelTimeBooking">
          Indicá tu horario estimado de llegada
          <select
            value={timeArrive}
            onChange={handleChange}
            className="SelectTimeBooking"
            style={errors.time && {border: "1px solid red"}}
          >
            <option defaultValue="default" style={{ color: "#BEBEBE" }}>
              Seleccionar hora de llegada
            </option>
            {time.map((element) => (
              <option key={`${element}AM`} value={`${element} AM`}>
                {element} AM
              </option>
            ))}
            {time.map((element) => (
              <option key={`${element}PM`} value={`${element} PM`}>
                {element} PM
              </option>
            ))}
          </select>
          {errors.time && <p className="input-error-time">{errors.time}</p>}
        </label>
      </div>
    </div>
  );
}

export { BookingArriveTime };
