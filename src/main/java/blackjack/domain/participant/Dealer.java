package blackjack.domain.participant;

import blackjack.domain.MatchResult;
import blackjack.domain.card.Card;
import java.util.EnumMap;

public class Dealer extends Participant {

    private static final int DEALER_CARD_PIVOT = 17;
    private static final String DEALER_NAME = "딜러";


    private final EnumMap<MatchResult, Integer> matchResultScores = new EnumMap(MatchResult.class);

    public Dealer() {
        super.name = new Name(DEALER_NAME);
        initResultScores();
    }

    private void initResultScores() {
        for (MatchResult matchResult : MatchResult.values()) {
            matchResultScores.put(matchResult, 0);
        }
    }

    public boolean shouldReceive() {
        return cardHand.getScore() < DEALER_CARD_PIVOT;
    }

    public Card getOpenCard() {
        return cardHand.getCards().get(0);
    }

    public EnumMap<MatchResult, Integer> getMatchResultScores() {
        return matchResultScores;
    }

    public Integer getMatchResultScore(MatchResult matchResult) {
        return matchResultScores.get(matchResult);
    }

    public void decideMatchResult(Player player) {
        MatchResult matchResult = cardHand.compareMatchResult(player.getCardHand());
        matchResultScores.put(matchResult, matchResultScores.get(matchResult) + 1);
    }
}
