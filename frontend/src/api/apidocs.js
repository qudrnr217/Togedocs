import { api } from "./index.js";


function addRow(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  return api
    .post("/docs/" + projectId + "/rows")
    .then(success)
    .catch(fail);
}
function addCol(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  let data = payload.requestBody;

  console.log(payload);
  return api
    .post("/docs/" + projectId + "/cols", { name: data.name, type: data.type })
    .then(success)
    .catch(fail);
}
function moveRow(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  return api
    .patch("/docs/" + projectId + "/rows", payload.requestBody)
    .then(success)
    .catch(fail);
}
function moveCol(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  return api
    .patch("/docs/" + projectId + "/cols", payload.requestBody)
    .then(success)
    .catch(fail);
}
function deleteRow(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  let rowId = payload.pathVariable.rowId;
  return api
    .delete("/docs/" + projectId + "/rows/" + rowId)
    .then(success)
    .catch(fail);
}
function deleteCol(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  let colId = payload.pathVariable.colId;
  return api
    .delete("/docs/" + projectId + "/cols/" + colId)
    .then(success)
    .catch(fail);
}
function updateCell(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
  return api
    .patch("/docs/" + projectId, payload.requestBody)
    .then(success)
    .catch(fail);
}
function getDocs(payload, success, fail) {
  let projectId = payload.pathVariable.projectId;
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
