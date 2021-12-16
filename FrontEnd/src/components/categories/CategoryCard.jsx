import { useContext } from "react";
import filterContext from "../../context/filterContext";
import "./CategoryCard.css";
export default function CategoryCard({ img, title, amount }) {
  const { setFilter } = useContext(filterContext);

  return (
    <div
      className="ContainerCardType"
      onClick={() => setFilter([title, "category"])}
    >
      <img className="ImgType" src={img} alt="Foto descriptiva del hotel" />
      <div className="TextCard">
        <h3>{title}</h3>
        <p>{amount} Hoteles</p>
      </div>
    </div>
  );
}
