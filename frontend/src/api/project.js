import { apiInstanceWithAuthorization,api } from "./index.js";
import jwt_decode from "jwt-decode";
//자신의 프로젝트 리스트 가져오기
async function getProjects(success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .get("/user/project")
    .then(success)
    .catch(fail);
}

//프로젝트 생성
async function createProject(payload, success, fail) {
  
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .post("/project", payload.requestBody)
    .then(success)
    .catch(fail);
}

//프로젝트 삭제
async function deleteProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .delete(`/project/${projectId}`)
    .then(success)
    .catch(fail);
}

function leaveProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .delete(`/project/${projectId}/leave`)
    .then(success)
    .catch(fail);
}

// 초대 코드로 프로젝트 조회
async function getProjectByCode(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  let code = payload.pathVariable.code;
  return apiInstanceWithAuthorization(accessToken)
    .get(`/project/code/${code}`)
    .then(success)
    .catch(fail);
}

//프로젝트 초대 코드 들어가기
async function joinProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  let code = payload.requestBody.code;
  return apiInstanceWithAuthorization(accessToken)
    .post("/project/join", { code: code })
    .then(success)
    .catch(fail);
}

//팀원 관리 및 조회(팀원목록 & 초대 코드)
async function getMemberManageInfo(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .get(`/project/${projectId}/members`)
    .then(success)
    .catch(fail);
}

//팀원 추방
async function removeMember(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  let userId = payload.pathVariable.userId;
  return apiInstanceWithAuthorization(accessToken)
    .delete(`/project/${projectId}/member/${userId}`)
    .then(success)
    .catch(fail);
}

//팀원 권한 수정
async function updateMemberRole(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  await checkExp(accessToken)
  accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .patch(`/project/${projectId}/member`, payload.requestBody)
    .then(success)
    .catch(fail);
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

export { 
  getProjects,
  createProject,
  deleteProject,
  leaveProject,
  joinProject,
  getMemberManageInfo,
  removeMember,
  updateMemberRole,
  getProjectByCode
};
