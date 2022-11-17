import { apiInstanceWithAuthorization } from "./index.js";

const api = apiInstanceWithAuthorization();

async function getProjects(success, fail) {
  return await api.get("/user/project").then(success).catch(fail);
}

//프로젝트 생성
function postNewProject(payload, success, fail) {
  return api.post("/project", payload).then(success).catch(fail);
}

//프로젝트 삭제
function deleteProject(payload, success, fail) {
  return api.delete(`/project/${payload}`).then(success).catch(fail);
}

//프로젝트 초대 코드 들어가기
function joinProject(payload, success, fail) {
  return api.post("/project/join", payload).then(success).catch(fail);
}

//팀원 관리 및 조회(팀원목록 & 초대 코드)
function getMemberManageInfo(payload, success, fail) {
  let projectid = payload.pathVariable.projectId;

  return api.get(`/project/${projectid}/members`).then(success).catch(fail);
}

//팀원 추방
function removeMember(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  let userId = payload.pathVariable.userId;
  return api
    .delete(`/project/${projectId}/member/${userId}`)
    .then(success)
    .catch(fail);
}

//팀원 권한 수정
function updateMemberRole(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  let data = payload.requestBody;
  return api.patch(`api/project/${projectId}/member`, data)
    .then(success)
    .catch(fail);
}

export {
  getProjects,
  postNewProject,
  deleteProject,
  joinProject,
  getMemberManageInfo,
  removeMember,
  updateMemberRole,
};
