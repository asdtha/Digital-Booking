import { useState, useEffect, useContext } from "react";

const useChangePasswordForm = (callback, validate) => {
  //const URL_API = "http://localhost:8080/API/changePassword";
  const URL_API = "http://digitalbooking.click:8080/API/changePassword";
  const urlParams = new URLSearchParams(window.location.search);
  const param = urlParams.get("token");

  const [values, setValues] = useState({
    newPassword: "",
    repeatPassword: "",
  });

  const [payload, setPayload] = useState({
    resetPasswordToken: "",
    password: "",
  });

  const [errors, setErrors] = useState({});
  const [statusErrors, setStatusErrors] = useState();
  const [statusChangePassword, setStatusChangePassword] = useState();

  const [classSpinner, setClassSpinner] = useState("Spinner SpinnerHidden");

  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setValues({
      ...values,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    setErrors(validate(values));
    setIsSubmitting(true);
    setStatusErrors();
    setStatusChangePassword();

    setPayload({
      ...payload,
      resetPasswordToken: param,
      password: event.target[1].value,
    });
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      setClassSpinner("Spinner");

      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      };

      setTimeout(() => {
        fetch(URL_API, requestOptions).then((response) => {
          if (response.status !== 200) {
            setStatusErrors(
              "Lamentablemente no ha podido realizarse el cambio de contraseña. Por favor intente más tarde"
            );

            setValues({ ...values, newPassword: "", repeatPassword: "" });

            setClassSpinner("Spinner SpinnerHidden");
          } else {
            setStatusChangePassword(
              "Tu contraseña ha sido cambiada exitosamente!"
            );

            setValues({ ...values, newPassword: "", repeatPassword: "" });

            setClassSpinner("Spinner SpinnerHidden");
          }
        });
      }, 1000);
    }
  }, [errors, isSubmitting, callback]);

  return {
    handleChange,
    values,
    handleSubmit,
    errors,
    statusErrors,
    classSpinner,
    statusChangePassword,
  };
};

export default useChangePasswordForm;
