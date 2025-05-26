import React from "react";
import Cell from "./Cell";

type BoardProps = {
  gridData: Array<string>;
  onCellClick?: (x: number, y: number) => void;
};

const Board: React.FC<BoardProps> = ({ gridData, onCellClick }) => {
  return (
    <div
      className={"grid aspect-square min-w-full grid-cols-10 grid-rows-10 gap-1  sm:gap-1.5 md:gap-2 lg:gap-3"}
    >
      {gridData.map((symbol, index) => {
        const x = index % 10;
        const y = Math.floor(index / 10);
        const key = `${x}-${y}`;
        return (
          <button
            key={key}
            type="button"
            onClick={onCellClick ? () => onCellClick(x, y) : undefined}
            style={{ cursor: onCellClick ? "pointer" : "default" }}
            tabIndex={0}
            aria-label={`Cell ${x}, ${y}`}
            onKeyDown={
              onCellClick
                ? (e) => {
                    if (e.key === "Enter" || e.key === " ") {
                      e.preventDefault();
                      onCellClick(x, y);
                    }
                  }
                : undefined
            }
            className="p-0 bg-transparent border-none"
          >
            <Cell symbol={symbol as "A" | "I" | "?" | "H"} />
          </button>
        );
      })}
    </div>
  );
};

export default Board;
