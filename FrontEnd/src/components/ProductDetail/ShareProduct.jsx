import React from "react";
import {
  WhatsappShareButton,
  FacebookShareButton,
  TwitterShareButton,
} from "react-share";
import "./ShareProduct.css"

function ShareProduct() {
  const locationHref = document.location.href;

  return (
    <div className="ContainerShareButtons">
      <h3>Compartir por</h3>
      <WhatsappShareButton url={locationHref} title={"Hotel:"}>
        <div className="ContainerShareIcon">
          <i className="fab fa-whatsapp"></i>
          <p>Whatsapp</p>
        </div>
      </WhatsappShareButton>

      <FacebookShareButton url={locationHref} title={"Hotel:"}>
        <div className="ContainerShareIcon">
          <i className="fab fa-facebook"></i>
          <p>Facebook</p>
        </div>
      </FacebookShareButton>

      <TwitterShareButton url={locationHref} title={"Hotel:"}>
        <div className="ContainerShareIcon">
          <i className="fab fa-twitter"></i>
          <p>Twitter</p>
        </div>
      </TwitterShareButton>
    </div>
  );
}

export default ShareProduct;
