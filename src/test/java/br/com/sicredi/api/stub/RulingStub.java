package br.com.sicredi.api.stub;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Ruling;
import br.com.sicredi.api.domain.Session;

import static br.com.sicredi.api.stub.SessionStub.*;


public class RulingStub {

    private static final FakeData fakeData = new FakeData();

    public static Ruling openRuling() {
        return getRuling(sessionDefault());
    }

    public static Ruling endingRuling() {
        return getRuling(endingVoteSession());
    }

    public static Ruling rulingWithoutSession() {
        return getRulingWithoutSession();
    }

    public static Ruling rulingYesVotes() {
        return getRuling(returnSessionYesVotes());
    }

    public static Ruling rulingDrawVotes() {
        return getRuling(returnSessionDrawVotes());
    }

    private static Ruling getRuling(Session session) {
        return Ruling.builder()
                .id(fakeData.generatedId())
                .name(fakeData.generateFunnyName())
                .session(session)
                .build();
    }

    private static Ruling getRulingWithoutSession() {
        return Ruling.builder()
                .id(fakeData.generatedId())
                .name(fakeData.generateFunnyName())
                .build();
    }
}
