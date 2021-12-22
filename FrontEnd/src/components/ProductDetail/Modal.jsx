import React from "react";
import "./Modal.css";

function Modal({ handleClose, show, children, styleButtonClose, styleModal }) {
  const showHideClassName = show ? "Modal DisplayBlock" : "Modal DisplayNone";

  return (
    <div className={showHideClassName}>
      <section className={styleModal}>
        {children}
        <button type="button" onClick={handleClose} className={styleButtonClose}>
            x
        </button>
      </section>
    </div>
  );
}

export default Modal;
