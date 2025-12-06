import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import SignUpPage from "./pages/SignUpPage";
import DashboardPage from "./pages/DashboardPage";
import PayrollPage from "./pages/PayrollPage";
import EmployeePayrollPage from "./pages/EmployeePayrollPage";
import "./App.css";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/cadastro" element={<SignUpPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/payroll" element={<PayrollPage />} />
        <Route path="/employee/payroll" element={<EmployeePayrollPage />} />
      </Routes>
    </Router>
  );
};

export default App;
