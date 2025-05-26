import React from "react";

interface ServiceItemProps {
  icon: string;
  title: string;
  description: string;
}

const ServiceItem: React.FC<ServiceItemProps> = ({ icon, title, description }) => (
  <div className="w-full p-4 sm:w-1/2 lg:w-1/3">
    <div className="text-center bg-white dark:bg-gray-800 rounded-xl shadow-md dark:shadow-none h-full p-6 flex flex-col items-center transition-colors duration-300">
      <div className="mb-4 text-5xl text-blue-600 dark:text-blue-300">
        <i className={icon}></i>
      </div>
      <h3 className="mb-3 text-lg font-semibold text-gray-800 dark:text-blue-100 uppercase">
        {title}
      </h3>
      <p className="text-gray-600 dark:text-gray-300">{description}</p>
    </div>
  </div>
);

export default ServiceItem;