export default function registerValidations(values) {
  let errors = {};

  //name validations

  if (values.nombre.length < 2) {
    errors.nombre = "Por favor, ingrese un nombre válido";
  }
  if (!values.nombre) {
    errors.nombre = "Este campo es obligatorio";
  }
  //surname validations

  if (values.apellido.length < 2) {
    errors.apellido = "Por favor, ingrese un apellido válido";
  }
  if (!values.apellido) {
    errors.apellido = "Este campo es obligatorio";
  }
  //password validations
  if (!values.password) {
    errors.password = "Este campo es obligatorio";
  } else if (values.password.length < 6) {
    errors.password = "La contraseña debe tener 6 caracteres o más";
  }
  //matching passwords validations
  if (!values.passwordConfirmation) {
    errors.passwordConfirmation = "Este campo es obligatorio";
  } else if (values.passwordConfirmation !== values.password) {
    errors.passwordConfirmation = "Las contraseñas no coinciden";
  }
  //email validations
  if (
    !values.emailRegister.includes("@") ||
    values.emailRegister.length < 7 ||
    values.emailRegister.endsWith(".") ||
    values.emailRegister.endsWith("@") ||
    !values.emailRegister.includes(".")
  ) {
    errors.emailRegister = "Por favor, ingrese un correo electrónico válido";
  }

  if (!values.emailRegister) {
    errors.emailRegister = "Este campo es obligatorio";
  }

  return errors;
}
