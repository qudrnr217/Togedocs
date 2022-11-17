import { apiInstanceWithAuthorization } from "./index.js";

// const api = apiInstanceWithAuthorization();
//회원 정보 수정
function modifyUserInfo(paylaod, success, fail) {

  let accessToken = localStorage.getItem("accessToken");
  return apiInstanceWithAuthorization(accessToken).patch("/user/info", paylaod.requestBody).then(success).catch(fail);
}

function getUserNameAndImgNo(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken");
  return apiInstanceWithAuthorization(accessToken)
    .get("/user/info/" + payload.pathVariable.userId)
    .then(success)
    .catch(fail);
}

//회원탈퇴
function withdrawUser(payload, success, fail) {
  payload;
  let accessToken = localStorage.getItem("accessToken");
  return apiInstanceWithAuthorization(accessToken).delete("/user/withdraw").then(success).catch(fail);
}

export { modifyUserInfo, getUserNameAndImgNo, withdrawUser };
