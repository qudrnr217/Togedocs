import axios from "axios";
import userStore from "@/store/modules/userStore";
const BASEURL = "https://k7a404.p.ssafy.io/api/";

const api = axios.create({
  baseURL: BASEURL,
  headers: {
    "Contents-type": "application/json",
  },
});

<<<<<<< HEAD
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
=======
export { BASEURL, api };
>>>>>>> 0da39286d4bb92bcc141d75bde0dee1c5c7f59bf
