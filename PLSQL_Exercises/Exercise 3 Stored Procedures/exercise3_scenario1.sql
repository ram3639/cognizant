-- Exercise 3, Scenario 1: Process Monthly Interest for Savings Accounts
-- Creates a stored procedure ProcessMonthlyInterest that calculates and updates 
-- the balance of all savings accounts by applying an interest rate of 1% to the current balance.

SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest AS
    v_rows_updated NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Starting Monthly Interest Processing for Savings Accounts ---');
    
    -- Bulk update for performance
    UPDATE Accounts
    SET Balance = Balance * 1.01
    WHERE AccountType = 'Savings';
    
    v_rows_updated := SQL%ROWCOUNT;
    
    DBMS_OUTPUT.PUT_LINE('Interest processing completed successfully.');
    DBMS_OUTPUT.PUT_LINE('Total Savings Accounts updated: ' || v_rows_updated);
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in ProcessMonthlyInterest: ' || SQLERRM);
        RAISE;
END ProcessMonthlyInterest;
/

/*
EXPECTED COMPILATION OUTPUT:
Procedure created.

EXPECTED EXECUTION OUTPUT (when calling ProcessMonthlyInterest):
--- Starting Monthly Interest Processing for Savings Accounts ---
Interest processing completed successfully.
Total Savings Accounts updated: 3
*/

