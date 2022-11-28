package br.com.sicredi.api.repository;

import br.com.sicredi.api.domain.Member;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends MongoRepository<Member, String> {

    @Query("{ 'cpf': ?0 }")
    Member findByCpfMember(@Param("cpf") String cpf);
}
