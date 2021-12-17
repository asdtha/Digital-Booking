import React, { useState } from "react";
import Login from "./Login";
import { Redirect } from "react-router-dom";

const Forms = () => {
  const [isSubmitted, setIsSubmitted] = useState(false);

  let localId = JSON.parse(localStorage.getItem("idProduct"));

  let infoLocal = localStorage.getItem("infoBooking");

  function submitForm() {

    setIsSubmitted(true);
  }
  return (
    <>
      {isSubmitted && infoLocal ? (
        <Redirect to={`/product/${localId}/booking`} />
      ) : isSubmitted && localStorage.getItem("userLogin") ? (
        <Redirect to="/home" />
      ) : (
        <Login submitLoginForm={submitForm} />
      )}
    </>
  );
};
export default Forms;
