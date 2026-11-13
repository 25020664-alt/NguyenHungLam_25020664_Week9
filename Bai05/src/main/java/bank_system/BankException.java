package main.java.bank_system;

/**
 * Ngoại lệ chung trong hệ thống ngân hàng.
 */
public class BankException extends Exception {

  /**
   * Khởi tạo một ngoại lệ ngân hàng với thông báo lỗi cụ thể.
   *
   * @param message thông báo chi tiết về lỗi
   */
  public BankException(String message) {
    super(message);
  }
}
