import { useState } from "react";
import axios from "axios";

const apiUrl = "http://localhost:8080/api/items";

const Todo = ({ handleDeleteItem, token, id, task }) => {
  const [currTask, setCurrTask] = useState(task);

  const handleUpdate = async () => {
    try {
      const response = await axios.get(apiUrl + "/" + id, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setCurrTask(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div>
      {currTask}
      <button style={{ backgroundColor: "blue" }} onClick={handleUpdate}>
        Update
      </button>
      <button
        style={{ backgroundColor: "red" }}
        onClick={() => handleDeleteItem(id)}
      >
        Delete
      </button>
    </div>
  );
};

export default Todo;
