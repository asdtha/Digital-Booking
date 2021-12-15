export default function validateFormBooking(data, images, atributes) {
  let errors = {};

  if (!data.name) {
    errors.name = "Este campo es obligatorio";
  }
  if (!data.description) {
    errors.description = "Este campo es obligatorio";
  }

  if (!data.latitude) {
    errors.latitude = "Este campo es obligatorio";
  }

  if (!data.longitude) {
    errors.longitude = "Este campo es obligatorio";
  }

  if (!data.address) {
    errors.address = "Este campo es obligatorio";
  }

  if (!data.city) {
    errors.city = "Este campo es obligatorio";
  }

  if (!data.category) {
    errors.category = "Este campo es obligatorio";
  }

  if (!data.cancelationPolicy) {
    errors.cancelationPolicy = "Este campo es obligatorio";
  }

  if (!data.healthAndSafetyPolicy) {
    errors.healthAndSafetyPolicy = "Este campo es obligatorio";
  }

  if (!data.houseRules) {
    errors.houseRules = "Este campo es obligatorio";
  }

  if (atributes.length === 0) {
    errors.atributes = "Debes agregar al menos un atributo";
  }
  if (images.length < 5) {
    errors.images = "Debes agregar mínimo 5 imágenes";
  }

  return errors;
}
