import { apiInstanceWithAuthorization } from "./index.js";

const api = apiInstanceWithAuthorization();
//회원 정보 수정
function modifyUserInfo(paylaod, success, fail) {
  return api.patch("/user/info", paylaod.requestBody).then(success).catch(fail);
}

function getUserNameAndImgNo(payload, success, fail) {
  return api
    .get("/user/info/" + payload.pathVariable.userId)
    .then(success)
    .catch(fail);
}

//회원탈퇴
function withdrawUser(payload, success, fail) {
  payload;
  return api.delete("/user/withdraw").then(success).catch(fail);
}

export { modifyUserInfo, getUserNameAndImgNo, withdrawUser };
