package bank_system;

public class Main {
  public static void main(String[] args) {
    Customer customer = new Customer(1001L, "Nguyen Hung Lam");
    CheckingAccount account = new CheckingAccount(25020664L, 5000.0);
    customer.addAccount(account);

    System.out.println("=== BANK SYSTEM EXECUTABLE JAR RUNNING ===");
    System.out.println("Customer: " + customer.getFullName() + " | ID: " + customer.getIdNumber());
    System.out.println("Account Balance: $" + account.getBalance());
  }
}
