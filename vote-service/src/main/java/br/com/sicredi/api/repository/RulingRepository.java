package br.com.sicredi.api.repository;

import br.com.sicredi.api.model.Ruling;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RulingRepository extends MongoRepository<Ruling, String> {

    @Query("{ 'name': {$regex: ?0, $options: 'i'}}")
    Page<Ruling> findByName(@Param("name") String name, Pageable pageable);

    Page<Ruling> findByStatus(String status, Pageable pageable);

    List<Ruling> findByStatus(String status);
}
