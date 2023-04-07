import javax.swing.*;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class KnpMethods {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> moves = Arrays.asList("kamen", "nuzky", "papir", "tapir", "spock");

    protected static int getRounds() {
        int rounds = 0;
        do {
            System.out.print("Zadej kolik chcete hrát kol: ");
            try {
                rounds = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m" + "Musíte zadat celé číslo!" + "\u001B[0m");
                scanner.nextLine();
            }
        } while (rounds <= 0);
        return rounds;
    }
    protected static int getWinRounds() {
        int rounds = 0;
        do {
            System.out.print("Zadej na kolik vítězných chcete hrát: ");
            try {
                rounds = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m" + "Musíte zadat celé číslo!" + "\u001B[0m");
                scanner.nextLine();
            }
        } while (rounds <= 0);
        return rounds;
    }

    protected static String getPlayerMove() {
        String playerMove;
        while (true) {
            System.out.printf("\nZadej jeden z tahů (%s): ", String.join(", ", moves));
            playerMove = stripAccents(scanner.next());
            if (!moves.contains(playerMove)) {
                System.out.println();
                System.out.println("\u001B[31m" + "Zadejte platný tah!" + "\u001B[0m");
                continue;
            }
            break;
        }
        return playerMove;
    }
    protected static boolean askToContinue() {
        JOptionPane pane = new JOptionPane( "Chceš hrát znovu?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
        JDialog dialog = pane.createDialog(null,"pokračovat");
        dialog.requestFocus();
        dialog.setVisible(true);

        Object value = pane.getValue();
        if (value instanceof Integer) {
            return ((Integer) value) == JOptionPane.YES_OPTION;
        } else {
            return false;
        }
    }
    protected static String getSentence(String input1, String input2) {
        String sentence = "";
        switch (input1) {
            case "kamen" -> {
                switch (input2) {
                    case "nuzky" -> sentence = "Kámen tupí nůžky.";
                    case "tapir" -> sentence = "Kámen rozdrtí tapíra.";
                }
            }
            case "nuzky" -> {
                switch (input2) {
                    case "papir" -> sentence = "Nůžky stříhají papír.";
                    case "tapir" -> sentence = "Nůžky utnou hlavu tapírovi.";
                }
            }
            case "papir" -> {
                switch (input2) {
                    case "kamen" -> sentence = "Papír balí kámen.";
                    case "spock" -> sentence = "Papír usvědčí spocka.";
                }
            }
            case "tapir" -> {
                switch (input2) {
                    case "papir" -> sentence = "Tapír sní papír.";
                    case "spock" -> sentence = "Tapír otráví spocka.";
                }
            }
            case "spock" -> sentence = switch (input2) {
                case "nuzky" -> "Spock zničí nůžky.";
                case "kamen" -> "Spock vypaří kámen.";
                default -> sentence;
            };
            default -> throw new IllegalStateException("Unexpected value: " + input1);
        }
        return sentence;
    }

    protected static boolean isPlayerWinner(String player, String computer) {
        boolean win;
        switch (player) {
            case "kamen" -> win = computer.equals("nuzky") || computer.equals("tapir");
            case "nuzky" -> win = computer.equals("tapir") || computer.equals("papir");
            case "papir" -> win = computer.equals("kamen") || computer.equals("spock");
            case "tapir" -> win = computer.equals("papir") || computer.equals("spock");
            case "spock" -> win = computer.equals("nuzky") || computer.equals("kamen");
            default -> throw new InputMismatchException();
        }
        return win;
    }

    protected static String stripAccents(String input) {
        String nfdNormalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("").toLowerCase();
    }
}