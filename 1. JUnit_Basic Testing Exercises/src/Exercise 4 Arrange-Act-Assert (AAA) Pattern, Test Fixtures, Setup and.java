import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BankAccountTest {

    private BankAccount account;

    @Before
    public void setUp() {
        // Arrange phase (initializing the test fixture)
        account = new BankAccount(100.0);
        System.out.println("[SetUp] Created BankAccount with initial balance: $100.0");
    }

    @After
    public void tearDown() {
        // Teardown phase (cleaning up the test fixture)
        account = null;
        System.out.println("[TearDown] Cleaned up BankAccount instance");
    }

    @Test
    public void testDeposit() {
        System.out.println("Running testDeposit...");
        // Arrange
        double depositAmount = 50.0;

        // Act
        account.deposit(depositAmount);

        // Assert
        assertEquals("Balance should increase by deposit amount", 150.0, account.getBalance(), 0.001);
    }

    @Test
    public void testWithdraw() {
        System.out.println("Running testWithdraw...");
        // Arrange
        double withdrawAmount = 30.0;

        // Act
        account.withdraw(withdrawAmount);

        // Assert
        assertEquals("Balance should decrease by withdrawal amount", 70.0, account.getBalance(), 0.001);
    }
}
