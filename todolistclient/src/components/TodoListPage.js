import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Todo from "./Todo";

const apiUrl = "http://localhost:8080/api";

const TodoListPage = () => {
  const [token, setToken] = useState("");
  const [username, setUsername] = useState("");
  const [todos, setTodos] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    setToken(localStorage.getItem("token"));

    const fetchUsername = async () => {
      try {
        const response = await axios.get(apiUrl + "/me", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setUsername(response.data);
      } catch (error) {
        setError(error.data);
      }
    };

    const fetchTodos = async () => {
      try {
        const response = await axios.get(apiUrl + "/items", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setTodos(response.data);
      } catch (error) {
        setError(error.data);
      }
    };

    fetchUsername();
    fetchTodos();
  }, []);

  const deleteItem = async (id) => {
    try {
      const response = await axios.delete(`${apiUrl}/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (error) {
      setError(error.data);
    }
  };

  return (
    <>
      <h1>Hello {username}</h1>
      {error && (
        <>
          <div style={{ color: "red" }}>{error}</div>
          <br />
        </>
      )}
      <ul>
        {todos.map((todo) => (
          <li key={todo.id}>
            <Todo
              deleteItem={deleteItem}
              setError={setError}
              token={token}
              item={todo}
            />
          </li>
        ))}
      </ul>
      <button onClick={() => navigate("/logout")}>Logout</button>
    </>
  );
};

export default TodoListPage;
