import { useState, useEffect } from "react";
import axios from "axios";
import Todo from "./Todo";

const apiUrl = "http://localhost:8080/api";

const TodoListPage = () => {
  const [token, setToken] = useState("");
  const [username, setUsername] = useState("");
  const [todos, setTodos] = useState([]);

  useEffect(() => {
    setToken(localStorage.getItem("token"));

    const fetchUsername = async () => {
      try {
        const response = await axios.get(apiUrl + "/me", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setUsername(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    const fetchTodos = async () => {
      try {
        const response = await axios.get(apiUrl + "/items", {
          headers: { Authorization: `Bearer ${token}` },
        });
        setTodos(response.data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUsername();
    fetchTodos();
  }, []);

  const handleDeleteItem = async (id) => {
    try {
      const response = await axios.delete(`${apiUrl}/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setTodos(todos.filter((todo) => todo.id !== id));
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      <h1>Hello {username}</h1>
      <ul>
        {todos.map((todo) => (
          <li key={todo.id}>
            <Todo
              handleDeleteItem={handleDeleteItem}
              token={token}
              item={todo}
            />
          </li>
        ))}
      </ul>
      <button onClick={() => (window.location = "/logout")}>Logout</button>
    </>
  );
};

export default TodoListPage;
