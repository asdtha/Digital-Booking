import React from "react";
import ImageGallery from "react-image-gallery";
import "./Gallery.css";
import useMediaQuery from "../../hooks/useMediaQuery";

function Gallery({ images }) {
  const imagesArray = images.map((element) => {
    return {
      original: element.url,
      thumbnail: element.url,
    
    };
  });

  return (
    <ImageGallery
      items={imagesArray}
      showBullets={true}
      showFullscreenButton={false}
      slideDuration={1000}
      showPlayButton={useMediaQuery("(max-width: 768px)") ? true : false}
      autoPlay={true}
      showIndex={useMediaQuery("(max-width: 768px)") ? true : false}
      showThumbnails={useMediaQuery("(max-width: 768px)") ? false : true}
    />
  );
}

export default Gallery;
