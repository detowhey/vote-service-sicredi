package br.com.sicredi.api.stub;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.model.Vote;
import br.com.sicredi.api.model.enu.VoteOption;

public class VoteStub {
    private static final FakeData FAKE_DATA = FakeData.getInstance();
    public static Vote createVote(VoteOption voteOption) {
        return new Vote(FAKE_DATA.generatedId(), FAKE_DATA.generateCpf(), voteOption);
    }
}
