import React from "react";
import './Footer.css'

function Footer() {
  return (
    <footer className="Footer">
      <p className="TextFooter">©2021 Digital Booking</p>
      <div className="IconsContainer">
        <i className="fab fa-facebook"></i>
        <i className="fab fa-linkedin-in"></i>
        <i className="fab fa-twitter"></i>
        <i className="fab fa-instagram"></i>
      </div>
    </footer>
  );
}

export default Footer;
