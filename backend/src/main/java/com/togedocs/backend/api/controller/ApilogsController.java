package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ApilogsDto;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.api.service.ApilogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
public class ApilogsController {
    @Autowired
    private ApilogsService apilogsService;

    @PostMapping("/{projectId}/{rowId}")
    public ResponseEntity<?> addLog(@PathVariable Long projectId, @PathVariable String rowId, @RequestBody ApilogsDto.LogDto request) {
        ApilogsDto.LogDto response;
        try {
            response = apilogsService.addLog(projectId, rowId, request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e);
        }
        return ResponseEntity.status(200).body(response);
    }

}
