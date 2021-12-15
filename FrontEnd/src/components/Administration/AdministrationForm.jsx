import React, { useState, useEffect } from "react";
import useFetch from "../../hooks/useFetch";
import ProductHeading from "../ProductDetail/ProductHeading";
import "./AdministrationForm.css";
import { Redirect } from "react-router-dom";
import validate from "./validateFormProduct";
import spinner from "../../icons/Ellipsis.svg";

function AdministrationForm() {
  //const URL_API_CATEGORY = "http://localhost:8080/API/category";
  const URL_API_CATEGORY = "http://digitalbooking.click:8080/API/category";
  //const URL_API_CITY = "http://localhost:8080/API/cities/allCities";
  const URL_API_CITY = "http://digitalbooking.click:8080/API/cities/allCities";
  //const URL_API_ATRIBUTES =
    //"http://localhost:8080/API/characteristics/allCharacteristics";
    const URL_API_ATRIBUTES =
    "http://digitalbooking.click:8080/API/characteristics/allCharacteristics";
  //const URL_API_POST = "http://localhost:8080/API/products";
  const URL_API_POST = "http://digitalbooking.click:8080/API/products";

  const { data: dataCity } = useFetch(URL_API_CITY, {});
  const { data: dataCategory } = useFetch(URL_API_CATEGORY, {});
  const { data: dataAtributes } = useFetch(URL_API_ATRIBUTES, {});

  const [stateCategory, setCategory] = useState({ categoryName: "", id: "" });
  const [stateCity, setCity] = useState({ cityName: "", id: "" });
  const [stateImages, setImages] = useState([]);
  const [stateAtributes, setAtributes] = useState([]);
  const [submit, setSubmit] = useState(false);
  const [stateCreateProduct, setCreateProduct] = useState(false);
  const [errors, setErrors] = useState({});
  const [statusErrors, setStatusErrors] = useState();
  const [classSpinner, setClassSpinner] = useState(
    "SpinnerCreateProduct SpinnerCreateProductHidden"
  );

  const [payload, setPayload] = useState({});

  const userLocal = localStorage.getItem("userLogin")
    ? JSON.parse(localStorage.getItem("userLogin"))
    : null;

  /**
   * Se cambia el color del nombre y del icono para indicar al usuario que ha seleccionado el atributo o no
   */

  const handleChangeCheckbox = (e) => {
    if (e.target.checked) {
      document.getElementById(`${e.target.id}${e.target.name}`).style.color =
        "#383b58";
      document.getElementById(`${e.target.id}-${e.target.name}`).style.color =
        "#383b58";

      setAtributes([
        ...stateAtributes,
        {
          id: Number(e.target.id),
        },
      ]);
    } else {
      document.getElementById(`${e.target.id}${e.target.name}`).style.color =
        "#BEBEBE";
      document.getElementById(`${e.target.id}-${e.target.name}`).style.color =
        "#BEBEBE";

      let arrayAtributes = stateAtributes.filter(
        (atribute) => atribute.id !== Number(e.target.id)
      );
      setAtributes(arrayAtributes);
    }
  };

  /**
   * Funciones para mostrar Dropdown de categorias de ciudades
   */
  const showDropDownCity = () => {
    document.getElementById("cityDropdown").classList.toggle("Show");
  };

  const showDropDownCategory = () => {
    document.getElementById("dropdownCategory").classList.toggle("Show");
  };

  /**
   * Se actualiza el estado del input de categoria cada vez que haya un cambio
   */
  const handleChangeCategory = (e) => {
    setCategory({
      ...stateCategory,
      categoryName: e.target.textContent,
      id: Number(e.target.id),
    });
  };

  /**
   * Se actualiza el estado del input de ciudad cada vez que haya un cambio
   */
  const handleChangeCity = (e) => {
    setCity({
      ...stateCity,
      cityName: e.target.textContent,
      id: Number(e.target.id),
    });
  };

  /**
   * Se agragan los objetos con las url de cada imagen al array de imagenes.
   */
  const handleChangeImages = (e) => {
    let urlImage = e.target.form.elements.imageUrl.value;

    if (urlImage) {
      setImages([
        ...stateImages,
        {
          title: "image",
          url: urlImage,
        },
      ]);

      document.getElementById("imageUrl").value = "";
    }
  };

  /**
   * Se elimina del array de imagenes la que el usuario seleccione al dar click en boton de eliminar.
   */
  const deleteImageOfArray = (e) => {
    let idImage = Number(e.target.id);
    let arrayFilter = stateImages.filter((image, index) => index !== idImage);
    setImages(arrayFilter);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    setStatusErrors();
    let address = e.target.elements.address.value.split(" ");

    let data = {
      name: e.target.elements.name.value,
      description: e.target.elements.description.value,
      category: e.target.elements.category.value,
      city: e.target.elements.city.value,
      latitude: Number(e.target.elements.latitude.value),
      longitude: Number(e.target.elements.longitude.value),
      address: e.target.elements.address.value,
      streetNumber: address.pop(),
      streetName: address.join(" "),
      cancelationPolicy: e.target.elements.cancellation.value,
      healthAndSafetyPolicy: e.target.elements.healthSafety.value,
      houseRules: e.target.elements.houseRules.value,
    };

    setErrors(validate(data, stateImages, stateAtributes));

    setPayload({
      ...payload,
      name: data.name,
      description: data.description,
      longitude: data.longitude,
      latitude: data.latitude,
      streetName: data.streetName,
      streetNumber: data.streetNumber,
      cancelationPolicy: data.cancelationPolicy,
      healthAndSafetyPolicy: data.healthAndSafetyPolicy,
      houseRulesPolicy: data.houseRules,
      city: {
        id: stateCity.id,
      },
      category: {
        id: stateCategory.id,
      },
      images: stateImages,
      characteristics: stateAtributes,
      earliestCheckInHour: 0,
      latestCheckInHour: 23,
    });

    setSubmit(true);
  };

  useEffect(() => {
    if (submit && Object.keys(errors).length === 0) {
      setClassSpinner("SpinnerCreateProduct");

      const requestOptions = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${userLocal.jwt}`,
        },
        body: JSON.stringify(payload),
      };

      setTimeout(() => {
        fetch(URL_API_POST, requestOptions).then((response) => {
          if (response.status === 200) {
            setCreateProduct(true);
            setClassSpinner("SpinnerCreateProduct SpinnerCreateProductHidden");
          } else {
            setStatusErrors(
              "Lamentablemente la reserva no ha podido realizarse. Por favor, intente más tarde"
            );
            setClassSpinner("SpinnerCreateProduct SpinnerCreateProductHidden");
          }
        });
      }, 1000);
    }
  }, [submit, errors]);

  return dataCategory && dataCity && dataAtributes ? (
    <>
      {stateCreateProduct ? (
        <Redirect to="/administration/successful-product-create" />
      ) : (
        <>
          <ProductHeading
            title="Administración de productos"
            category=""
            redirectPage="/home"
          />
          <div>
            <h2 className="TitleFormAdmin">Crear propiedad</h2>

            <form className="FormCreateProductAdmin" onSubmit={handleSubmit}>
              <div className="ContainerInputsFormAdmin">
                <label>
                  Nombre de la propiedad
                  <input
                    type="text"
                    name="name"
                    placeholder="Hotel Hermitage"
                    style={errors.name && { border: "1px solid red" }}
                  />
                  {errors.name && (
                    <p className="input-error-form-product">{errors.name}</p>
                  )}
                </label>
                <label>
                  Categoria
                  <input
                    type="text"
                    value={stateCategory.categoryName}
                    name="category"
                    placeholder="Hotel"
                    onClick={showDropDownCategory}
                    readOnly
                    className="SelectsFormAdmin"
                    id={stateCategory.id}
                    style={errors.category && { border: "1px solid red" }}
                  />
                  <div className="DropdownFormAdmin" id="dropdownCategory">
                    {dataCategory.map((category) => {
                      return (
                        <p
                          key={category.id}
                          onClick={handleChangeCategory}
                          id={category.id}
                        >
                          {category.name}
                        </p>
                      );
                    })}
                  </div>
                  {errors.category && (
                    <p className="input-error-form-product">
                      {errors.category}
                    </p>
                  )}
                </label>
              </div>

              <div className="ContainerInputsFormAdmin">
                <label>
                  Dirección
                  <input
                    type="text"
                    name="address"
                    placeholder="Av. Colón 1643"
                    style={errors.address && { border: "1px solid red" }}
                  />
                  {errors.address && (
                    <p className="input-error-form-product">{errors.address}</p>
                  )}
                </label>
                <label>
                  Ciudad
                  <input
                    type="text"
                    value={stateCity.cityName}
                    name="city"
                    placeholder="Ciudad"
                    onClick={showDropDownCity}
                    readOnly
                    className="SelectsFormAdmin"
                    id={stateCity.id}
                    style={errors.city && { border: "1px solid red" }}
                  />
                  <div className="DropdownFormAdmin" id="cityDropdown">
                    {dataCity.map((city) => {
                      return (
                        <p
                          key={city.id}
                          onClick={handleChangeCity}
                          id={city.id}
                        >
                          {city.name}, {city.country_name}
                        </p>
                      );
                    })}
                  </div>
                  {errors.city && (
                    <p className="input-error-form-product">{errors.city}</p>
                  )}
                </label>
              </div>

              <div className="ContainerInputsFormAdmin">
                <label>
                  Latitud
                  <input
                    type="text"
                    name="latitude"
                    placeholder="Latitude"
                    style={errors.latitude && { border: "1px solid red" }}
                  />
                  {errors.latitude && (
                    <p className="input-error-form-product">
                      {errors.latitude}
                    </p>
                  )}
                </label>
                <label>
                  Longitud
                  <input
                    type="text"
                    name="longitude"
                    placeholder="Longitude"
                    style={errors.longitude && { border: "1px solid red" }}
                  />
                  {errors.longitude && (
                    <p className="input-error-form-product">
                      {errors.longitude}
                    </p>
                  )}
                </label>
              </div>

              <div>
                <label>
                  Descripción
                  <textarea
                    placeholder="Escribe aquí"
                    className="DescriptionProductFormAdmin"
                    name="description"
                    style={errors.description && { border: "1px solid red" }}
                  />
                  {errors.description && (
                    <p className="input-error-form-product">
                      {errors.description}
                    </p>
                  )}
                </label>
              </div>

              <h3>Agregar atributos</h3>

              {errors.atributes && (
                <p className="input-error-images-atributes-product">
                  {errors.atributes}
                </p>
              )}

              {dataAtributes.map((atribute) => {
                return (
                  <div
                    className="ContainerAtributesFormAdmin"
                    key={atribute.id}
                  >
                    <label className="LabelAtributesFormAdmin">
                      <div className="AtributesFormAdmin">
                        <div className="ContainerInputsAtributes">
                          <div className="NameAtribute">
                            <p className="Label">Nombre</p>
                            <p
                              className="TitleAtributesFormAdmin"
                              id={`${atribute.id}${atribute.name}`}
                            >
                              {atribute.name}
                            </p>
                          </div>
                          <div className="IconAtribute">
                            <p className="Label">Ícono</p>
                            <p
                              className="TitleAtributesFormAdmin Icon "
                              id={`${atribute.id}-${atribute.name}`}
                            >
                              <i className={atribute.icon}></i>
                            </p>
                          </div>
                        </div>
                        <input
                          name={atribute.name}
                          type="checkbox"
                          className="CheckboxAtributesFormAdmin"
                          onChange={handleChangeCheckbox}
                          id={atribute.id}
                        />
                        <span className="custom-radio-checkbox__show custom-radio-checkbox__show--checkbox"></span>
                      </div>
                    </label>
                  </div>
                );
              })}

              <h3>Políticas del producto</h3>

              <div className="ContainerPoliciesFormAdmin">
                <div>
                  <h4>Normas de la casa</h4>
                  <label>
                    Descripción
                    <textarea
                      placeholder="Escribe aquí"
                      name="houseRules"
                      style={errors.houseRules && { border: "1px solid red" }}
                    />
                    {errors.houseRules && (
                      <p className="input-error-form-product">
                        {errors.houseRules}
                      </p>
                    )}
                  </label>
                </div>

                <div>
                  <h4>Salud y seguridad</h4>
                  <label>
                    Descripción
                    <textarea
                      placeholder="Escribe aquí"
                      name="healthSafety"
                      style={
                        errors.healthAndSafetyPolicy && {
                          border: "1px solid red",
                        }
                      }
                    />
                    {errors.healthAndSafetyPolicy && (
                      <p className="input-error-form-product">
                        {errors.healthAndSafetyPolicy}
                      </p>
                    )}
                  </label>
                </div>

                <div>
                  <h4>Política de cancelación</h4>
                  <label>
                    Descripción
                    <textarea
                      placeholder="Escribe aquí"
                      name="cancellation"
                      style={
                        errors.cancelationPolicy && { border: "1px solid red" }
                      }
                    />
                    {errors.cancelationPolicy && (
                      <p className="input-error-form-product">
                        {errors.cancelationPolicy}
                      </p>
                    )}
                  </label>
                </div>
              </div>

              <h3>Cargar imágenes</h3>

              {errors.images && (
                <p className="input-error-images-atributes-product">
                  {errors.images}
                </p>
              )}

              {stateImages.map((image, index) => {
                return (
                  <div className="ContainerImagesFormAdmin" key={index}>
                    <input
                      type="text"
                      name="images"
                      value={image.url}
                      readOnly
                    />
                    <button
                      type="button"
                      className="ButtonImagesFormAdmin ButtonImagesClose"
                      onClick={deleteImageOfArray}
                      id={index}
                    ></button>
                  </div>
                );
              })}

              <div className="ContainerImagesFormAdmin">
                <input
                  type="text"
                  name="imageUrl"
                  placeholder="Intertar https://"
                  id="imageUrl"
                />
                <button
                  type="button"
                  className="ButtonImagesFormAdmin ButtonImagesPlus"
                  onClick={handleChangeImages}
                ></button>
              </div>

              {statusErrors && (
                <p className="service-error-create-product">{statusErrors}</p>
              )}

              <div className={classSpinner}>
                <img src={spinner} alt="spinner" />
              </div>

              <button className="ButtonCreateProduct">Crear</button>
            </form>
          </div>
        </>
      )}
    </>
  ) : null;
}

export { AdministrationForm };
