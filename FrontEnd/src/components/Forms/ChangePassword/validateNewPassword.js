export default function validateNewPassword(values) {
  let errors = {};

  //password validations
  if (!values.newPassword) {
    errors.newPassword = "Este campo es obligatorio";
  } else if (values.newPassword.length < 6) {
    errors.newPassword = "La contraseña debe tener 6 caracteres o más";
  }
  //matching passwords validations
  if (!values.repeatPassword) {
    errors.repeatPassword = "Este campo es obligatorio";
  } else if (values.repeatPassword !== values.newPassword) {
    errors.repeatPassword = "Las contraseñas no coinciden";
  }

  return errors;
}
