package br.com.sicredi.api.stub;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.model.Ruling;
import br.com.sicredi.api.model.Session;

import static br.com.sicredi.api.stub.SessionStub.*;

public class RulingStub {

    private static final FakeData FAKE_DATA = FakeData.getInstance();

    public static Ruling openRuling() {
        return createRuling(sessionDefault());
    }

    public static Ruling endingRuling() {
        return createRuling(endingVoteSession());
    }

    public static Ruling rulingWithoutSession() {
        return Ruling.builder()
                .id(FAKE_DATA.generatedId())
                .name(FAKE_DATA.generateFunnyName())
                .build();
    }

    public static Ruling rulingYesVotes() {
        return createRuling(returnSessionYesVotes());
    }

    public static Ruling rulingDrawVotes() {
        return createRuling(returnSessionDrawVotes());
    }

    private static Ruling createRuling(Session session) {
        return Ruling.builder()
                .id(FAKE_DATA.generatedId())
                .name(FAKE_DATA.generateFunnyName())
                .session(session)
                .build();
    }
}
