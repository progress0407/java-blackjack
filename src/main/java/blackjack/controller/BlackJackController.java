package blackjack.controller;

import blackjack.domain.ScoreBoard;
import blackjack.domain.card.Deck;
import blackjack.domain.card.strategy.RandomCardsGenerateStrategy;
import blackjack.domain.dto.ScoreBoardResponse;
import blackjack.domain.participant.Dealer;
import blackjack.domain.participant.Player;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.ArrayList;
import java.util.List;

public class BlackJackController {

    private static final String Y = "Y";
    private static final String DEALER_MATCH_RESULT_MESSAGE = "딜러: ";

    private Deck deck = new Deck(new RandomCardsGenerateStrategy());
    private Dealer dealer = new Dealer();
    private List<Player> players = createPlayers();

    public void run() {
        initiateParticipantsHand();

        takeMoreCardPlayerTurnForAllPlayers();
        takeOneMoreCardDuringDealerTurn();
        OutputView.printParticipantScore(dealer, players);

//        decideGameScore();
        ScoreBoard scoreBoard = new ScoreBoard(dealer, players);
        ScoreBoardResponse scoreBoardResponse = ScoreBoardResponse.from(scoreBoard);
//        OutputView.printBlackjackGameResult(dealer, players);
        OutputView.printBlackjackGameResult2(scoreBoardResponse);
    }

    private List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        String participantsNames = InputView.inputParticipantsNames();
        for (String participantName : participantsNames.split(",")) {
            players.add(new Player(participantName));
        }
        return players;
    }

    private void initiateParticipantsHand() {
        dealer.receiveCard(deck.draw());
        dealer.receiveCard(deck.draw());
        for (Player player : players) {
            player.receiveCard(deck.draw());
            player.receiveCard(deck.draw());
        }
        OutputView.printGiveCardsToParticipants(dealer, players);
    }

    private void takeMoreCardPlayerTurnForAllPlayers() {
        for (Player player : players) {
            takeMoreCardPlayerTurnForPlayer(player);
        }
    }

    private void takeMoreCardPlayerTurnForPlayer(Player player) {
        while (!player.isBust() && InputView.inputOneMoreCard(player).equalsIgnoreCase(Y)) {
            player.receiveCard(deck.draw());
            OutputView.showPlayerHand(player);
        }
    }

    private void takeOneMoreCardDuringDealerTurn() {
        while (dealer.shouldReceive()) {
            OutputView.printDealerOneMoreCard();
            dealer.receiveCard(deck.draw());
        }
    }

    void decideGameScore() {
        for (Player player : players) {
            player.decideMatchResult(dealer);
            dealer.decideMatchResult(player);
        }
    }
}
