import { useState } from "react";
import SockJS from "sockjs-client";
import { over } from 'stompjs'
import './style/style.css'

var stompClient
function App() {
  const [newTodo, setNewTodo] = useState("")
  const [todos, setTodos] = useState()
  const connect = () => {
    let sockjs = new SockJS("http://localhost:8080/ws");
    stompClient = over(sockjs);
    stompClient.connect({}, onConnected, onError);
  };
  const onConnected = (e) => {
    console.log("Connected")
    stompClient.subscribe(
      "/topic/todos",
      onMessageReceived
    );
  }
  const onError = (e) => {
    console.log(e)
  }
  const onMessageReceived = (e) => {
    const data = JSON.parse(e.body)
    if (Array.isArray(data.response)) {
      setTodos(data.response)
    }
    console.log(data.status)
  }
  const sendMessage = (msg) => {
    if (msg.trim() !== "") {
      const message = {
        command: "addLog",
        content: msg
      };

      stompClient.send("/app/message", {}, JSON.stringify(message));
    }
    setNewTodo("")
  };
  const getAll = () => {
    const message = {
      command: "logs",
      content: null
    }
    stompClient.send("/app/message", {}, JSON.stringify(message));
  }
  return (
    <div className="container">
      <div>
        <div className="container_inner">
          <div className="connect">
            <input type="submit" value="connect" className="connect_button" onClick={() => {
              connect()
            }} />
          </div>
          <div className="add">
            <input type="text" placeholder="New Todo" value={newTodo} className="new_todo" onChange={(e) => {
              setNewTodo(e.target.value)
            }} />
            <input type="submit" value="Add todo" className="add_todo" onClick={() => {
              sendMessage(newTodo)
            }} />
          </div>
          <div>
            <input type="submit" value="Get all" className="get_all_button" onClick={() => {
              getAll()
            }} />
          </div>
        </div>
        <div className="todos">
          {
            todos?.map(todo => (
              <div className="todo_item">{todo.text}</div>
            ))
          }
        </div>
      </div>
    </div>
  );
}

export default App;
