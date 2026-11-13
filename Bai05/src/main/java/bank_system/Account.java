package main.java.bank_system;

import bank_system.InsufficientFundsException;
import bank_system.InvalidFundingAmountException;
import bank_system.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;


/**
 * Lớp này đại diện cho tài khoản nhưng viết Javadoc rất sơ sài và sai format
 */
public abstract class Account {

  private static final Logger LOGGER =
          LoggerFactory.getLogger(Account.class);

  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  private long accountNumber;
  private double balance;
  protected List<Transaction> transactionList;

  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactionList = new ArrayList<>();
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public double getBalance() {
    return balance;
  }

  protected void setBalance(double balance) {
    this.balance = balance;
  }

  public List<Transaction> getTransactionList() {
    return transactionList;
  }

   /**
   * Cập nhật danh sách giao dịch cho tài khoản.
   *
   * @param transactionList danh sách giao dịch mới
   */
  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.transactionList = new ArrayList<Transaction>();
    } else {
      this.transactionList = transactionList;
    }
  }

  /**
   * Thực hiện nạp tiền vào tài khoản.
   *
   * @param amount số tiền nạp
   */
  public abstract void deposit(double amount);

  /**
   * Thực hiện rút tiền khỏi tài khoản.
   *
   * @param amount số tiền rút
   */
  public abstract void withdraw(double amount);

  protected void doDepositing(double amount) throws InvalidFundingAmountException {
            // Vi phạm: Whitespace quanh toán tử (amount<=0)
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  protected void doWithdrawing(double amount) throws  InvalidFundingAmountException, InsufficientFundsException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Thêm một giao dịch vào nhật ký lịch sử của tài khoản.
   *
   * @param transaction giao dịch cần ghi nhận
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactionList.add(transaction);
    }
  }

  public String getTransactionHistory() {
    StringBuilder history = new StringBuilder();

    history.append("Lịch sử giao dịch của tài khoản ");
    history.append(accountNumber);
    history.append(":\n");

    for (int i = 0; i < transactionList.size(); i++) {
      history.append(transactionList.get(i).getTransactionSummary());

      if (i < transactionList.size() - 1)  {
        history.append("\n");
      }
    }
            // Vi phạm: In log trực tiếp ra console để debug (Thay vì dùng Logger)
    LOGGER.debug("Đã lấy lịch sử cho tài khoản: {}", accountNumber);
    return history.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Account)) {
      return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

@Override
  public int hashCode() {
    return (int) (accountNumber ^ (accountNumber >>> 32));
  }
}