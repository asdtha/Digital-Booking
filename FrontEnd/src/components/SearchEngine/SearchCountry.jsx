import { useState } from "react";
import "./SearchCountry.css";
import useFetch from "../../hooks/useFetch";

export default function Buscador() {
  //const URL_API = "http://localhost:8080/API/cities/allCities";
  const URL_API = "http://digitalbooking.click:8080/API/cities/allCities";

  const { data } = useFetch(URL_API, {});
  const [stateCity, setCity] = useState("¿A dónde vamos?");

  const showDropDown = () => {
    document.getElementById("dropdownCity").classList.toggle("show");
  };

  const handleChange = (e) => {
    let arrayCityCountry = e.target.innerText.split(",");
    setCity(`${arrayCityCountry[0]}, ${arrayCityCountry[1]}`);
    showDropDown();
  };

  return data ? (
    <div>
      <input
        value={stateCity}
        className="Country"
        onClick={showDropDown}
        readOnly
      />
      <div className="dropdown-content" id="dropdownCity">
        {data.map((city) => {
          return (
            <div key={city.id} onClick={handleChange} id="city">
              <p className="TextDropdownCity">
                {city.name}
                {","}
                <span
                  onClick={(e) => e.stopPropagation()}
                  className="TextDropdownCountry"
                >
                  {city.country_name}
                </span>
              </p>
            </div>
          );
        })}
      </div>
    </div>
  ) : null;
}
