import { apiInstanceWithAuthorization } from "./index.js";

// const api = apiInstanceWithAuthorization(accessToken);
//자신의 프로젝트 리스트 가져오기
function getProjects(success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .get("/user/project")
    .then(success)
    .catch(fail);
}

//프로젝트 생성
function postNewProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .post("/project", payload)
    .then(success)
    .catch(fail);
}

//프로젝트 삭제
function deleteProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .delete(`/project/${payload}`)
    .then(success)
    .catch(fail);
}

//프로젝트 초대 코드 들어가기
function joinProject(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .post("/project/join", payload)
    .then(success)
    .catch(fail);
}

//팀원 관리 및 조회(팀원목록 & 초대 코드)
function manageMember(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken");
  let projectId = payload.pathVariable.projectId;
  return apiInstanceWithAuthorization(accessToken)
    .get(`/project/${projectId}/members`)
    .then(success)
    .catch(fail);
}

//팀원 추방
function exportMember(projectId, userId, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .delete(`/project/${projectId}/member/${userId}`)
    .then(success)
    .catch(fail);
}

//팀원 권한 수정
function changeRole(payload, success, fail) {
  let accessToken = localStorage.getItem("accessToken")
  return apiInstanceWithAuthorization(accessToken)
    .patch(`api/project/${payload}/member`)
    .then(success)
    .catch(fail);
}

export {
  getProjects,
  postNewProject,
  deleteProject,
  joinProject,
  manageMember,
  exportMember,
  changeRole,
};
