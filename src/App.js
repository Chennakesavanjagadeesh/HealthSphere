import React from "react";
import { Routes, Route } from "react-router-dom";

import HomePage from "./components/home.jsx";
import LoginPage from "./components/Login.jsx";
import DoctorDashboard from "./components/doctor-dashboard.jsx";
import PatientDashboard from "./components/patient-dashboard.jsx";
import AdminDashboard from "./components/admin-dashboard.jsx";
import UsersPage from "./pages/UserPage.jsx";


function App() {
  return (
    <Routes>
      <Route path="/" element={<HomePage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/doctor-dashboard" element={<DoctorDashboard />} />
      <Route path="/patient-dashboard" element={<PatientDashboard />} />
      <Route path="/admin-dashboard" element={<AdminDashboard />} />
      <Route path="/users" element={<UsersPage />} />
    </Routes>
  );
}

export default App;
