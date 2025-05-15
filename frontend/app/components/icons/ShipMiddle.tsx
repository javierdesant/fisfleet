import React from "react";

type Orientation = "vertical" | "horizontal";

const ShipMiddle = ({
  fill = "black",
  orientation = "vertical",
}: React.SVGProps<SVGSVGElement> & { orientation?: Orientation }) => {
  const rotationDegrees = orientation === "horizontal" ? 90 : 0;

  return (
    <svg
      width="100%"
      height="100%"
      viewBox="0 0 24 40"
      fill={fill}
      xmlns="http://www.w3.org/2000/svg"
      preserveAspectRatio="xMidYMid meet"
      style={{
        transform: `rotate(${rotationDegrees}deg)`,
        transformOrigin: "center",
      }}
    >
      <path d="M24 0V40H0V0H24Z" fill={fill} />
    </svg>
  );
};

export default ShipMiddle;
