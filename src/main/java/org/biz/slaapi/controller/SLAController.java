package org.biz.slaapi.controller;

import org.biz.slaapi.model.SlaRequest;
import org.biz.slaapi.model.SlaResponse;
import org.biz.slaapi.service.SlaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api/sla")
public class SLAController {
    private final SlaService slaService;

    public SLAController(SlaService slaService) {
        this.slaService = slaService;
    }

    @RequestMapping("/calculate")
    public ResponseEntity<SlaResponse> calculateSLA(@RequestBody SlaRequest slaRequest) {
        return ResponseEntity.ok().body(new SlaResponse(slaService.getDueDate(slaRequest)));
    }
}
