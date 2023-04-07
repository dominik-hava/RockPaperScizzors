import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main extends KnpMethods{
    private static final Random random = new Random();
    private static final List<String> moves = Arrays.asList("kamen", "nuzky", "papir", "tapir", "spock");

    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException, InterruptedException {
        System.out.println("\u001B[35m" + "Vítej ve hře Kámen, Nůžky, Papír, Tapír, Spock!" + "\u001B[0m");

        int rounds, winRounds;
        boolean continuePlaying;

        do {
            int playerWins = 0;
            int computerWins = 0;
            rounds = getRounds();
            winRounds = getWinRounds();
            for (int i=0; i<rounds; i++) {
                int tempComputerWins = 0;
                int tempPlayerWins = 0;
                while (true) {
                    String playerMove = getPlayerMove();
                    String computerMove = moves.get(random.nextInt(moves.size()));

                    System.out.println("\nTy jsi zadal " + "\u001B[34m" + playerMove + "\u001B[0m" + ".");
                    System.out.println("Počítač zadal " + "\u001B[34m" + computerMove + "\u001B[0m" + ".");

                    if (playerMove.equals(computerMove)) {
                        System.out.println("\u001B[33m" + "Remíza, hrajem ještě jednou." + "\u001B[0m");
                        continue;
                    }

                    if (isPlayerWinner(playerMove, computerMove)) {
                        System.out.println(getSentence(playerMove, computerMove));
                        PlaySounds.playSound("win.wav");
                        System.out.println("\u001B[32m" + "Gratuluji, vyhrál jsi." + "\u001B[0m");
                        tempPlayerWins++;
                    } else {
                        System.out.println(getSentence(computerMove, playerMove));
                        PlaySounds.playSound("fail.wav");
                        System.out.println("\u001B[31m" + "Bohužel, prohrál jsi, ale nevzdávej se!" + "\u001B[0m");
                        tempComputerWins++;
                    }
                    System.out.printf("\nPrůběžné skóre bodů: \nUživatel - %d\nPočítač - %d%n", tempPlayerWins, tempComputerWins);
                    if (tempPlayerWins == winRounds || tempComputerWins == winRounds) break;
                }
                Thread.sleep(1000);
                if (tempPlayerWins < tempComputerWins) computerWins++;
                else playerWins++;
                if(playerWins > computerWins) PlaySounds.playSound("drumWin.wav");
                else if (playerWins < computerWins) PlaySounds.playSound("drumLoose.wav");
                else {
                    System.out.println("\u001B[33m" + "\nProtože by to byla remíza dáme ještě jedno kolo." + "\u001B[0m");
                    --i;
                }
                System.out.printf("\nPrůběžné skóre výher: \nUživatel - %d\nPočítač - %d%n", playerWins, computerWins);
            }
            continuePlaying = askToContinue();
            System.out.println();
        } while (continuePlaying);
        System.out.println("Děkuji za vyzkoušení mé hry. :D");
        System.exit(0);
    }
}