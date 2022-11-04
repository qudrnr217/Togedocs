import axios from "axios";
import userStore from "@/store/modules/userStore";
const BASEURL = "https://k7a404.p.ssafy.io/api/";

function apiInstance() {
  const instance = axios.create({
    baseURL: BASEURL,
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

function apiInstanceWithAuthorization() {
  const instance = axios.create({
    baseURL: BASEURL,
    headers: {
      Accept: "application/json",
      "Content-type": "application/json",
      Authorization: userStore.state.accessToken,
    },
  });
  return instance;
}

export { BASEURL, apiInstance, apiInstanceWithAuthorization };
