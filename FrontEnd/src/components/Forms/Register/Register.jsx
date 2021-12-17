import React from "react";
import { Link } from "react-router-dom";
import validateRegister from "./validateRegister";
import useRegisterForm from "./useRegisterForm";
import spinner from "../../../icons/Ellipsis.svg"
import "../../Forms/Forms.css";

const Register = ({ submitRegisterForm }) => {
  const { handleChange, values, handleSubmit, errors, statusErrors, classSpinner } =
    useRegisterForm(submitRegisterForm, validateRegister);

  return (
    <div className="form-container">
      <form onSubmit={handleSubmit} className="Form" noValidate>
        {statusErrors && <p className="service-error">{statusErrors}</p>}
        <h1 className="title"> Crear cuenta </h1>

        <div className="FormNameLastname">
          <div className="ContainerInputNameLastname">
            <label htmlFor="nombre" className="form-label">
              Nombre
            </label>
            <input
              id="name"
              type="text"
              name="nombre"
              className="InputNameLastname"
              value={values.nombre}
              onChange={handleChange}
              style={errors.nombre && {border: "1px solid red"}}
            />
            {errors.nombre && <p className="input-error">{errors.nombre}</p>}
          </div>

          <div className="ContainerInputNameLastname">
            <label htmlFor="apellido" className="form-label">
              Apellido
            </label>
            <input
              id="apellido"
              type="text"
              name="apellido"
              className="InputNameLastname"
              value={values.apellido}
              onChange={handleChange}
              style={errors.apellido && {border: "1px solid red"}}
            />
            {errors.apellido && (
              <p className="input-error">{errors.apellido}</p>
            )}
          </div>
        </div>

        <label htmlFor="emailRegister" className="form-label">
          Correo electrónico
        </label>
        <input
          id="emailRegister"
          type="email"
          name="emailRegister"
          className="form-input form-input-email"
          value={values.emailRegister}
          onChange={handleChange}
          style={errors.emailRegister && {border: "1px solid red"}}
        />
        {errors.emailRegister && (
          <p className="input-error">{errors.emailRegister}</p>
        )}

        <label htmlFor="password" className="form-label">
          Contraseña
        </label>
        <input
          id="password"
          type="password"
          name="password"
          className="form-input form-input-password"
          value={values.password}
          onChange={handleChange}
          style={errors.password && {border: "1px solid red"}}
        />
        {errors.password && <p className="input-error">{errors.password}</p>}

        <label htmlFor="password-confirmation" className="form-label">
          Confirmar contraseña
        </label>
        <input
          id="password-confirmation"
          type="password"
          name="passwordConfirmation"
          className="form-input form-input-password"
          value={values.passwordConfirmation}
          onChange={handleChange}
          style={errors.passwordConfirmation && {border: "1px solid red"}}
        />
        {errors.passwordConfirmation && (
          <p className="input-error">{errors.passwordConfirmation}</p>
        )}

        <div className={classSpinner}>
          <img src={spinner} alt="logo" />
        </div>

        <button type="submit" className="form-input-btn">
          Crear cuenta
        </button>
      </form>
      <p className="TextRegister">
        ¿Ya tienes cuenta?{" "}
        <Link to="/login" className="Ref">
          Iniciar sesión
        </Link>
      </p>
    </div>
  );
};

export default Register;
