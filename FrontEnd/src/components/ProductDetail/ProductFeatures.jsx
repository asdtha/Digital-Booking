import React from "react";
import "./ProductFeatures.css";

function ProductFeatures({ features }) {
  return (
    <div className="ContainerProductFeatures">
      <div className="ContainerTitleFeatures">
        <h2>¿Qué ofrece este lugar?</h2>
      </div>
      <div className="ContainerFeaturesAndIcons">
        {features.map((feature) => {
          return (
            <div key={feature.id}>
              <i className={feature.icon}></i>
              <p>{feature.name}</p>
            </div>
          );
        })}
      </div>
    </div>
  );
}

export default ProductFeatures;
