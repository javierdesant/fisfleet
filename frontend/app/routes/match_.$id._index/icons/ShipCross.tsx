import React from "react";

const ShipCross = ({
  stroke = "black",
  strokeWidth = 2.5,
  size = "60%", // Propiedad adicional para ajustar la escala
}: React.SVGProps<SVGSVGElement> & { size?: string }) => (
  <svg
    width={size}
    height={size}
    viewBox="0 0 20 20"
    fill="none"
    xmlns="http://www.w3.org/2000/svg"
    preserveAspectRatio="xMidYMid meet"
  >
    <path
      d="M2 2L10 10M18 18L10 10M10 10L18 2M10 10L2 18"
      stroke={stroke}
      strokeWidth={strokeWidth}
      strokeLinecap="round"
    />
  </svg>
);

export default ShipCross;
