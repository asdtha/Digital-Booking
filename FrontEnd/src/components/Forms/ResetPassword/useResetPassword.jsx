import { useState, useEffect } from "react";

const useResetPassword = (callback, validate) => {
  const URL_API = "http://localhost:8080/API/resetPassword";
  //const URL_API = "http://digitalbooking.click:8080/API/resetPassword";
  const [values, setValues] = useState({
    email: "",
  });

  const [errors, setErrors] = useState({});
  const [statusErrors, setStatusErrors] = useState();
  const [statusSendEmail, setStatusSendEmail] = useState();
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
    setStatusSendEmail();


  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmitting) {
      setClassSpinner("Spinner");

      const requestOptions = {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          Accept: "application/x-www-form-urlencoded",
        },

        body: new URLSearchParams(values),
      };

      setTimeout(() => {
        fetch(URL_API, requestOptions).then((response) => {
          if (response.status !== 200) {
 
            setStatusErrors(
              "Lamentablemente no ha podido enviarse el correo electrónico. Por favor intente más tarde"
            );

            setValues({ ...values, email: "" });

            setClassSpinner("Spinner SpinnerHidden");
          } else {
            setStatusSendEmail(
              "Acabamos de enviarte un correo electrónico con instrucciones para restablecer la contraseña. "
            );

            setValues({ ...values, email: "" });

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
    statusSendEmail,
  };
};

export default useResetPassword;
