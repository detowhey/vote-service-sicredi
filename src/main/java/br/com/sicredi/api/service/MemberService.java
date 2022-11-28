package br.com.sicredi.api.service;

import br.com.sicredi.api.domain.Member;
import br.com.sicredi.api.exception.ObjectNotFoundException;
import br.com.sicredi.api.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<Member> returnAllMembers() {
        return memberRepository.findAll();
    }

    public Member findByCpf(String cpf) {
        return memberRepository.findByCpfMember(cpf);
    }

    public Member findById(String id) {
        return memberRepository.findById(id).orElseThrow(() -> {
            throw new ObjectNotFoundException("Member with this id " + id + " not found");
        });
    }
}
