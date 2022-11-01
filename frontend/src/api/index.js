import axios from "axios";

const BASEURL = "http://localhost:8081/api/v1";

function apiInstance() {
  const instance = axios.create({
    baseURL: BASEURL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

export { BASEURL, apiInstance };
