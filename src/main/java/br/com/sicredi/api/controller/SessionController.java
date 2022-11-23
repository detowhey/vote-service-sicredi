package br.com.sicredi.api.controller;

import br.com.sicredi.api.domain.Session;
import br.com.sicredi.api.dto.SessionDTO;
import br.com.sicredi.api.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "v1/api/session", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable String id) {
        return ResponseEntity.ok().body(new SessionDTO(sessionService.getSessionById(id)));
    }

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAllSessions() {
        return ResponseEntity.ok().body(transformToDTO(sessionService.getAllSessions()));
    }

    private List<SessionDTO> transformToDTO(List<Session> sessionList) {
        return sessionList.stream().map(SessionDTO::new).collect(Collectors.toList());
    }
}
