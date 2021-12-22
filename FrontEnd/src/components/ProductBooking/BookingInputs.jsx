import React from "react";
import "./BookingInputs.css";

function BookingInputs({ errors }) {
  const userLocal = localStorage.getItem("userLogin")
    ? JSON.parse(localStorage.getItem("userLogin"))
    : null;

  return (
    <div>
      <h2 className="TitleInputsBooking">Completa tus datos</h2>
      <div className="ContainerInputsBooking">
        <div className="InputsLabelsBooking">
          <label>
            Nombre
            <input
              type="text"
              value={userLocal.firstName}
              name="name"
              disabled
              className="InputsDisabledBooking"
            />
          </label>
          <label>
            Apellido
            <input
              type="text"
              value={userLocal.lastName}
              name="lastname"
              disabled
              className="InputsDisabledBooking"
            />
          </label>
        </div>

        <div className="InputsLabelsBooking">
          <label>
            Correo electrónico
            <input
              type="email"
              value={userLocal.email}
              name="email"
              disabled
              className="InputsDisabledBooking"
            />
          </label>
          <label>
            Ciudad
            <input
              type="text"
              name="city"
              className="InputCityBooking"
              placeholder="Ciudad"
              required
              style={errors.city && {border: "1px solid red"}}
            />
            {errors.city && <p className="input-error">{errors.city}</p>}
          </label>
        </div>
      </div>
    </div>
  );
}

export { BookingInputs };
