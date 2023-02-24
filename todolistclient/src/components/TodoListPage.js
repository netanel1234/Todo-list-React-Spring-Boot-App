import { useState, useEffect } from "react";
import axios from "axios";
import Todo from "./Todo";

const apiUrl = "http://localhost:8080/api";

const TodoListPage = () => {
  const [token, setToken] = useState("");
  const [username, setUsername] = useState("");
  const [todos, setTodos] = useState([
    { id: 1, task: "Task #1", completed: true },
    { id: 2, task: "Task #2", completed: false },
    { id: 3, task: "Task #3", completed: true },
    { id: 4, task: "Task #4", completed: false },
    { id: 5, task: "Task #5", completed: true },
  ]);

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

  const handleLogout = () => {};

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
