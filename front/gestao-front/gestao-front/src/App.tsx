import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import SignUpPage from "./pages/SignUpPage";
import DashboardPage from "./pages/DashboardPage";
import PayrollPage from "./pages/PayrollPage";
import EmployeePayrollPage from "./pages/EmployeePayrollPage";
import "./App.css";
import AdminUserHistoryPage from "./pages/AdminUserHistoryPage";
import AdminUserListPage from "./pages/AdminUserListPage";

const App: React.FC = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/payroll" element={<PayrollPage />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/employee/payroll" element={<EmployeePayrollPage />} />
        <Route path="/login" element={<LoginPage />} />

        <Route path="/admin/users" element={<AdminUserListPage />} />
        <Route
          path="/admin/users/:id/history"
          element={<AdminUserHistoryPage />}
        />
      </Routes>
    </Router>
  );
};

export default App;
