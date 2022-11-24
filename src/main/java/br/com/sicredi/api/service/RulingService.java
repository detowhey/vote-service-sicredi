package br.com.sicredi.api.service;

import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Session;
import br.com.sicredi.api.dto.RulingDTO;
import br.com.sicredi.api.exception.DataBaseException;
import br.com.sicredi.api.exception.ObjectNotFoundException;
import br.com.sicredi.api.repository.RulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RulingService {

    @Autowired
    private RulingRepository rulingRepository;

    public Ruling save(Ruling ruling) {
        try {
            rulingRepository.insert(ruling);
            return ruling;
        } catch (DataBaseException e) {
            throw new DataBaseException("Could not connect to the database");
        }
    }

    public List<Ruling> returnAllRulings() {
        return rulingRepository.findAll();
    }

    public List<Ruling> findByNameRegex(String name) {
        List<Ruling> rulingList = rulingRepository.findByName(name);

        if (rulingList.isEmpty())
            throw new ObjectNotFoundException("There isn't Ruling with this name " + name);

        return rulingList;
    }

    public Ruling findById(String id) {
        return rulingRepository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException("Ruling with this id " + id + " not found");
        });
    }

    public Ruling fromDTO(RulingDTO rulingDTO) {
        return new Ruling(new Session(rulingDTO.getNumberVanancies()), rulingDTO.getName());
    }
}
