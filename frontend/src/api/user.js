import {  apiInstanceWithAuthorization, api} from "./index.js";
import jwt_decode from "jwt-decode";

// const api = apiInstanceWithAuthorization();
//회원 정보 수정
async function modifyUserInfo(paylaod, success, fail) {

  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken).patch("/user/info", paylaod.requestBody).then(success).catch(fail);
}

async function getUserNameAndImgNo(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .get("/user/info/" + payload.pathVariable.userId)
    .then(success)
    .catch(fail);
}

//회원탈퇴
async function withdrawUser(payload, success, fail) {
  payload;
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken).delete("/user/withdraw").then(success).catch(fail);
}

//로그아웃
async function logoutUser(success,fail){
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken).post("/logout").then(success).catch(fail);
}

async function checkExp(token){
  let exp = localStorage.getItem("exp");
  let now = new Date().getTime().toString();
  now=now.substring(0,10);
  let time = exp - now;
  let userInfo = jwt_decode(token);
  if(time<=60){//1분 미만으로 남았다면
    var params = {
      imgNo: userInfo.imgNo,
      name:userInfo.name,
      email:userInfo.sub,
      userId:userInfo.userId,
    }
    await api.post("/refresh",params).then((data)=>{
      localStorage.setItem("accessToken",data.data);
      userInfo = jwt_decode(data.data);
      localStorage.setItem("exp",userInfo.exp);
      
    })
    
  }
}


export { modifyUserInfo, getUserNameAndImgNo, withdrawUser,logoutUser };
