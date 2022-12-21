package br.com.sicredi.api.stub;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Session;

import static br.com.sicredi.api.stub.SessionStub.*;


public class RulingStub {

    private static final FakeData fakeData = new FakeData();

    public static Ruling openRuling() {
        return createRuling(sessionDefault());
    }

    public static Ruling endingRuling() {
        return createRuling(endingVoteSession());
    }

    public static Ruling rulingWithoutSession() {
        return createRulingWithoutSession();
    }

    public static Ruling rulingYesVotes() {
        return createRuling(returnSessionYesVotes());
    }

    public static Ruling rulingDrawVotes() {
        return createRuling(returnSessionDrawVotes());
    }

    private static Ruling createRuling(Session session) {
        return Ruling.builder()
                .id(fakeData.generatedId())
                .name(fakeData.generateFunnyName())
                .session(session)
                .build();
    }

    private static Ruling createRulingWithoutSession() {
        return Ruling.builder()
                .id(fakeData.generatedId())
                .name(fakeData.generateFunnyName())
                .build();
    }
}
