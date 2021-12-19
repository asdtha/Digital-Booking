import React, { useContext } from "react";
import { Link } from "react-router-dom";
import userContext from "../../context/userContext";
import userLoginContext from "../../context/userLoginContext";
import "./UserAvatar.css";

function UserAvatar() {
  const { stateUserName, stateUserLastname } = useContext(userContext);
  const { setUserLogin } = useContext(userLoginContext);

  const handleClick = () => {
    localStorage.removeItem("userLogin");

    localStorage.removeItem("infoBooking");
    localStorage.removeItem("arrayFavorites");
    setUserLogin("false");
  };

  return (
    <div className="ContainerUserAvatar">
      <Link to={"/home"}>
        <button className="ButtonLogout" onClick={handleClick}>
          x
        </button>
      </Link>

      <div className="ContainerAvatar">
        <div className="Avatar">
          <h2 className="TextAvatar">
            {stateUserName.charAt(0).toUpperCase()}
            {stateUserLastname.charAt(0).toUpperCase()}
          </h2>
        </div>
        <h3 className="TextUser">
          <span className="SpanTextUser">Hola,</span> <br />
          {stateUserName} {stateUserLastname}
        </h3>
      </div>
    </div>
  );
}

export default UserAvatar;
