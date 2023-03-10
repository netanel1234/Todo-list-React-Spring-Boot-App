import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";

const apiUrl = "http://localhost:8080/api";

const RegistrationForm = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleRegistration = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post(apiUrl + "/register", {
        username: username,
        email: email,
        password: password,
      });
      localStorage.setItem("token", response.data);
      navigate("/todo-list");
    } catch (error) {
      setError(error.data);
    }
  };

  return (
    <>
      <h1>Registration</h1>
      <Link to="/api/login">To login page</Link>
      <br />
      <br />
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
