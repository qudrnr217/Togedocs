package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ApidocsRequest;
import com.togedocs.backend.api.dto.ApidocsResponse;
import com.togedocs.backend.api.exception.NotEnoughArgsException;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.api.service.ApidocsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/docs")
public class ApidocsController {

    @Autowired
    private ApidocsService apidocsService;

    @PostMapping("/{projectId}/rows")
    public ResponseEntity<?> addRow(@PathVariable Long projectId) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.addRow(projectId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/{projectId}/cols")
    public ResponseEntity<?> addCol(@PathVariable Long projectId, @RequestBody ApidocsRequest.AddColRequest request) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.addCol(projectId, request);
        } catch (NotEnoughArgsException e) {
            return ResponseEntity.status(500).body(request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/{projectId}/rows")
    public ResponseEntity<?> moveRow(@PathVariable Long projectId, @RequestBody ApidocsRequest.MoveItemRequest request) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.moveRow(projectId, request);
        } catch (NotEnoughArgsException e) {
            return ResponseEntity.status(500).body(request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/{projectId}/cols")
    public ResponseEntity<?> moveCol(@PathVariable Long projectId, @RequestBody ApidocsRequest.MoveItemRequest request) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.moveCol(projectId, request);
        } catch (NotEnoughArgsException e) {
            return ResponseEntity.status(500).body(request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @DeleteMapping("/{projectId}/rows/{rowId}")
    public ResponseEntity<?> deleteRow(@PathVariable Long projectId, @PathVariable String rowId) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.deleteRow(projectId, rowId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(204).body(response);
    }

    @DeleteMapping("/{projectId}/cols/{colId}")
    public ResponseEntity<?> deleteCol(@PathVariable Long projectId, @PathVariable String colId) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.deleteCol(projectId, colId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(204).body(response);
    }

    @PatchMapping("/{projectId}/cell")
    public ResponseEntity<?> updateCell(@PathVariable Long projectId, @RequestBody ApidocsRequest.UpdateCellRequest request) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.updateCell(projectId, request);
        } catch (NotEnoughArgsException e) {
            return ResponseEntity.status(500).body(request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getDocs(@PathVariable Long projectId) {
        ApidocsResponse.Apidocs response;
        try {
            response = apidocsService.getDocs(projectId);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/{projectId}")
    public ResponseEntity<?> updateProjectInfo(@PathVariable Long projectId, @RequestBody ApidocsRequest.UpdateProjectInfoRequest request) {
        ApidocsResponse.ProjectInfo response;
        try {
            response = apidocsService.updateProjectInfo(projectId, request);
        } catch (NotEnoughArgsException e) {
            return ResponseEntity.status(500).body(request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @PatchMapping("/{projectId}/cols/{colId}")
    public ResponseEntity<?> updateCol(@PathVariable Long projectId, @PathVariable String colId, @RequestBody ApidocsRequest.UpdateColRequest request) {
        ApidocsResponse.Ids response;
        try {
            response = apidocsService.updateCol(projectId, colId, request);
        } catch (NotEnoughArgsException e) {
            return ResponseEntity.status(500).body(request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }
}
