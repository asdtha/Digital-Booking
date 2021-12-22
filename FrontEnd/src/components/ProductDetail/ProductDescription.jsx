import React from "react";
import "./ProductDescription.css";

function ProductDescription({description, city}) {
  return (
    <div className="ContainerProductDescription">
      <h2>Alojate en el corazón de {city}</h2>
      <p>
        {description}
      </p>
      <p>
        Grimalkin. Havana brown siamese and abyssinian . Balinese bengal, yet
        british shorthair american shorthair panther. Egyptian mau. Tabby ocelot
        for scottish fold, cheetah yet havana brown devonshire rex.
      </p>
      <p>
        Bombay american shorthair. Lion turkish angora bengal, so munchkin.
        Ragdoll leopard. Bengal kitten or persian burmese yet bengal for
        munchkin and american shorthair. Kitty leopard for leopard yet thai
        jaguar for ocicat kitten. Savannah turkish angora.
      </p>
    </div>
  );
}

export default ProductDescription;
