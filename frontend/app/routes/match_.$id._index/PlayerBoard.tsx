import React from "react";
import Board from "./Board";

type PlayerBoardProps = {
  gridData: string[];
};

const PlayerBoard: React.FC<PlayerBoardProps> = ({ gridData }) => {
  return <Board gridData={gridData} />;
};

export default PlayerBoard;
