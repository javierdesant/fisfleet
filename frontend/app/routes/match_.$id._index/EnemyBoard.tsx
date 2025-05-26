import React from "react";
import Board from "./Board";

type Props = {
  gridData: string[];
  onCellClick?: (x: number, y: number) => void;
};

const EnemyBoard: React.FC<Props> = ({ gridData, onCellClick }) => (
  <Board gridData={gridData} onCellClick={onCellClick} />
);

export default EnemyBoard;