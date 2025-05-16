import React from "react";
import ShipMiddle from "./icons/ShipMiddle";
import ShipEnd from "./icons/ShipEnd";
import ShipCross from "./icons/ShipCross";

type CellProps = {
  symbol: "A" | "I" | "?" | "H";
  orientation?: "vertical" | "horizontal";
  partType?: "start" | "middle" | "end" | "cross" | "none";
};

const Cell: React.FC<CellProps> = ({
  symbol,
  orientation = "vertical",
  partType = "none",
}) => {
  const renderContent = () => {
    switch (partType) {
      case "start":
      case "end":
        return (
          <ShipEnd
            fill="black"
            direction={orientation === "horizontal" ? "right" : "up"}
          />
        );
      case "middle":
        return <ShipMiddle fill="black" orientation={orientation} />;
      case "cross":
        return <ShipCross stroke="red" />;
      default:
        return null;
    }
  };

  const bg =
    symbol === "A"
      ? "bg-blue-300"
      : symbol === "I" || symbol === "H"
        ? "bg-red-500"
        : "bg-gray-200";

  return (
    <div className="group relative aspect-square h-full w-full">
      <div
        className={`absolute inset-0 translate-x-[2px] translate-y-[2px] rounded-md bg-black transition-all duration-200 ease-in-out group-hover:translate-x-0 group-hover:translate-y-0 group-hover:opacity-0 md:translate-x-[3px] md:translate-y-[3px]`}
      />
      <div
        className={`absolute inset-0 flex cursor-pointer items-center justify-center ${bg} rounded-md border-2 border-black transition-all duration-200 ease-in-out group-hover:translate-x-[2px] group-hover:translate-y-[2px] sm:border-3 md:border-3 md:group-hover:translate-x-[3px] md:group-hover:translate-y-[3px]`}
      >
        {renderContent()}
      </div>
    </div>
  );
};

export default Cell;
