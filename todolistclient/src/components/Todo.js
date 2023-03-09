import { useState, useEffect } from "react";
import axios from "axios";

const apiUrl = "http://localhost:8080/api/items";

const Todo = ({ deleteItem, setError, token, item }) => {
  const [todo, setTodo] = useState({});

  useEffect(() => {
    setTodo(item);
  }, []);

  const updateItem = async () => {
    try {
      await axios.put(`${apiUrl}/todos/${todo.id}`, todo, {
        headers: { Authorization: `Bearer ${token}` },
      });
    } catch (error) {
      setError(error.data);
    }
  };

  return (
    <>
      completed:
      <input
        type="checkbox"
        checked={todo.completed}
        onChange={(e) => setTodo({ ...todo, completed: e.target.checked })}
      />
      <input
        value={todo.task}
        onChange={(e) => setTodo({ ...todo, task: e.target.value })}
      />
      <button style={{ backgroundColor: "blue" }} onClick={updateItem}>
        Update
      </button>
      <button
        style={{ backgroundColor: "red" }}
        onClick={() => deleteItem(todo.id)}
      >
        Delete
      </button>
    </>
  );
};

export default Todo;
