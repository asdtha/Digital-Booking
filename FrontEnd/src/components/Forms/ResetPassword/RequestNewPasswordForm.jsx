import React from "react";
import useResetPassword from "./useResetPassword";
import validate from "./validateEmailResetPassword";
import { Link } from "react-router-dom";
import spinner from "../../../icons/Ellipsis.svg";
import "../../Forms/Forms.css";

const RequestNewPasswordForm = ({ submitForm }) => {
  const {
    handleChange,
    values,
    handleSubmit,
    errors,
    classSpinner,
    statusErrors,
    statusSendEmail
  } = useResetPassword(submitForm, validate);

  // let localInfo = localStorage.getItem("infoBooking");
  // let registerInfo = localStorage.getItem("infoRegister");

  return (
    <div className="form-container">
      <form className="Form" onSubmit={handleSubmit} noValidate>
        {statusErrors && !statusSendEmail && <p className="service-error">{statusErrors}</p>}

        {statusSendEmail  && <p className="TextSendEmail"><i className="fas fa-check-circle"></i>{statusSendEmail}</p>}


        <h1 className="title">¿Has olvidado tu contraseña?</h1>

        <p className="TextResetPassword">
          Ingresa tu dirección de correo electrónico y te enviaremos
          instrucciones para restablecer tu contraseña.
        </p>

        <label htmlFor="emailLogin" className="form-label">
          Correo electrónico
        </label>
        <input
          id="emaiResetPassword"
          type="email"
          name="email"
          className="form-input form-input-email"
          value={values.email}
          onChange={handleChange}
          style={errors.email && { border: "1px solid red" }}
        />
        {errors.email && <p className="input-error">{errors.email}</p>}

        <div className={classSpinner}>
          <img src={spinner} alt="logo" />
        </div>

        <button type="submit" className="form-input-btn">
          Enviar
        </button>
      </form>
    </div>
  );
};

export default RequestNewPasswordForm;
