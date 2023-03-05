import React, { useState } from "react";
import axios from "axios";

const apiUrl = "http://localhost:8080/api";

const RegistrationForm = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleRegistration = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post(apiUrl + "/register", {
        username: username,
        email: email,
        password: password,
      });
      localStorage.setItem("token", response.data);
      window.location = "/todo-list";
    } catch (error) {
      setError(error.data);
    }
  };

  return (
    <>
      <h1>Registration</h1>
      {error && (
        <>
          <div style={{ color: "red" }}>{error}</div>
          <br />
        </>
      )}
      <form onSubmit={handleRegistration}>
        <div>
          <input
            type="text"
            id="username"
            placeholder="Username"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
          />
        </div>
        <br />
        <div>
          <input
            type="email"
            id="email"
            placeholder="Email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
          />
        </div>
        <br />
        <div>
          <input
            type="password"
            id="password"
            placeholder="Password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
          />
        </div>
        <br />
        <button type="submit">Register</button>
      </form>
    </>
  );
};

export default RegistrationForm;
