package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.api.service.ApidocsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/docs")
public class ApidocsController {

    private final ApidocsService apidocsService;

    @PostMapping("/{projectId}/rows")
    public ResponseEntity<String> addRow(@PathVariable Long projectId) {
        apidocsService.addRow(projectId);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 행을 추가했습니다!");
    }

    @PostMapping("/{projectId}/cols")
    public ResponseEntity<String> addCol(@PathVariable Long projectId, @RequestBody @Valid ApidocsRequest.AddColRequest request) {
        apidocsService.addCol(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 열을 추가했습니다!");
    }

    @PatchMapping("/{projectId}/rows")
    public ResponseEntity<String> moveRow(@PathVariable Long projectId, @RequestBody @Valid ApidocsRequest.MoveItemRequest request) {
        apidocsService.moveRow(projectId, request);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 행을 이동했습니다!");
    }

    @PatchMapping("/{projectId}/cols")
    public ResponseEntity<String> moveCol(@PathVariable Long projectId, @RequestBody @Valid ApidocsRequest.MoveItemRequest request) {
        apidocsService.moveCol(projectId, request);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 열을 이동했습니다!");
    }

    @DeleteMapping("/{projectId}/rows/{rowId}")
    public ResponseEntity<Void> deleteRow(@PathVariable Long projectId, @PathVariable String rowId) {
        apidocsService.deleteRow(projectId, rowId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{projectId}/cols/{colId}")
    public ResponseEntity<Void> deleteCol(@PathVariable Long projectId, @PathVariable String colId) {
        apidocsService.deleteCol(projectId, colId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{projectId}/cell")
    public ResponseEntity<String> updateCell(@PathVariable Long projectId, @RequestBody @Valid ApidocsRequest.UpdateCellRequest request) {
        apidocsService.updateCell(projectId, request);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 셀을 수정했습니다!");
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApidocsResponse.Apidocs> getDocs(@PathVariable Long projectId) {
        ApidocsResponse.Apidocs response = apidocsService.getDocs(projectId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<String> updateProjectInfo(@PathVariable Long projectId, @RequestBody @Valid ApidocsRequest.UpdateProjectInfoRequest request) {
        apidocsService.updateProjectInfo(projectId, request);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 프로젝트 정보를 수정했습니다!");
    }

    @PatchMapping("/{projectId}/cols/{colId}")
    public ResponseEntity<String> updateCol(@PathVariable Long projectId, @PathVariable String colId, @RequestBody @Valid ApidocsRequest.UpdateColRequest request) {
        apidocsService.updateCol(projectId, colId, request);
        return ResponseEntity.status(HttpStatus.OK).body("성공적으로 열 정보를 수정했습니다!");
    }
}
