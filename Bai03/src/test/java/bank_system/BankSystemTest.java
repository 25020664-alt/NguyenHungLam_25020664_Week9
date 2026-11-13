package bank_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(9999.0, account.getBalance(), 0.01);

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
}