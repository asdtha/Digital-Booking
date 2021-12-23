import React, { useEffect, useState } from "react";
import "./ProductImages.css";
import Gallery from "./Gallery";
import Modal from "./Modal";
import useMediaQuery from "../../hooks/useMediaQuery";
import ShareProduct from "./ShareProduct";
import "./Modal.css";

function ProductImages({ id, images, dataFavorites }) {
  const [stateModalGallery, setModalGallery] = useState(false);
  const [stateModalSocial, setModalSocial] = useState(false);

  // Mostrar u oculatr modal de galeria de imagenes
  const showModal = () => {
    setModalGallery(true);
  };

  const hideModal = () => {
    setModalGallery(false);
  };

  // Mostrar u oculatr modal de compartir en redes sociales
  const showModalSocial = () => {
    setModalSocial(true);
  };

  const hideModalSocial = () => {
    setModalSocial(false);
  };

  /**
   * Implementación de favoritos.
   */

  const [stateIdFavorite, setIdFavorite] = useState(() => {
    if (dataFavorites) {
      let arrayFavoritesFilter = dataFavorites.filter(
        (favorite) => favorite.productId === id
      );

      return arrayFavoritesFilter[0] ? arrayFavoritesFilter[0].id : "";
    }
  });

  const URL_API_FAVORITES_POST = `http://localhost:8080/API/favorites/${id}`;
  //const URL_API_FAVORITES_POST = `http://digitalbooking.click:8080/API/favorites/${id}`;
  const URL_API_FAVORITES_DELETE = `http://localhost:8080/API/favorites/delete/${stateIdFavorite}`;
  //const URL_API_FAVORITES_DELETE = `http://digitalbooking.click:8080/API/favorites/delete/${stateIdFavorite}`;

  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  const [stateFavorite, setFavorite] = useState(() => {
    if (dataFavorites) {
      let arrayFavorites = dataFavorites.map((favorite) => favorite.productId);
      return arrayFavorites.includes(id) ? true : false;
    }

    if (localStorage.getItem("arrayFavorites") && !userLoginParse) {
      let arrayLocal = JSON.parse(localStorage.getItem("arrayFavorites"));
      return arrayLocal.includes(id) ? true : false;
    }
  });

  const setStateFavorite = () => {
    let localArray = localStorage.getItem("arrayFavorites");

    //Cambiar estado de favorito en localStorage cuando no hay usuario logueado
    if (!userLoginParse) {
      if (localArray) {
        let newArray = JSON.parse(localArray);

        if (newArray.includes(id)) {
          setFavorite(false);
          let arrayFilter = newArray.filter((element) => element !== id);
          localStorage.setItem("arrayFavorites", JSON.stringify(arrayFilter));
          // setDataFavorites(arrayFilter)
        } else {
          newArray = [...newArray, id];
          localStorage.setItem("arrayFavorites", JSON.stringify(newArray));
          setFavorite(true);
        }
      } else {
        localStorage.setItem("arrayFavorites", JSON.stringify([id]));
        setFavorite(true);
      }
    }

    //Cambiar estado de favorito en localStorage cuando hay un usuario logueado
    stateFavorite ? setFavorite(false) : setFavorite(true);
  };

  useEffect(() => {
    if (userLoginParse) {
      if (
        stateFavorite &&
        !dataFavorites.map((favorite) => favorite.productId).includes(id)
      ) {
        const requestOptions = {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${userLoginParse.jwt}`,
          },
        };

        fetch(URL_API_FAVORITES_POST, requestOptions)
          .then((response) => {
            if (response.status !== 200) {
              throw Error(response.statusText);
            } else {
              return response.json();
            }
          })
          .then((data) => (data ? setIdFavorite(data.id) : ""));
      } else if (!stateFavorite && stateIdFavorite) {
        const requestOptions = {
          method: "DELETE",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${userLoginParse.jwt}`,
          },
        };

        fetch(URL_API_FAVORITES_DELETE, requestOptions).then((response) => {
          if (response.status === 200) {
          } else {
            throw Error(response.statusText);
          }
        });
      }
    }
  }, [stateFavorite]);

  return (
    <>
      <div className="ContainerBlockImages">
        <div className="ContainerIconsShareHeart">
          <button className="ButtonShare" onClick={showModalSocial}>
            <i className="fas fa-share-alt"></i>
          </button>
          <button className="ButtonFavorite" onClick={setStateFavorite}>
            {!stateFavorite ? (
              <i className="far fa-heart"></i>
            ) : (
              <i className="fas fa-heart"></i>
            )}
          </button>
        </div>

        {useMediaQuery("(max-width: 768px)") ? (
          <Gallery images={images} />
        ) : (
          <div className="ContainerImgGeneral">
            <div className="BigImg">
              <img src={images[0].url} alt="imagen hotel" />
            </div>
            <div className="SmallImages">
              {images.map((image, index) => {
                return index !== 0 ? (
                  <div key={image.id}>
                    <img src={image.url} alt="imagen de hotel" />
                  </div>
                ) : null;
              })}

              <button onClick={showModal}>Ver más</button>
            </div>
          </div>
        )}
      </div>

      {/* Modal galeria */}
      <Modal
        show={stateModalGallery}
        handleClose={hideModal}
        styleButtonClose="ButtonCloseModal"
        styleModal="ModalMain"
      >
        <Gallery images={images} />
      </Modal>

      {/* Modal para compartir en redes sociales */}
      <Modal
        show={stateModalSocial}
        handleClose={hideModalSocial}
        styleButtonClose="ButtonCloseModalSocial"
        styleModal="ModalMainSocial"
      >
        <ShareProduct />
      </Modal>
    </>
  );
}

export default ProductImages;
