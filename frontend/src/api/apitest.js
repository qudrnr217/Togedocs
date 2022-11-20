import { api } from "./index.js";

function getLogs(payload, success, fail) {
  let projectId = payload.pathVariable.projectId,
    rowId = payload.pathVariable.rowId;
  return api
    .get("/logs/" + projectId + "/" + rowId)
    .then(success)
    .catch(fail);
}

function addLog(payload, success, fail) {
  let projectId = payload.pathVariable.projectId,
    rowId = payload.pathVariable.rowId;
  let requestBody = payload.requestBody;
  return api
    .post("/logs/" + projectId + "/" + rowId, requestBody)
    .then(success)
    .catch(fail);
}

export { getLogs, addLog };
