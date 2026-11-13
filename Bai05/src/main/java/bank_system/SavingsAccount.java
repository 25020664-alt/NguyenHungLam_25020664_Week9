package main.java.bank_system;

import bank_system.InsufficientFundsException;
import bank_system.InvalidFundingAmountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm thực thi các quy định về hạn mức rút tiền và số dư tối thiểu.
 */
public class SavingsAccount extends Account {

  private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccount.class);
  private static final double MAX_WITHDRAW = 1000.0;
  private static final double MIN_BALANCE = 5000.0;

  /**
   * Khởi tạo một tài khoản tiết kiệm mới.
   *
   * @param accountNumber số tài khoản
   * @param balance số dư ban đầu
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  @Override
  public void deposit(double amount) {
    LOGGER.debug("Dang xu ly giao dich nap tien vao tai khoan tiet kiem: {}", getAccountNumber());
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction transaction =
          new Transaction(
              Transaction.TYPE_DEPOSIT_SAVINGS,
              amount,
              initialBalance,
              finalBalance);
      addTransaction(transaction);
      LOGGER.info(
          "Nap tien vao tai khoan tiet kiem {} thanh cong: +{}",
              getAccountNumber(),
              amount);
    } catch (BankException e) {
      LOGGER.warn("Loi nap tien tai khoan tiet kiem {}: {}", getAccountNumber(), e.getMessage());
    }
  }

  @Override
  public void withdraw(double amount) {
    LOGGER.debug("Dang xu ly giao dich rut tien tu tai khoan tiet kiem: {}", getAccountNumber());
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAW) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();
      Transaction transaction =
          new Transaction(
              Transaction.TYPE_WITHDRAW_SAVINGS,
              amount,
              initialBalance,
              finalBalance);
      addTransaction(transaction);
      LOGGER.info(
          "Rut {} tu tai khoan tiet kiem {} thanh cong. So du con: {}",
              amount,
              getAccountNumber(),
              finalBalance);
    } catch (BankException e) {
      LOGGER.warn("Loi rut tien tai khoan tiet kiem {}: {}", getAccountNumber(), e.getMessage());
    }
  }
}