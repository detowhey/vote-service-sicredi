package br.com.sicredi.api.repository;

import br.com.sicredi.api.domain.Ruling;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RulingRepository extends MongoRepository<Ruling, String> {

    @Query("{ 'name': {$regex: ?0, $options: 'i'}}")
    List<Ruling> findByName(@Param("name") String name);

    @Query("{ 'status': ?0}")
    List<Ruling> findAllByStatus(String status);
}
