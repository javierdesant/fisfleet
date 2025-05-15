import React from "react";

const ShipDot = ({
  fill = "black",
  size = "30%", // Propiedad adicional para ajustar la escala
}: React.SVGProps<SVGSVGElement> & { size?: string }) => (
  <svg
    width={size}
    height={size}
    viewBox="0 0 10 10"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
    preserveAspectRatio="xMidYMid meet"
  >
    <circle cx="5" cy="5" r="5" fill={fill} />
  </svg>
);

export default ShipDot;
