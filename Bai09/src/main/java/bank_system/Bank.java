package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Manages the customer list and general banking operations.
 */
public class Bank {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);

    private static final String ID_NUMBER_REGEX = "\\d{9}";
    private static final String WHITESPACE_REGEX = "\\s+";

    private List<Customer> customerList;

    /**
     * Creates a bank with an empty customer list.
     */
    public Bank() {
        this.customerList = new ArrayList<>();
    }

    /**
     * Returns the customer list.
     *
     * @return the list of customers
     */
    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Updates the customer list.
     *
     * @param customerList the new customer list
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Reads and processes customer and account information from an input stream.
     *
     * @param inputStream the input stream containing customer data
     */
    public void readCustomerList(InputStream inputStream) {
        if (inputStream == null) {
            LOGGER.warn("Input stream bi null. Khong the doc du lieu khach hang.");
            return;
        }

        LOGGER.debug("Bat dau doc du lieu danh sach khach hang.");

        try (BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            String line;
            Customer currentCustomer = null;

            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty()) {
                    continue;
                }

                currentCustomer = processLine(trimmedLine, currentCustomer);
            }

            LOGGER.info(
                    "Hoan tat doc du lieu khach hang. Tong so khach hang: {}",
                    customerList.size());

        } catch (IOException e) {
            LOGGER.error("Loi khi doc du lieu khach hang.", e);
        }
    }

    /**
     * Processes a line to create a customer or add an account.
     *
     * @param line the line of input data
     * @param currentCustomer the currently processed customer
     * @return the current or newly created customer
     */
    private Customer processLine(String line, Customer currentCustomer) {
        int lastSpaceIndex = line.lastIndexOf(' ');

        if (lastSpaceIndex <= 0) {
            LOGGER.warn("Bo qua dong du lieu khong hop le.");
            return currentCustomer;
        }

        String lastToken = line.substring(lastSpaceIndex + 1).trim();

        if (lastToken.matches(ID_NUMBER_REGEX)) {
            String fullName = line.substring(0, lastSpaceIndex).trim();
            long idNumber = Long.parseLong(lastToken);

            Customer newCustomer = new Customer(idNumber, fullName);
            customerList.add(newCustomer);

            LOGGER.debug(
                    "Da them khach hang: {} (ID: {})",
                    fullName,
                    idNumber);

            return newCustomer;
        }

        if (currentCustomer != null) {
            parseAndAddAccount(line, currentCustomer);
        } else {
            LOGGER.warn("Tim thay du lieu tai khoan nhung chua co khach hang.");
        }

        return currentCustomer;
    }

    /**
     * Parses an account record and adds the account to a customer.
     *
     * @param line the account data line
     * @param customer the customer receiving the account
     */
    private void parseAndAddAccount(String line, Customer customer) {
        String[] parts = line.split(WHITESPACE_REGEX);

        if (parts.length < 3) {
            LOGGER.warn("Dong du lieu tai khoan khong hop le: {}", line);
            return;
        }

        try {
            long accountNumber = Long.parseLong(parts[0]);
            String accountType = parts[1];
            double balance = Double.parseDouble(parts[2]);

            if (Account.CHECKING_TYPE.equals(accountType)) {
                customer.addAccount(new CheckingAccount(accountNumber, balance));

                LOGGER.debug(
                        "Da them tai khoan thanh toan {} cho khach hang {}",
                        accountNumber,
                        customer.getFullName());

            } else if (Account.SAVINGS_TYPE.equals(accountType)) {
                customer.addAccount(new SavingsAccount(accountNumber, balance));

                LOGGER.debug(
                        "Da them tai khoan tiet kiem {} cho khach hang {}",
                        accountNumber,
                        customer.getFullName());

            } else {
                LOGGER.warn("Loai tai khoan khong duoc ho tro: {}", accountType);
            }

        } catch (NumberFormatException e) {
            LOGGER.warn("Gia tri so khong hop le trong du lieu tai khoan: {}", line, e);
        }
    }

    /**
     * Returns customer information sorted by ID number in ascending order.
     *
     * @return customer information sorted by ID number
     */
    public String getCustomersInfoByIdOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);

        sortedList.sort(
                Comparator.comparingLong(Customer::getIdNumber));

        return formatCustomerList(sortedList);
    }

    /**
     * Returns customer information sorted by full name in ascending order.
     *
     * <p>Customers with the same name are sorted by ID number.</p>
     *
     * @return customer information sorted by name and ID number
     */
    public String getCustomersInfoByNameOrder() {
        List<Customer> sortedList = new ArrayList<>(customerList);

        sortedList.sort(
                Comparator.comparing(Customer::getFullName)
                        .thenComparingLong(Customer::getIdNumber));

        return formatCustomerList(sortedList);
    }

    /**
     * Formats a customer list into a string separated by newline characters.
     *
     * @param customers the list of customers to format
     * @return the formatted customer information
     */
    private String formatCustomerList(List<Customer> customers) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < customers.size(); i++) {
            builder.append(customers.get(i).getCustomerInfo());

            if (i < customers.size() - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    public void addCustomer(Customer customer) {
    }
}