import axios from "axios";

function apiInstance() {
  const instance = axios.create({
    baseURL: "http://localhost:8081/api/v1",
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

export { apiInstance };
