package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Đại diện cho một giao dịch ngân hàng.
 */
public class Transaction {

  private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);
  private static final String CURRENCY_FORMAT = "%.2f";

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới với đầy đủ thông tin biến động số dư.
   *
   * @param type loại giao dịch
   * @param amount số tiền giao dịch
   * @param initialBalance số dư trước giao dịch
   * @param finalBalance số dư sau giao dịch
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public double getInitialBalance() {
    return initialBalance;
  }

  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  public double getFinalBalance() {
    return finalBalance;
  }

  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Chuyển đổi mã loại giao dịch sang mô tả tiếng Việt tương ứng.
   *
   * @param transactionType mã loại giao dịch
   * @return chuỗi mô tả loại giao dịch
   */
  public static String getTransactionTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Tạo chuỗi tóm tắt thông tin chi tiết của giao dịch.
   *
   * @return chuỗi tóm tắt giao dịch
   */
  public String getTransactionSummary() {
    LOGGER.debug("Bat dau tao tom tat giao dich cho loai: {}", type);
    String formattedInitial = String.format(Locale.US, CURRENCY_FORMAT, initialBalance);
    String formattedAmount = String.format(Locale.US, CURRENCY_FORMAT, amount);
    String formattedFinal = String.format(Locale.US, CURRENCY_FORMAT, finalBalance);

    return "- Kiểu giao dịch: "
        + getTransactionTypeString(type)
        + ". Số dư ban đầu: $"
        + formattedInitial
        + ". Số tiền: $"
        + formattedAmount
        + ". Số dư cuối: $"
        + formattedFinal
        + ".";
  }
}