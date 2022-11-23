package br.com.sicredi.api.repository;

import br.com.sicredi.api.domain.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionRepository extends MongoRepository <Session, String> {


}
