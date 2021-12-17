export default function loginValidations(values) {
  let errors = {};

  if (!values.email) {
    errors.email = "Este campo es obligatorio";
  } else if (
    !values.email.includes("@") ||
    values.email.length < 7 ||
    values.email.endsWith(".") ||
    values.email.endsWith("@") ||
    !values.email.includes(".")
  ) {
    errors.email = "Formato de correo electrónico inválido";
  }

  if (!values.password) {
    errors.password = "Este campo es obligatorio";
  } else if (values.password.length < 6) {
    errors.password = "La contraseña debe tener 6 caracteres o más";
  }

  return errors;
}
