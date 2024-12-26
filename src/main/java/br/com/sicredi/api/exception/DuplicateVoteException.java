package br.com.sicredi.api.exception;

public class DuplicateVoteException extends RuntimeException {

    public DuplicateVoteException(String memberId) {
        super("The member with id '%s' has already voted for this session.".formatted(memberId));
    }
}
