package test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

public class Application {

    private Scanner scanner = new Scanner(System.in);
    private static List<Player> PLAYERS = new CopyOnWriteArrayList<>();
    private List<Integer> CARDS_TAKEN = new ArrayList<>();
    private int index;
    private Integer[] cardValuesList = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10,
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

    private List<Integer> PLAYING_CARDS = new CopyOnWriteArrayList<>();
    private Player dealer = null;

    {
        PLAYING_CARDS.addAll(Arrays.asList(cardValuesList));
    }


    void gameResult() {
        index = 0;
        PLAYERS.stream().forEach((Player res) -> {

            if (res.getPlayersName().equalsIgnoreCase("Dealer")) {
                int sum = res.getHand().stream().mapToInt(result -> result.getValue()).sum();
                res.setPlayerScore(sum);
                res.setPlayerTotalCards(res.getHand().size());
                if (sum >= 17 && sum <= 21) {
                    res.setPlayerStatus("WON!");
                } else {
                    res.setPlayerStatus("BUSTED!");
                }
                dealer = res;
                PLAYERS.set(index, res);
            }
            index++;
        });


        index = 0;
        PLAYERS.stream().forEach(res -> {
            if (!res.getPlayersName().equalsIgnoreCase("Dealer")) {
                int sum = res.getHand().stream().mapToInt(sumCard -> sumCard.getValue()).sum();
                res.setPlayerScore(sum);
                res.setPlayerTotalCards(res.getHand().size());
                if (sum > 21) {
                    res.setPlayerStatus("BUSTED!");
                } else if (sum > dealer.getPlayerScore() && sum <= 21) {
                    res.setPlayerStatus("WON!");
                } else if (dealer.getPlayerScore() > 21 && sum < 21) {
                    res.setPlayerStatus("WON!");
                }else{
                    res.setPlayerStatus("LOSE!");
                }
                PLAYERS.set(index, res);
            }
            index++;
        });


    }


    public static void main(String[] args) {

        System.out.println("Welcome to Blackjack!");
        System.out.println("");
        System.out.println("  BLACKJACK RULES: ");
        System.out.println(
                "	-Each player is dealt 2 cards. The dealer is dealt 2 cards with one face-up and one face-down.");
        System.out.println("	-Cards are equal to their value with face cards being 10 and an Ace being 1 or 11.");
        System.out.println("	-The players cards are added up for their total.");
        System.out.println(
                "	-Players “Hit” to gain another card from the deck. Players “Stay” to keep their current card total.");
        System.out.println("	-Dealer “Hits” until they equal or exceed 17.");
        System.out.println("	-The goal is to have a higher card total than the dealer without going over 21.");
        System.out.println("	-If the player total equals the dealer total, it is a “Push” and the hand ends.");
        System.out.println(
                "	-Players win their bet if they beat the dealer. Players win 1.5x their bet if they get “Blackjack” which is 21.");
        System.out.println("");
        System.out.println("");
        int round = 1;
        for (int i = 0; i < 90; i++, ++round) {
            PLAYERS.clear();
            Application application = new Application();


            // Init Players
            int players = application.initNumberOfPlayers();

            System.out.println("========================================================= Round: " + round
                    + " =========================================================");
            // init Players
            application.initPlayers(players);

            // Validate If Player still has money for next Game
            application.validateWallet();

            // PLACE BET
            application.placeBet();

            // shuffle and dis.
            application.suffleAndDistribute();

            // validate and Hit for Dealer
            application.validateIfDealerNeedToHit();


            System.out.println("========================================= Players Hand Before Hitting =========================================");
            for (Player player : PLAYERS) {
                System.out.println(player.getPlayersName() + " " + player.getHand());
            }
            System.out.println("========================================= Players Hand Before Hitting =========================================");
            System.out.println("");

            // do a hit, stay or fold
            application.playerOptions();

            application.gameResult();
            // find winner

            System.out.println("========================================= Players Hand After Hitting =========================================");
            for (Player player : PLAYERS) {
                System.out.println("Name: " + player.getPlayersName() + ", Status: " +
                        player.getPlayerStatus() + ", Wallet: "+ player.getWallet()  + ", Card SUM: " + player.getPlayerScore() + ", " + ", Hand: " + player.getHand());
            }
            System.out.println("========================================= Players Hand After Hitting =========================================");
        } // end of main Loop
    }

    // Get Number Of Players
    int initNumberOfPlayers() {
        int value = 0;
        System.out.println("Enter Number of players (1 - 5)\n");
        for (; ; ) {
            scanner.nextLine();
            try {
                System.out.print("value: ");
                value = scanner.nextInt();
                if (value >= 1 && value <= 5) {

                    break;
                } else {
                    System.out.println("Value '" + value + "' is not in range (1 - 5)");
                }
            } catch (Exception e) {
                System.out.println("Please Provide a NUMERIC VALUE (1 - 5)\n");
            }
        }
        return value;

    }

