import { useState, useEffect } from "react";

const useRegisterForm = (callback, registerValidations) => {
  const URL_API = "http://localhost:8080/API/authenticate/register";
  //const URL_API = "http://digitalbooking.click:8080/API/authenticate/register";

  const [values, setValues] = useState({
    nombre: "",
    apellido: "",
    emailRegister: "",
    password: "",
    passwordConfirmation: "",
  });

  const [payload, setPayload] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const [errors, setErrors] = useState({});
  const [statusErrors, setStatusErrors] = useState();
  const [classSpinner, setClassSpinner] = useState("Spinner SpinnerHidden");

  const [isSubmittingRegister, setIsSubmittingRegister] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setValues({
      ...values,
      [name]: value,
    });
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    setErrors(registerValidations(values));
    setIsSubmittingRegister(true);
    setStatusErrors();

    setPayload({
      ...payload,
      firstName: event.target[0].value,
      lastName: event.target[1].value,
      email: event.target[2].value,
      password: event.target[3].value,
    });
  };

  useEffect(() => {
    if (Object.keys(errors).length === 0 && isSubmittingRegister) {
      setClassSpinner("Spinner");

      const requestOptions = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      };

      setTimeout(() => {
        fetch(URL_API, requestOptions).then((response) => {
          if (response.status === 201) {
            callback(payload.email);
           
          } else {
            setStatusErrors(
              "Lamentablemente no ha podido registrarse. Por favor intente más tarde"
            );

            setValues({
              ...values,
              nombre: "",
              apellido: "",
              emailRegister: "",
              password: "",
              passwordConfirmation: "",
            });

            setClassSpinner("Spinner SpinnerHidden");
          }
        });
      }, 500);
    }
  }, [errors, isSubmittingRegister, callback, payload]);

  return {
    handleChange,
    values,
    handleSubmit,
    errors,
    statusErrors,
    classSpinner,
  };
};

export default useRegisterForm;
