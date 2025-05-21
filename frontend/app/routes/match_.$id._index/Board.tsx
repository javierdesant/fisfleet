import React from "react";
import Cell from "~/routes/match_.$id._index/Cell";

type BoardProps = {
  gridData: Array<string>;
};

const Board: React.FC<BoardProps> = ({ gridData }) => {
  return (
    <div
      className={"grid aspect-square min-w-full grid-cols-10 grid-rows-10 gap-1  sm:gap-1.5 md:gap-2 lg:gap-3"}
    >
      {gridData.map((symbol, index) => (
        <Cell key={index} symbol={symbol as "A" | "I" | "?" | "H"} />
      ))}
    </div>
  );
};

export default Board;
