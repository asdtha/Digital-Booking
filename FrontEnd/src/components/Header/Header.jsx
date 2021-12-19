import React, { useContext, useEffect } from "react";
import "./Header.css";
import { Link } from "react-router-dom";
import LoginButton from "./LoginButton";
import RegisterButton from "./RegisterButton";
import UserAvatar from "./UserAvatar";
import userLoginContext from "../../context/userLoginContext";
import MobileMenu from "./MobileMenu";
import filterContext from "../../context/filterContext";
import { AdministrationButton } from "./AdministrationButton";

const urlLogo = "/images/logo3.png";

function Header() {
  const { stateUserLogin } = useContext(userLoginContext);
  const { stateFilter, setFilter } = useContext(filterContext);

  const userLogin = localStorage.getItem("userLogin");
  const userLoginParse =
    userLogin && JSON.parse(localStorage.getItem("userLogin"));

  const validate = () => {
    userLogin && userLoginParse.userType === "USER" ? (
      <UserAvatar />
    ) : userLogin && userLoginParse.userType === "ADMIN" ? (
      <>
        <Link to={"/administration"}>
          <AdministrationButton />
        </Link>
        <UserAvatar />
      </>
    ) : (
      <></>
    );
  };

  useEffect(() => {
    validate();
  }, [stateUserLogin]);

  const handleClick = () => {
    let elmnt = document.querySelector("main");
    elmnt.scrollTop = 0;

    localStorage.removeItem("infoBooking");
    localStorage.removeItem("infoRegister");

    if (stateFilter !== "all") {
      setFilter("all");
    }
  };

  return (
    <header className="Header">
      <Link to={"/home"}>
        <img src={urlLogo} alt="logo" onClick={handleClick} />
      </Link>
      <div className="ContainerButtons">
        <Link to={"/login"}>
          <LoginButton />
        </Link>
        <Link to={"/register"}>
          <RegisterButton />
        </Link>
        {userLogin && userLoginParse.userType === "USER" ? (
          <>
            <Link to={"/favorites"}>
              <AdministrationButton name="Favoritos" />
            </Link>
            <Link to={`/${userLoginParse.id}/bookings`}>
              <AdministrationButton name="Reservas" />
            </Link>
            <UserAvatar />
          </>
        ) : userLogin && userLoginParse.userType === "ADMIN" ? (
          <>
            <Link to={"/administration"}>
              <AdministrationButton name="Administración" />
            </Link>
            <UserAvatar />
          </>
        ) : 
        null}
      </div>

      <MobileMenu />
    </header>
  );
}

export default Header;
