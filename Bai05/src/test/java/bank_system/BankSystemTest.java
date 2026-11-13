package bank_system;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BankSystem core functionalities.
 */
class BankSystemTest {

  private Bank bank;

  @BeforeEach
  void setUp() {
        bank = new Bank();
    }

  @Test
  void testCheckingAccountDepositAndWithdraw() {
    CheckingAccount account = new CheckingAccount(1001L, 1000.0);
    account.deposit(500.0);
    assertEquals(1500.0, account.getBalance(), 0.01);

    account.withdraw(300.0);
    assertEquals(1200.0, account.getBalance(), 0.01);
    assertEquals(2, account.getTransactionList().size());
  }

  @Test
  void testSavingsAccountWithdrawLimits() {
    SavingsAccount account = new SavingsAccount(2001L, 5500.0);
    // Rút quá 1000.0 -> không thay đổi số dư
    account.withdraw(1500.0);
    assertEquals(5500.0, account.getBalance(), 0.01);

        // Rút khiến số dư dưới 5000.0 -> không thay đổi số dư
    account.withdraw(600.0);
    assertEquals(5500.0, account.getBalance(), 0.01);

       // Rút hợp lệ
    account.withdraw(400.0);
    assertEquals(5100.0, account.getBalance(), 0.01);
  }

  @Test
  void testReadCustomerListAndSortById() {
    String sampleData =
         "Nguyen Van B 987654321\n"
              + "10001 CHECKING 2000.0\n"
              + "Tran Van A 123456789\n"
              + "20001 SAVINGS 6000.0\n";

        InputStream inputStream = new ByteArrayInputStream(sampleData.getBytes(StandardCharsets.UTF_8));
        bank.readCustomerList(inputStream);

        assertEquals(2, bank.getCustomerList().size());
        String sortedById = bank.getCustomersInfoByIdOrder();
        assertTrue(sortedById.startsWith("Số CMND: 123456789"));
    }

    @Test
    void testBuildReportPathCrossPlatform() {
        ReportExporter exporter = new ReportExporter();
        String actualPath = exporter.buildReportPath("reports", "statement.txt");
        String expectedPath = new File("reports", "statement.txt").getPath();
        assertEquals(expectedPath, actualPath);
    }
    @Test
    void testLoanSimpleInterest() {
        LoanCalculator calculator = new LoanCalculator();
        assertEquals(800.0, calculator.calculateSimpleInterest(10000.0, 0.08, 1), 0.01);
        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateSimpleInterest(-100.0, 0.08, 1));
    }

    @Test
    void testLoanMonthlyPayment() {
        LoanCalculator calculator = new LoanCalculator();
        assertEquals(1000.0, calculator.calculateMonthlyPayment(12000.0, 0.0, 12), 0.01);
        assertEquals(1054.99, calculator.calculateMonthlyPayment(12000.0, 0.10, 12), 0.01);
        assertThrows(
                IllegalArgumentException.class,
                () -> calculator.calculateMonthlyPayment(0.0, 0.05, 12));
    }

    @Test
    void testLoanEligibility() {
        LoanCalculator calculator = new LoanCalculator();
        assertFalse(calculator.isEligibleForLoan(300.0, 750));
        assertTrue(calculator.isEligibleForLoan(1000.0, 720));
        assertTrue(calculator.isEligibleForLoan(2500.0, 650));
        assertFalse(calculator.isEligibleForLoan(1000.0, 600));
    }
}