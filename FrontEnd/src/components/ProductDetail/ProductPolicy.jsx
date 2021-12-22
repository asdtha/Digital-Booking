import React from "react";
import "./ProductPolicy.css";

function ProductPolicy({
  houseRulesPolicy,
  healthAndSafetyPolicy,
  cancelationPolicy,
}) {
  const houseRules = houseRulesPolicy.split(".");
  const healthAndSafety = healthAndSafetyPolicy.split(".");

  return (
    <div className="ContainerProductPolicy">
      <div className="ContainerTitlePolicy">
        <h2>Qué tienes que saber</h2>
      </div>
      <div className="ContainerPoliciyLists">
        <div>
          <h3>Normas del hotel</h3>
          <ul>
            {houseRules.map((rule) => (
              <li key={rule}>{rule.trim()}</li>
            ))}
          </ul>
        </div>
        <div>
          <h3>Salud y seguridad</h3>
          <ul>
            {healthAndSafety.map((rule) => (
              <li key={rule}>{rule.trim()}</li>
            ))}
          </ul>
        </div>
        <div>
          <h3>Política de cancelación</h3>
          <ul>
            <li key={cancelationPolicy}>{cancelationPolicy}</li>
          </ul>
        </div>
      </div>
    </div>
  );
}

export default ProductPolicy;
