import { useEffect, useState, useContext } from "react";
import { useLocation } from "react-router-dom";
import { DateObject } from "react-multi-date-picker";
import filterContext from "../../context/filterContext";
import "./SearchBlock.css";
import SearchCalendar from "./SearchCalendar.jsx";
import SearchCountry from "./SearchCountry.jsx";

export default function SearchBlock() {
  const [stateClass, setClass] = useState("ContainerSearch");
  const { setFilter } = useContext(filterContext);

  const location = useLocation();
  const locationPath = location.pathname;

  const validation = () =>
    locationPath === "/home"
      ? setClass("ContainerSearch")
      : setClass("HiddenSearchBlock");

  const getDaysArray = (start, end) => {
    for (var arr = [], dt = start; dt <= end; dt.setDate(dt.getDate() + 1)) {
      arr.push(new Date(dt));
    }
    return arr;
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    let arrayCityCountry = e.target[0].value.split(",");

    let arrayDate = [];

    if (e.target[1].value) {
      let arrayDates = e.target[1].value.split("~");

      let checkIn = new Date(arrayDates[0].trim());

      let checkOut = new Date(arrayDates[1].trim());
      let endDate = checkOut.setDate(checkOut.getDate() - 1);

      let daylist = getDaysArray(new Date(checkIn), new Date(endDate));
      daylist.map(
        (v) =>
          (arrayDate = [...arrayDate, new DateObject(v).format("YYYY-MM-DD")])
      );
    }

    if (e.target[0].value !== "¿A dónde vamos?" && !e.target[1].value) {
      setFilter([arrayCityCountry[0], "city"]);
    } else if (e.target[1].value && e.target[0].value === "¿A dónde vamos?") {
      setFilter([arrayDate, "dates"]);
    } else {
      setFilter([arrayCityCountry[0], arrayDate, "cityAndDates"]);
    }

    localStorage.setItem("dates", JSON.stringify(e.target[1].value));
  };

  useEffect(() => {
    validation();
  });

  return (
    <form className={stateClass} onSubmit={handleSubmit}>
      <h1 className="TitleSearch">
        Busca ofertas en hoteles, hostales y mucho más
      </h1>
      <div className="FilterSearch">
        <SearchCountry />
        <SearchCalendar />
        <button type="submit" className="search-button">
          Buscar
        </button>
      </div>
    </form>
  );
}
