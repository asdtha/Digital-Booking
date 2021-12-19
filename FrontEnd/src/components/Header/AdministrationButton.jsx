import React from "react";
import "./AdministrationButton.css";

function AdministrationButton({name}) {
  function validateDisplay() {
    let x = document.getElementById("myLinks");
    x.style.animation = "menuInactive 0.75s";
    setTimeout(() => {
      x.style.display = "none";
    }, 500);
  }
  return <button className="ButtonAdmin" onClick={validateDisplay}>{name}</button>;
}

export { AdministrationButton };
