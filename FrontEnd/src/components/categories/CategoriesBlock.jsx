import CategoryCard from "./CategoryCard";
import "./CategoriesBlock.css";
import useFetch from "../../hooks/useFetch";

export default function CategoriesBlock() {
  const URL_API = "http://localhost:8080/API/category";
  //const URL_API = "http://digitalbooking.click:8080/API/category";

  const { data } = useFetch(URL_API, {});

  return data ? (
    <section className="PrincipalContainer">
      <div className="CategoriesSection">
        <h2 className="TextCategory">Buscar por tipo de alojamiento</h2>

        {data.map((category) => {
          return (
            <CategoryCard
              key={category.id}
              img={category.imageURL}
              title={category.name}
              amount="807.105"
            />
          );
        })}
      </div>
    </section>
  ) : null;
}
