import React from "react";

type Props = {
  enabled: boolean;
  onClick: () => void;
};

const SpecialAbilityButton: React.FC<Props> = ({ enabled, onClick }) => (
  <button
    className={`px-4 py-2 rounded bg-indigo-600 text-white font-bold mt-2 ${enabled ? "hover:bg-indigo-700" : "opacity-50 cursor-not-allowed"}`}
    disabled={!enabled}
    onClick={onClick}
  >
    Usar habilidad especial
  </button>
);

export default SpecialAbilityButton;