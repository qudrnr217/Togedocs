import axios from "axios";
import userStore from "@/store/modules/userStore";
// const BASEURL = "http://localhost:8081";
const BASEURL = "http://k7a404.p.ssafy.io:8081";

const api = axios.create({
  baseURL: BASEURL + "/api",
  headers: {
    "Contents-type": "application/json",
  },
});

function apiInstanceWithAuthorization(accessToken) {
  const instance = axios.create({
    baseURL: BASEURL + "/api",
    headers: {
      Accept: "application/json",
      "Contents-type": "application/json",
      Authorization: "Bearer " + accessToken,
    },
  });

  return instance;
}

function apiInstanceWithAuthorization2(accessToken) {
  const instance = axios.create({
    baseURL: BASEURL + "/api",
    headers: {
      Accept: "application/json",
      "Contents-type": "application/json",
      Authorization: "Bearer " + accessToken,
    },
  });
  console.log(userStore.state.accessToken);
  return instance;
}
export {
  BASEURL,
  api,
  apiInstanceWithAuthorization,
  apiInstanceWithAuthorization2,
};
