import Body from "../Body/Body";
import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import Main from "../Main/Main";
import { Route, Switch, Redirect } from "react-router-dom";
import ListedBlock from "../Product/ListedBlock";
import Forms from "../Forms/Login/Forms";
import FormRegister from "../Forms/Register/FormRegister";
import userContext from "../../context/userContext";
import userLoginContext from "../../context/userLoginContext";
import filterContext from "../../context/filterContext";
import { useState } from "react";
import SearchBlock from "../SearchEngine/SearchBlock";
import CategoriesBlock from "../Categories/CategoriesBlock";
import useMediaQuery from "../../hooks/useMediaQuery";
import ProductDetail from "../ProductDetail/ProductDetail";
import FavoriteProductsList from "../Favorites/FavoriteProductsList";
import { ProductBooking } from "../ProductBooking/ProductBooking";
import { SuccessfulMessage } from "../SuccessfulMessage/SuccessfulMessage";
import { AdministrationForm } from "../Administration/AdministrationForm";
import { MyBookings } from "../MyBookings/MyBookings";
import RequestNewPassword from "../Forms/ResetPassword/RequestNewPassword";
import ChangePassword from "../Forms/ChangePassword/ChangePassword";

function Home() {
  /**
   * Se valida en localStorage si viene un usuario logueado para actualizar el userContext
   */

  const validateLocal = () => {
    let itemLocal = localStorage.getItem("userLogin");
    let itemParse = itemLocal ? JSON.parse(itemLocal) : null;

    if (itemParse) {
      return itemParse;
    }
  };

  /**
   * Valores iniciales del userContext
   */
  const [stateUserName, setUserName] = useState(
    validateLocal() ? validateLocal().firstName : ""
  );
  const [stateUserLastname, setUserLastname] = useState(
    validateLocal() ? validateLocal().lastName : ""
  );

  /**
   * Valores iniciales del userLoginContext
   */
  const [stateUserLogin, setUserLogin] = useState(false);

  /**
   * Valores iniciales del filterContext
   */
  const [stateFilter, setFilter] = useState("all");

  return (
    <Body>
      <userLoginContext.Provider value={{ stateUserLogin, setUserLogin }}>
        <filterContext.Provider value={{ stateFilter, setFilter }}>
          <userContext.Provider
            value={{
              stateUserName,
              setUserName,
              stateUserLastname,
              setUserLastname,
            }}
          >
            <Header />

            {useMediaQuery("(max-width: 706px)") ? null : <SearchBlock />}

            <Main>
              {useMediaQuery("(max-width: 706px)") ? <SearchBlock /> : null}

              <Switch>
                <Route exact path={"/home"}>
                  <CategoriesBlock />
                  <ListedBlock />
                </Route>

                <Route exact path={"/administration"}>
                  <AdministrationForm />
                </Route>

                <Route path={"/administration/successful-product-create"}>
                  <SuccessfulMessage message="Tu propiedad se ha creado con éxito." />
                </Route>

                <Route path={"/login"}>
                  <Forms />
                </Route>

                <Route path={"/register"}>
                  <FormRegister />
                </Route>

                <Route path={"/reset-password"}>
                  <RequestNewPassword />
                </Route>

                <Route path={"/change-password"}>
                  <ChangePassword />
                </Route>

                <Route exact path={"/product/:id"}>
                  <ProductDetail />
                </Route>

                <Route exact path={"/product/:id/booking"}>
                  {() => {
                    if (localStorage.getItem("userLogin")) {
                      return <ProductBooking />;
                    } else {
                      localStorage.setItem(
                        "infoBooking",
                        "Para realizar una reserva necesitas estar logueado"
                      );

                      return <Redirect to="/login" />;
                    }
                  }}
                </Route>

                <Route path={"/product/:id/booking/successful"}>
                  <SuccessfulMessage message="Su reserva se ha realizado con éxito." />
                </Route>

                <Route path={"/favorites"}>
                  <FavoriteProductsList />
                </Route>

                <Route path={"/:idUser/bookings"}>
                  <MyBookings />
                </Route>
              </Switch>
            </Main>
          </userContext.Provider>
        </filterContext.Provider>
      </userLoginContext.Provider>

      <Footer />
    </Body>
  );
}

export default Home;
