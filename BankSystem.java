import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class BankSystem {

    // --- BankAccount class ---
    static class BankAccount {
        private String acno;
        private String acname;
        private double balance;

        public BankAccount(String acno, String acname, double balance) {
            this.acno = acno;
            this.acname = acname;
            this.balance = balance;
        }

        public String getAcno() { return acno; }
        public String getAcname() { return acname; }
        public double getBalance() { return balance; }
        public void setBalance(double balance) { this.balance = balance; }
    }

    // --- BankUtils class ---
    static class BankUtils {
        private BankAccount fromAccount;
        private BankAccount toAccount;
        private double amount;
        private String transactionCode;

        public BankUtils(BankAccount fromAccount, BankAccount toAccount,
                         double amount, String transactionCode) {
            this.fromAccount = fromAccount;
            this.toAccount = toAccount;
            this.amount = amount;
            this.transactionCode = transactionCode;
        }

        public BankAccount getFromAccount() { return fromAccount; }
        public BankAccount getToAccount() { return toAccount; }
        public double getAmount() { return amount; }
        public String getTransactionCode() { return transactionCode; }
    }

    // --- Transaction class ---
    static class Transaction {
        private String transactionCode;
        private double amount;
        private String timestamp;

        public Transaction(String transactionCode, double amount, String timestamp) {
            this.transactionCode = transactionCode;
            this.amount = amount;
            this.timestamp = timestamp;
        }

        public String getTransactionCode() { return transactionCode; }
        public double getAmount() { return amount; }
        public String getTimestamp() { return timestamp; }
    }

    // --- Main Program ---
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<BankAccount> ac = new ArrayList<>();

        // Create 2 accounts
        for (int i = 0; i < 2; i++) {
            System.out.println("Account " + (i + 1) + ":");

            System.out.print("Account Number: ");
            String a = sc.nextLine();

            System.out.print("Account Holder Name: ");
            String b = sc.nextLine();

            System.out.print("Balance: ");
            double c = sc.nextDouble();
            sc.nextLine();

            ac.add(new BankAccount(a, b, c));
            System.out.println();
        }

        // Transfer details
        System.out.println("Transfer Details:");
        System.out.print("Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        System.out.print("Transaction Code: ");
        String tran = sc.nextLine();
        System.out.println();

        BankUtils bu = new BankUtils(ac.get(0), ac.get(1), amount, tran);

        System.out.println("Before Transfer:");
        for (int i = 0; i < ac.size(); i++) {
            System.out.println("Account " + (i + 1) + ": "
                    + ac.get(i).getAcname() + " - "
                    + ac.get(i).getAcno() + " - "
                    + ac.get(i).getBalance());
        }

        ArrayList<Double> ans = transferFunds(bu);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fTimestamp = sdf.format(timestamp);

        Transaction t = new Transaction(tran, amount, fTimestamp);

        System.out.println();

        if (ans != null) {
            System.out.println("After Transfer:");
            for (int i = 0; i < ans.size(); i++) {
                System.out.println("Account " + (i + 1) + ": "
                        + ac.get(i).getAcname() + " - "
                        + ac.get(i).getAcno() + " - "
                        + ans.get(i));
            }

            System.out.println();
            System.out.println("Transaction Details:");
            System.out.println("Transaction Code: " + t.getTransactionCode());
            System.out.println("Amount Transferred: " + t.getAmount());
            System.out.println("Timestamp: " + t.getTimestamp());
        } else {
            System.out.println("Insufficient Balance in Account 1");
            System.out.println("Transaction Code: " + t.getTransactionCode());
            System.out.println("Timestamp: " + t.getTimestamp());
        }

        sc.close();
    }

    // --- Transfer Logic ---
    public static ArrayList<Double> transferFunds(BankUtils b) {
        if (b.getFromAccount().getBalance() > b.getAmount()) {
            ArrayList<Double> amount = new ArrayList<>();
            amount.add(b.getFromAccount().getBalance() - b.getAmount());
            amount.add(b.getToAccount().getBalance() + b.getAmount());
            return amount;
        }
        return null;
    }
}
