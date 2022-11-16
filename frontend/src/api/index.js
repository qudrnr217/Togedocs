import axios from "axios";
// import userStore from "@/store/modules/userStore";
const BASEURL = "http://localhost:8081/api";
// const BASEURL = "http://k7a404.p.ssafy.io:8081/api";

const api = axios.create({
  baseURL: BASEURL,
  headers: {
    "Contents-type": "application/json",
  },
});

function apiInstanceWithAuthorization() {
  const instance = axios.create({
    baseURL: BASEURL,
    headers: {
      Accept: "application/json",
      "Content-type": "application/json",
      Authorization: "Bearer " + localStorage.getItem("accessToken"),
    },
  });
  return instance;
}

export { BASEURL, api, apiInstanceWithAuthorization };
