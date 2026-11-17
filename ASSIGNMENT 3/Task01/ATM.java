import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class InsufficientFundsException extends RuntimeException {
    InsufficientFundsException(String message) { super(message); }
}
class InvalidAmountException extends IllegalArgumentException {
    InvalidAmountException(String message) { super(message); }
}

public class ATM {
    private double balance;
    ATM(double openingBalance) { balance = openingBalance; }

    void withdraw(int accountNumber, double amount, Path cardFile) throws IOException {
        try {
            if (accountNumber <= 0) throw new InvalidAmountException("Invalid account number");
            if (amount <= 0) throw new InvalidAmountException("Withdrawal must be positive");
            if (!Files.exists(cardFile)) throw new IOException("Card-reader file is missing: " + cardFile);
            if (amount > balance) throw new InsufficientFundsException("Insufficient balance");
            balance -= amount;
            System.out.printf("Cash dispensed: %.2f%nRemaining balance: %.2f%n", amount, balance);
        } catch (InvalidAmountException | InsufficientFundsException e) {
            System.out.println("Transaction rejected: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O failure: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("ATM transaction finished (finally).");
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM(1000);
        Path card = Path.of("Task01/card-reader.txt");
        try {
            Files.createDirectories(card.getParent());
            if (!Files.exists(card)) Files.writeString(card, "CARD-READER-READY");
            if (args.length == 2) {
                int account = Integer.parseInt(args[0]);
                double amount = Double.parseDouble(args[1]);
                atm.withdraw(account, amount, card);
            } else {
                try (Scanner in = new Scanner(System.in)) {
                    System.out.print("Account number: ");
                    int account = in.nextInt();
                    System.out.print("Withdrawal amount: ");
                    double amount = in.nextDouble();
                    atm.withdraw(account, amount, card);
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Input failure: enter numeric values only.");
                    System.out.println("ATM transaction finished (input finally equivalent).");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Input failure: enter numeric values only.");
        } catch (IOException e) {
            System.out.println("Unable to access card reader: " + e.getMessage());
        }
    }
}
