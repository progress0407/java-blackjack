package blackjack.domain.card;

import static blackjack.constant.CommonConstant.BLACKJACK_SYMBOL_NUMBER;
import static blackjack.domain.card.Denomination.ACE_UPPER_SCORE;

import blackjack.constant.MatchResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hand {

    private static final int BLACKJACK_CARD_SIZE = 2;
    private static final int ACE_LOWER_SCORE = 1;
    private static final int ACE_SCORE_DIFFERENCE = ACE_UPPER_SCORE - ACE_LOWER_SCORE;

    private final List<Card> cards = new ArrayList<>();

    public void addAll(Card... cards) {
        this.cards.addAll(List.of(cards));
    }

    public List<Card> getCards() {
        return cards;
    }

    public int getScore() {
        if (hasAce()) {
            return getIncludingAceSum();
        }

        return getExcludingAceSum();
    }

    private int getIncludingAceSum() {
        int sum = getSum() + ACE_SCORE_DIFFERENCE;
        if (sum <= BLACKJACK_SYMBOL_NUMBER) {
            return sum;
        }
        return sum - ACE_SCORE_DIFFERENCE;
    }

    private int getSum() {
        return cards.stream()
                .mapToInt(Card::getScore)
                .sum();
    }

    private int getExcludingAceSum() {
        return cards.stream()
                .filter(card -> !card.isAce())
                .mapToInt(Card::getScore)
                .sum();
    }

    private boolean hasAce() {
        return getAceCount() > 0;
    }

    private int getAceCount() {
        return (int) cards.stream()
                .filter(Card::isAce)
                .count();
    }

    public boolean isBust() {
        return getScore() > BLACKJACK_SYMBOL_NUMBER;
    }

    public boolean isBlackjack() {
        return cards.size() == BLACKJACK_CARD_SIZE && getScore() == BLACKJACK_SYMBOL_NUMBER;
    }

    public MatchResult compareMatchResult(Hand opponentCardHand) {
        return MatchResult.get(this, opponentCardHand);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hand hand = (Hand) o;
        return Objects.equals(cards, hand.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cards);
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}
