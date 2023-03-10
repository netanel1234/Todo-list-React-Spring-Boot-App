import { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { Link } from "react-router-dom";

const apiUrl = "http://localhost:8080/api";

const LoginForm = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post(apiUrl + "/login", {
        email: email,
        password: password,
      });
      localStorage.setItem("token", response.data);
      navigate("/todo-list");
    } catch (error) {
      setError(error);
    }
  };

  return (
    <>
      <h1>Login</h1>
      <Link to="/api/register">To registration page</Link>
      <br />
      <br />
      {error && (
        <>
          <div style={{ color: "red" }}>{error}</div>
          <br />
        </>
      )}
      <form onSubmit={handleLogin}>
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
        <button type="submit">Login</button>
      </form>
    </>
  );
};

export default LoginForm;
