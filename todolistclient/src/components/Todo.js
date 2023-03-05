import { useState } from "react";
import axios from "axios";

const apiUrl = "http://localhost:8080/api/items";

const Todo = ({ handleDeleteItem, setError, token, item }) => {
  const [todoItem, setTodoItem] = useState(item);

  const handleTaskUpdate = async () => {
    try {
      setTodoItem({ ...todoItem, task: todoItem.task });

      const response = await axios.put(
        `${apiUrl}/todos/${todoItem.id}`,
        { task: todoItem.task },
        { headers: { Authorization: `Bearer ${token}` } }
      );
    } catch (error) {
      setError(error.data);
    }
  };

  return (
    <>
      completed:
      <input
        type="checkbox"
        checked={todoItem.completed}
        onChange={handleTaskUpdate}
      />
      <input
        type="text"
        value={todoItem.task}
        onChange={(e) => setTodoItem({ ...todoItem, task: e.target.value })}
      />
      <button style={{ backgroundColor: "blue" }} onClick={handleTaskUpdate}>
        Update
      </button>
      <button
        style={{ backgroundColor: "red" }}
        onClick={() => handleDeleteItem(todoItem.id)}
      >
        Delete
      </button>
    </>
  );
};

export default Todo;
