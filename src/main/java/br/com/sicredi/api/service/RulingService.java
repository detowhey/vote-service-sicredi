package br.com.sicredi.api.service;

import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.dto.RulingDTO;
import br.com.sicredi.api.exception.DataBaseException;
import br.com.sicredi.api.exception.ObjectNotFoundException;
import br.com.sicredi.api.repository.RulingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RulingService {

    @Autowired
    private RulingRepository rulingRepository;

    public Ruling save(RulingDTO rulingDTO) {
        try {
            Ruling ruling = new Ruling(rulingDTO.getId(), rulingDTO.getSession(), rulingDTO.getName());
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
            throw new ObjectNotFoundException("There isn't Ruling with this name" + name);

        return rulingList;
    }

    public Ruling findById(String id) {
        Optional<Ruling> optionalRuling = rulingRepository.findById(id);
        return optionalRuling.orElseThrow(() -> {
            throw new ObjectNotFoundException("Rulling with this id " + id + " not found");
        });
    }
}
