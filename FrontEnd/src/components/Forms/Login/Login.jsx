import React from "react";
import useLoginForm from "./useLoginForm";
import validate from "./loginValidations";
import { Link } from "react-router-dom";
import spinner from "../../../icons/Ellipsis.svg";
import "../../Forms/Forms.css";

const Login = ({ submitLoginForm }) => {
  const {
    handleChange,
    values,
    handleSubmit,
    errors,
    classSpinner,
    statusErrors,
  } = useLoginForm(submitLoginForm, validate);

  let localInfo = localStorage.getItem("infoBooking");
  let registerInfo = localStorage.getItem("infoRegister");

  return (
    <div className="form-container">
      <form className="Form" onSubmit={handleSubmit} noValidate>
        {statusErrors && <p className="service-error">{statusErrors}</p>}

        {localInfo && !statusErrors && (
          <p className="info-booking">
            <i className="fas fa-exclamation-circle"></i>
            {localInfo}
          </p>
        )}

        {registerInfo && !statusErrors && !localInfo && (
          <p className="info-register">
            <i className="fas fa-exclamation-circle"></i>
            {registerInfo}
          </p>
        )}

        <h1 className="title">Iniciar sesión</h1>

        <label htmlFor="emailLogin" className="form-label">
          Correo electrónico
        </label>
        <input
          id="emailLogin"
          type="email"
          name="email"
          className="form-input form-input-email"
          value={values.email}
          onChange={handleChange}
          style={errors.email && { border: "1px solid red" }}
        />
        {errors.email && <p className="input-error">{errors.email}</p>}

        <label htmlFor="passwordLogin" className="form-label">
          Contraseña
        </label>
        <input
          id="passwordLogin"
          type="password"
          name="password"
          className="form-input form-input-password"
          value={values.password}
          onChange={handleChange}
          style={errors.password && { border: "1px solid red" }}
        />

        {errors.password && <p className="input-error">{errors.password}</p>}

        <div className={classSpinner}>
          <img src={spinner} alt="logo" />
        </div>

        <button type="submit" className="form-input-btn">
          Ingresar
        </button>
      </form>

      <p className="TextRegister">
        <Link to="/reset-password" className="Ref">
          ¿Has olvidado tu contraseña?
        </Link>
      </p>

      <p className="TextRegister">
        ¿Aún no tienes cuenta?{" "}
        <Link to="/register" className="Ref">
          {" "}
          Regístrate{" "}
        </Link>
      </p>
    </div>
  );
};

export default Login;
