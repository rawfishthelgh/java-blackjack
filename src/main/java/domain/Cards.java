package domain;

import java.util.HashSet;
import java.util.List;

public class Cards {

    private final List<Card> cards;
    private boolean blackJack;

    public Cards(final List<Card> cards) {
        validateDuplicated(cards);
        this.cards = cards;
        this.blackJack = isTwentyOne();
    }

    public int sumOfCards() {
        int sum = 0;
        for (int i = 0; i < cards.size(); i += 2) {
            sum += cards.get(i).sum(cards.get(i + 1));
        }
        return sum;
    }

    public boolean addCard(final Card otherCard) {
        if (isNotContains(otherCard)) {
            cards.add(otherCard);
            return true;
        }
        return false;
    }

    private boolean isNotContains(final Card otherCard) {
        return !cards.contains(otherCard);
    }

    private void validateDuplicated(final List<Card> cards) {
        int size = new HashSet<>(cards).size();
        if (cards.size() != size) {
            throw new IllegalArgumentException();
        }
    }

    public boolean isTwentyOne() {
        return sumOfCards() == 21;
    }

    public boolean isBlackJack() {
        return blackJack;
    }
}
