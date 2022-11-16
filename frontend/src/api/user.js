import { apiInstanceWithAuthorization } from "./index.js";

const api = apiInstanceWithAuthorization();
//회원 정보 수정
function modifyUserInfo(paylaod, success, fail) {
  return api.patch("/user/info", paylaod).then(success).catch(fail);
}

//회원탈퇴
function withdrawUser(success, fail) {
  return api.delete("/user/withdraw").then(success).catch(fail);
}

//이름가져오기
function getMyName(success, fail) {
  return api.get("/user/myname").then(success).catch(fail);
}
export { modifyUserInfo, withdrawUser, getMyName };
