package com.togedocs.backend.api.controller;

import com.togedocs.backend.api.dto.ApilogsRequest;
import com.togedocs.backend.api.dto.ApilogsResponse;
import com.togedocs.backend.api.exception.IdNotFoundException;
import com.togedocs.backend.api.service.ApilogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/logs")
public class ApilogsController {
    @Autowired
    private ApilogsService apilogsService;

    @GetMapping("/{projectId}/{rowId}")
    public ResponseEntity<?> getLogs(@PathVariable Long projectId, @PathVariable String rowId) {
        ApilogsResponse.Logs response;
        try {
            response = apilogsService.getLogs(projectId, rowId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

    @PostMapping("/{projectId}/{rowId}")
    public ResponseEntity<?> addLog(@PathVariable Long projectId, @PathVariable String rowId, @RequestBody ApilogsRequest.LogDto request) {
        ApilogsResponse.LogIdsAndTime response;
        try {
            response = apilogsService.addLog(projectId, rowId, request);
        } catch (IdNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Unexpected exception");
        }
        return ResponseEntity.status(200).body(response);
    }

}
