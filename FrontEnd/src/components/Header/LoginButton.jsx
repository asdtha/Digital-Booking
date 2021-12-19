import React, { useState, useEffect } from "react";
import "./SessionButtons.css";
import { useLocation } from "react-router-dom";

function LoginButton() {
  const [stateClass, setClass] = useState("Buttons");

  const location = useLocation();
  const locationPath = location.pathname;

  function validateDisplay() {

    localStorage.removeItem("infoBooking")
    localStorage.removeItem("infoRegister")

    let x = document.getElementById("myLinks");
    x.style.animation = "menuInactive 0.75s";
      setTimeout(() => {
        x.style.display = "none";
      }, 500);
  }

  const validation = () =>
    locationPath === "/login" || localStorage.getItem("userLogin")
      ? setClass("HideButton")
      : setClass("Buttons");

  useEffect(() => {
    validation();
  });

  return (
    <button className={stateClass} onClick={validateDisplay}>
      Iniciar sesión
    </button>
  );
}

export default LoginButton;
