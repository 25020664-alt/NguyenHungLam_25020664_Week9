package bank_system;

/**
 * Utility class for calculating bank loan installments and interest.
 */
public class LoanCalculator {

    /**
     * Calculates simple interest for a loan.
     *
     * @param principal loan amount
     * @param annualRate annual interest rate (e.g., 0.08 for 8%)
     * @param years duration in years
     * @return total interest amount
     */
    public double calculateSimpleInterest(double principal, double annualRate, int years) {
        if (principal <= 0 || annualRate < 0 || years <= 0) {
            throw new IllegalArgumentException("Invalid loan parameters");
        }
        return principal * annualRate * years;
    }

    /**
     * Calculates monthly payment for a loan.
     *
     * @param principal loan amount
     * @param annualRate annual interest rate
     * @param months duration in months
     * @return monthly installment amount
     */
    public double calculateMonthlyPayment(double principal, double annualRate, int months) {
        if (principal <= 0 || months <= 0) {
            throw new IllegalArgumentException("Principal and months must be positive");
        }
        if (annualRate == 0) {
            return principal / months;
        }
        double monthlyRate = annualRate / 12.0;
        return (principal * monthlyRate) / (1 - Math.pow(1 + monthlyRate, -months));
    }

    /**
     * Checks whether a customer is eligible for a loan based on income and credit score.
     *
     * @param monthlyIncome customer's monthly income
     * @param creditScore customer's credit score
     * @return true if eligible, false otherwise
     */
    public boolean isEligibleForLoan(double monthlyIncome, int creditScore) {
        if (monthlyIncome < 500.0) {
            return false;
        }
        if (creditScore >= 700) {
            return true;
        }
        return monthlyIncome >= 2000.0 && creditScore >= 620;
    }
}