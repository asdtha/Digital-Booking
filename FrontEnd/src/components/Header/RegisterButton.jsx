import React, { useState, useEffect } from "react";
import "./SessionButtons.css";
import { useLocation } from "react-router-dom";

function RegisterButton() {
  const [stateClass, setClass] = useState("Buttons");

  const location = useLocation();
  const locationPath = location.pathname;

  function validateDisplay() {
    let x = document.getElementById("myLinks");
    x.style.animation = "menuInactive 0.75s";
    setTimeout(() => {
      x.style.display = "none";
    }, 500);
  }

  const validation = () =>
    locationPath === "/register" || localStorage.getItem("userLogin")
      ? setClass("HideButton")
      : setClass("Buttons");

  useEffect(() => {
    validation();
  });

  return <button className={stateClass} onClick={validateDisplay}>Crear cuenta</button>;
}

export default RegisterButton;
