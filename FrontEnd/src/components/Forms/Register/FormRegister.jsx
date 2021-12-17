import React, { useState } from "react";
import Register from "./Register";
import { Redirect } from "react-router-dom";

const FormRegister = () => {
  const [registerIsSubmitted, setRegisterIsSubmitted] = useState(false);

  function submitForm(email) {
    JSON.stringify(
      localStorage.setItem(
        "infoRegister",
        "Se ha enviado un correo a: " +
          email + 
          ", por favor, siga los pasos para completar su registro, de lo contrario no podrá iniciar sesión. "
      )
    );
    setRegisterIsSubmitted(true);
  }
  return (
    <>
      {registerIsSubmitted || localStorage.getItem("userLogin") ? (
        <Redirect to="/login" />
      ) : (
        <Register submitRegisterForm={submitForm} />
      )}
    </>
  );
};

export default FormRegister;
