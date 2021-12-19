import React, { useContext } from "react";
import LoginButton from "./LoginButton";
import RegisterButton from "./RegisterButton";
import "./MobileMenu.css";
import { Link } from "react-router-dom";
import UserAvatar from "./UserAvatar";
import userLoginContext from "../../context/userLoginContext";
import { AdministrationButton } from "./AdministrationButton";

function MobileMenu() {
  const { setUserLogin } = useContext(userLoginContext);

  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  function validateDisplay() {
    let x = document.getElementById("myLinks");
    if (x.style.display === "block") {
      x.style.animation = "menuInactive 0.75s";
      setTimeout(() => {
        x.style.display = "none";
      }, 500);
    } else {
      x.style.display = "block";
      x.style.animation = "menuActive 0.75s";
    }
  }

  const handleClick = () => {
    localStorage.removeItem("userLogin");
    setUserLogin("false");
  };

  return (
    <>
      <div id="myLinks" className="Menu">
        <div className="ContainerMenuText">
          <div>
            <button onClick={validateDisplay}>x</button>
          </div>
          <p
            style={
              localStorage.getItem("userLogin") ? { display: "none" } : null
            }
          >
            MENÚ
          </p>
          {userLogin && <UserAvatar /> }
        </div>
        <div className="ContainerButtonsIcons">
          <div className="ContainerButtonsMobile">
            <Link to={"/login"}>
              <LoginButton />
            </Link>
            <Link to={"/register"}>
              <RegisterButton />
            </Link>
            {userLogin && userLoginParse.userType === "ADMIN" ? (
              <>
                <Link to={"/administration"}>
                  <AdministrationButton name="Administración" />
                </Link>
              </>
            ) : userLogin && userLoginParse.userType === "USER" ? (
              <>
                <Link to={"/favorites"}>
                  <AdministrationButton name="Favoritos" />
                </Link>
                <Link to={`/${userLoginParse.id}/bookings`}>
                  <AdministrationButton name="Reservas" />
                </Link>
              </>
            ) : (
              <></>
            )}
          </div>
          <div className="ContainerIconsMobile">
            <div
              className="ContainerLogout"
              style={
                !localStorage.getItem("userLogin") ? { display: "none" } : null
              }
            >
              <p>
                ¿Deseas{" "}
                <span>
                  <button onClick={handleClick}> cerrar sesión</button>
                </span>
                ?
              </p>
            </div>
            <div className="ContainerIconFooter">
              <i className="fab fa-facebook"></i>
              <i className="fab fa-linkedin-in"></i>
              <i className="fab fa-twitter"></i>
              <i className="fab fa-instagram"></i>
            </div>
          </div>
        </div>
      </div>
      <button onClick={validateDisplay} className="ButtonBurguer">
        <i className="fa fa-bars"></i>
      </button>
    </>
  );
}

export default MobileMenu;
