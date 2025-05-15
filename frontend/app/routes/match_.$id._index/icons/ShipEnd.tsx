import React from "react";

type Direction = "up" | "down" | "left" | "right";

const ShipEnd = ({
  fill = "none",
  direction = "up",
}: React.SVGProps<SVGSVGElement> & { direction?: Direction }) => {
  const rotationDegrees = {
    up: 0,
    right: 90,
    down: 180,
    left: 270,
  }[direction];

  return (
    <svg
      width="100%"
      height="100%"
      viewBox="0 0 24 32"
      fill={fill}
      xmlns="http://www.w3.org/2000/svg"
      preserveAspectRatio="xMidYMid meet"
      style={{
        transform: `rotate(${rotationDegrees}deg)`,
        transformOrigin: "center",
      }}
    >
      <path
        d="M12 0C12 0 24 4 24 16C24 28 24 32 24 32H6.01324e-07C6.01324e-07 32 6.01324e-07 28 6.01324e-07 16C6.01324e-07 4 12 0 12 0Z"
        fill="black"
      />
    </svg>
  );
};

export default ShipEnd;
