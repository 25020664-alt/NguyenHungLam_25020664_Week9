package main.java.bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Customer đại diện cho một khách hàng.
 */
public class Customer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /**
   * Constructor không tham số (phục vụ kiểm thử).
   */
  public Customer() {
    this(0L, "");
  }

  /**
   * Khởi tạo một khách hàng mới với số CMND/CCCD và họ tên.
   *
   * @param idNumber số CMND/CCCD
   * @param fullName họ và tên khách hàng
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<>();
  }

  public long getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Cập nhật danh sách tài khoản cho khách hàng.
   *
   * @param accountList danh sách tài khoản mới
   */
  public void setAccountList(List<Account> accountList) {
    if (accountList == null) {
      this.accountList = new ArrayList<>();
    } else {
      this.accountList = accountList;
    }
  }

  /**
   * Thêm tài khoản cho khách hàng.
   *
   * @param account tài khoản cần thêm
   */
  public void addAccount(Account account) {
    if (account == null) {
      LOGGER.warn("Khong the them tai khoan null cho khach hang ID: {}", idNumber);
        return;
    }
    if (!accountList.contains(account)) {
      accountList.add(account);
      LOGGER.debug("Da them tai khoan {} cho khach hang ID: {}",
          account.getAccountNumber(), idNumber);
    }
  }

  /**
   * Xóa tài khoản khỏi khách hàng.
   *
   * @param account tài khoản cần xóa
   */
  public void removeAccount(Account account) {
    if (account == null) {
      LOGGER.warn("Khong the xoa tai khoan null khoi khach hang ID: {}", idNumber);
      return;
    }
    if (accountList.remove(account)) {
      LOGGER.info("Da xoa tai khoan {} khoi khach hang ID: {}",
          account.getAccountNumber(), idNumber);
    }
  }

  /**
   * Lấy thông tin khách hàng dạng văn bản.
   *
   * @return chuỗi thông tin khách hàng
   */
  public String getCustomerInfo() {
    return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
  }
}