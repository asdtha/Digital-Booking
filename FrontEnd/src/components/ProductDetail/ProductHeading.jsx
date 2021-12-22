import React from "react";
import { Link } from "react-router-dom";
import "./ProductHeading.css";

function ProductHeading({category, title, redirectPage}) {
  return (
    <div className="ContainerHeadingProduct">
      <div>
        <p className="TitleCategory">{category && category.toUpperCase()}</p>
        <h2>{title}</h2>
      </div>

      <Link to={redirectPage}>
        <button className="ButtonHeading">
          <i className="fas fa-chevron-left"></i>
        </button>
      </Link>
    </div>
  );
}

export default ProductHeading;
