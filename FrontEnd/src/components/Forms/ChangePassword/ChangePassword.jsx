import React, { useState } from "react";
import { Redirect } from "react-router-dom";
import ChangePasswordForm from "./ChangePasswordForm";

const ChangePassword = () => {
  const [passwordChange, setPasswordChange] = useState(false);

  function submitForm(email) {
    setPasswordChange(true);
  }
  return (
    <>
      {passwordChange || localStorage.getItem("userLogin") ? (
        <Redirect to="/login" />
      ) : (
        <ChangePasswordForm submitForm={submitForm} />
      )}
    </>
  );
};

export default ChangePassword;