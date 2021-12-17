import React, { useState } from "react";
import { Redirect } from "react-router-dom";
import RequestNewPasswordForm from "./RequestNewPasswordForm";

const RequestNewPassword = () => {
  const [resetPassword, setResetPassword] = useState(false);

  function submitForm() {
    setResetPassword(true);
  }
  return (
    <>
      {resetPassword || localStorage.getItem("userLogin") ? (
        <Redirect to="/login" />
      ) : (
        <RequestNewPasswordForm submitForm={submitForm} />
      )}
    </>
  );
};

export default RequestNewPassword;
