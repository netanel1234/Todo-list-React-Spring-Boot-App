import React from "react";
import { Routes, Route } from "react-router-dom";
import LoginForm from "./components/LoginForm";
import RegistrationForm from "./components/RegistrationForm";
import TodoListPage from "./components/TodoListPage";
import Logout from "./components/Logout";
import NotFound from "./components/NotFound";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/api/register" element={<RegistrationForm />} />
        <Route path="/api/login" element={<LoginForm />} />
        <Route path="/api/todo-list" element={<TodoListPage />} />
        <Route path="/api/logout" element={<Logout />} />
        <Route path="/api/not-found" element={<NotFound />} />
        <Route path="*" element={<LoginForm />} />
      </Routes>
    </div>
  );
}

export default App;
