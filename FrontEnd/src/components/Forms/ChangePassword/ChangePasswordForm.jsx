import React from "react";
import useChangePasswordForm from "./useChangePasswordForm";
import validate from "./validateNewPassword";
import { Link } from "react-router-dom";
import spinner from "../../../icons/Ellipsis.svg";
import "../../Forms/Forms.css";

const ChangePasswordForm = ({ submitForm }) => {
  const {
    handleChange,
    values,
    handleSubmit,
    errors,
    classSpinner,
    statusErrors,
    statusChangePassword,
  } = useChangePasswordForm(submitForm, validate);

  return (
    <div className="form-container">
      <form className="Form" onSubmit={handleSubmit} noValidate>
        {statusErrors && <p className="service-error">{statusErrors}</p>}

        {statusChangePassword && (
          <p className="TextSendEmail">
            <i className="fas fa-check-circle"></i>
            {statusChangePassword}
          </p>
        )}
        <h1 className="title">Restablecer contraseña</h1>

        <label htmlFor="emailLogin" className="form-label">
          Nueva contraseña
        </label>
        <input
          id="newPassword"
          type="password"
          name="newPassword"
          className="form-input form-input-password"
          value={values.newPassword}
          onChange={handleChange}
          style={errors.newPassword && { border: "1px solid red" }}
        />
        {errors.newPassword && (
          <p className="input-error">{errors.newPassword}</p>
        )}

        <label htmlFor="passwordLogin" className="form-label">
          Confirmar contraseña
        </label>
        <input
          id="repeatPassword"
          type="password"
          name="repeatPassword"
          className="form-input form-input-password"
          value={values.repeatPassword}
          onChange={handleChange}
          style={errors.repeatPassword && { border: "1px solid red" }}
        />

        {errors.repeatPassword && (
          <p className="input-error">{errors.repeatPassword}</p>
        )}

        <div className={classSpinner}>
          <img src={spinner} alt="logo" />
        </div>

        <button type="submit" className="form-input-btn">
          Cambiar mi contraseña
        </button>
      </form>
    </div>
  );
};

export default ChangePasswordForm;
