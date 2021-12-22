import React from "react";
import { Link } from "react-router-dom";
import "./SuccessfulMessage.css";

function SuccessfulMessage({message}) {
  return (
    <div className="ContainerSuccessfulMessage">
      <h2 className="TitleSuccessfulMessage">¡Muchas gracias!</h2>
      <h4 className="DescriptionSuccessfulMessage">
        {message}
      </h4>
      <Link to={"/home"}>
        <button className="ButtonSuccessfulMessage">Volver</button>
      </Link>
    </div>
  );
}

export { SuccessfulMessage };
