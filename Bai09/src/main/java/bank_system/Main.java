package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    logger.info("Starting Bank System application...");
    Customer customer = new Customer(1001L, "Nguyen Hung Lam");
    CheckingAccount account = new CheckingAccount(25020664L, 5000.0);
    customer.addAccount(account);

    logger.info("Initialized customer: {} (ID: {})", customer.getFullName(), customer.getIdNumber());
    logger.info("Initial balance for account {}: {}", account.getAccountNumber(), account.getBalance());
  }
}