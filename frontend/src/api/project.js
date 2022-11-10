import { apiInstanceWithAuthorization } from "./index.js";

const api = apiInstanceWithAuthorization();

function getProjects(payload, success, fail) {
  return api.get("/user/project").then(success).catch(fail);
}

function postNewProject(payload, success, fail) {
  return api.post("/project", payload).then(success).catch(fail);
}

export { getProjects, postNewProject };
