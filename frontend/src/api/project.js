import { api, apiInstanceWithAuthorization } from "./index.js";

//자신의 프로젝트 리스트 가져오기
function getProjects(success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .get("/user/project")
    .then(success)
    .catch(fail);
}

//프로젝트 생성
function createProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .post("/project", payload.requestBody)
    .then(success)
    .catch(fail);
}

//프로젝트 삭제
function deleteProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .delete(`/project/${projectId}`)
    .then(success)
    .catch(fail);
}

// 초대 코드로 프로젝트 조회
function getProjectByCode(payload, success, fail) {
  let code = payload.pathVariable.code;
  return apiInstanceWithAuthorization(accessToken)
    .get(`/project/code/${code}`)
    .then(success)
    .catch(fail);
}

//프로젝트 초대 코드 들어가기
function joinProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  let code = payload.requestBody.code;
  return apiInstanceWithAuthorization(accessToken)
    .post("/project/join", { code: code })
    .then(success)
    .catch(fail);
}

//팀원 관리 및 조회(팀원목록 & 초대 코드)
function getMemberManageInfo(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken");
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .get(`/project/${projectId}/members`)
    .then(success)
    .catch(fail);
}

//팀원 추방
function removeMember(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  let userId = payload.pathVariable.userId;
  return apiInstanceWithAuthorization(accessToken)
    .delete(`/project/${projectId}/member/${userId}`)
    .then(success)
    .catch(fail);
}

//팀원 권한 수정
function updateMemberRole(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .patch(`api/project/${projectId}/member`)
    .then(success)
    .catch(fail);
}

export {
  getProjects,
  createProject,
  deleteProject,
  joinProject,
  getMemberManageInfo,
  removeMember,
  updateMemberRole,
  getProjectByCode
};
