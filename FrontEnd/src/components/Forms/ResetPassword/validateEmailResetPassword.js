export default function validateEmailResetPassword(values) {
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

  return errors;
}
