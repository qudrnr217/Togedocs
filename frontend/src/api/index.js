import axios from "axios";

const BASEURL = "http://localhost:8081/api/";

const api = axios.create({
  baseURL: BASEURL,
  headers: {
    "Contents-type": "application/json",
  },
});

export { BASEURL, api };
