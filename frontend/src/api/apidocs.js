import { apiInstance } from "./index.js";

const api = apiInstance();

function addRow(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .post("/docs/" + projectId + "/rows")
    .then(success)
    .catch(fail);
}
function addCol(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .post("/docs/" + projectId + "/cols", payload.requestbody)
    .then(success)
    .catch(fail);
}
function moveRow(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .patch("/docs/" + projectId + "/rows", payload.requestbody)
    .then(success)
    .catch(fail);
}
function moveCol(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .patch("/docs/" + projectId + "/cols", payload.requestbody)
    .then(success)
    .catch(fail);
}
function deleteRow(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .delete("/docs/" + projectId + "/rows")
    .then(success)
    .catch(fail);
}
function deleteCol(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .delete("/docs/" + projectId + "/cols")
    .then(success)
    .catch(fail);
}
function updateCell(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .patch("/docs/" + projectId, payload.requestbody)
    .then(success)
    .catch(fail);
}
function getDocs(payload, success, fail) {
  let projectId = payload.pathvariable;
  return api
    .get("/docs/" + projectId)
    .then(success)
    .catch(fail);
}

export {
  addRow,
  addCol,
  moveRow,
  moveCol,
  deleteRow,
  deleteCol,
  updateCell,
  getDocs,
};
