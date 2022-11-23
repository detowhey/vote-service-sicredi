package br.com.sicredi.api.service;

import br.com.sicredi.api.domain.Session;
import br.com.sicredi.api.exception.ObjectNotFoundException;
import br.com.sicredi.api.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(String id) {
        return sessionRepository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException("Session with this id " + id + " not found");
        });
    }
}