    // initPlayers
    void initPlayers(int players) {
        scanner.nextLine();
        System.out.println("Enter TO CONTINUE!");
        scanner.nextLine();

        for (int i = 0; i < players; i++) {
            System.out.println("Enter Player " + (1 + i) + " Name");
            String name = scanner.nextLine();
            PLAYERS.add(new Player(name));
            if (i + 1 == players) {
                PLAYERS.add(new Player("Dealer"));
            }
        }
    }

    void validateWallet() {
        for (Player player : PLAYERS) {
            if (player.getWallet() <= 0) {
                PLAYERS.remove(player);
            }
        }
    }

    void placeBet() {
        index = 0;
        for (Player player : PLAYERS) {

            if (!player.getPlayersName().equalsIgnoreCase("Dealer")) {

                inner:
                for (; ; ) {
                    try {
                        scanner.nextLine();
                        System.out.print(player.getPlayersName() + " Place Bet Amount: R");
                        double betAmount = scanner.nextDouble();
                        if (player.getWallet() <= betAmount) {
                            System.out.println(
                                    "Bet Amount 'R" + betAmount + "' is More that your Wallet R" + player.getWallet());
                        } else if (betAmount <= 0) {
                            System.out.println("Bet Amount Can not be Zero or Have a Negative value!");
                        } else {
                            player.setChips(betAmount);
                            player.setWallet(player.getWallet() - betAmount);
                            PLAYERS.set(index, player);
                            break inner;
                        }
                    } catch (Exception e) {
                        System.out.println("Please, Bet Amount should be NUMBERIC!");
                    }
                }

            }
            index++;
        }
        scanner.nextLine();
    }


    private int randomCard() {
        SplittableRandom random = new SplittableRandom();
        int ran = random.nextInt(1, 11);
        return ran;
    }

    int suffle() {
        int randomValue = 0;
        stopShuffle:
        for (; ; ) {// Loop making sure we don't duplicates Cards already taken
            randomValue = randomCard();
            int value = randomValue;
            long found = PLAYING_CARDS.stream().filter(ran -> ran.intValue() == value).count();
            if (found == 0) {
                continue;
            } else {
                break stopShuffle;
            }
        }
        return randomValue;
    }

    void suffleAndDistribute() {
        int index = 0;
        for (Player player : PLAYERS) {

            for (int i = 0; i < 2; i++) { // Loop to Make Sure 2 Cards Are Served
                int random = suffle();

                Card card = new Card(random);
                player.getHand().add(card);
                PLAYERS.set(index, player);
                CARDS_TAKEN.add(random);//Add to Taken Cards
                PLAYING_CARDS.remove(random);

            }
            index++;
        }

    }

    //Validate and Hit For Dealer
    void validateIfDealerNeedToHit() {
        for (Player player : PLAYERS) {
            if (player.getPlayersName().equalsIgnoreCase("Dealer")) {
                inner:
                for (; ; ) {
                    IntStream values = player.getHand().stream().mapToInt(Card::getValue);
                    int dealersHand = values.sum();
                    System.out.println("SUM Of Dealer Hand: " + dealersHand);
                    if (dealersHand <= 17) {
                        int random = suffle();
                        Card card = new Card(random);
                        PLAYING_CARDS.remove(random);
                        CARDS_TAKEN.add(random);

                        player.getHand().add(card);
                    } else {
                        break inner;
                    }

                }
            }
        }
    }

    void playersHand(Player player) {
        for (Card card : player.getHand()) {
            System.out.print(card + ", ");
        }
        System.out.println();

    }

    void playerOptions() {
        int index = 0;
        for (Player player : PLAYERS) {

            if (!player.getPlayersName().equalsIgnoreCase("Dealer")) {
                int option = 0;
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
                System.out.println("############################## " + player.getPlayersName() + " ##############################");
                inner:
                for (; ; ) {
                    try {

                        scanner.nextLine();
                        playersHand(player);
                        System.out.println("1. To Hit\n2. To Stay\n3. To Fold");
                        option = scanner.nextInt();
                        if (option >= 1 && option <= 3) {
                            if (option == 1) {
                                int random = suffle();
                                Card card = new Card(random);
                                player.getHand().add(card);
                                PLAYERS.set(index, player);
                                PLAYING_CARDS.remove(random);
                                CARDS_TAKEN.add(random);
                            } else if (option == 2) {
                                break;
                            } else if (option == 3) {
                                PLAYERS.remove(player);
                                break;
                            }
                        } else {
                            System.out.println("Please Provide only Numbers (1, 2, Or 3)");
                        }

                    } catch (Exception e) {
                        System.out.println("Please Provide only Numbers (1, 2, Or 3)");
                    }
                }
                System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");

            }
            ++index;
            System.out.println("\n");
        }
    }


}
