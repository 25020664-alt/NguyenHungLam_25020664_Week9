package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản vãng lai.
 */
public class CheckingAccount extends Account {

  private static final Logger LOGGER = LoggerFactory.getLogger(CheckingAccount.class);

  /**
   * Khởi tạo một tài khoản vãng lai mới.
   *
   * @param accountNumber số tài khoản
   * @param balance số dư ban đầu
   */
  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction transaction =
          new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);
      LOGGER.info("Nap tien thanh cong vao tai khoan vang lai: {}", getAccountNumber());
    } catch (BankException e) {
      LOGGER.warn("Loi nap tien tai khoan vang lai {}: {}", getAccountNumber(), e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      double finalBalance = getBalance();
      Transaction transaction =
          new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(transaction);
      LOGGER.info("Rut tien thanh cong tu tai khoan vang lai: {}", getAccountNumber());
    } catch (BankException e) {
      LOGGER.warn("Loi rut tien tai khoan vang lai {}: {}", getAccountNumber(), e.getMessage());
    }
  }
}