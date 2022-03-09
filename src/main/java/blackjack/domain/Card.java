package blackjack.domain;

import java.util.Objects;

public class Card {

    private final Denomination denomination;
    private final Suit suit;

    public Card(Denomination denomination, Suit suit) {
        validateNull(denomination, suit);
        this.denomination = denomination;
        this.suit = suit;
    }

    private void validateNull(Denomination denomination, Suit suit) {
        if (denomination == null || suit == null) {
            throw new NullPointerException("카드를 생성할때 숫자와 무늬중 null이 존재합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return denomination == card.denomination && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination, suit);
    }

    @Override
    public String toString() {
        return "Card{" +
                "denomination=" + denomination +
                ", suit=" + suit +
                '}';
    }
}
