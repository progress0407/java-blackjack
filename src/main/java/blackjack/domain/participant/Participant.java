package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.card.Hand;
import java.util.List;
import java.util.Objects;

abstract class Participant {

    protected Name name;
    protected final Hand hand = new Hand();

    public Participant(Name name) {
        this.name = name;
    }

    public String getName() {
        return name.getValue();
    }

    public Hand getHand() {
        return hand;
    }

    public int getScore() {
        return hand.getScore();
    }

    public List<Card> getCards() {
        return hand.getCards();
    }

    public void receiveCard(Card card) {
        hand.addAll(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Participant that = (Participant) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Participant{" +
                "name=" + name +
                ", cardHand=" + hand +
                '}';
    }
}
