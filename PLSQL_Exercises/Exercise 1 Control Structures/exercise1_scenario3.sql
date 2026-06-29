-- Exercise 1, Scenario 3: Loan Due Reminders
-- Fetches all loans due in the next 30 days and prints a reminder message for each customer.

SET SERVEROUTPUT ON;

DECLARE
    CURSOR c_due_loans IS
        SELECT l.LoanID, c.Name, l.LoanAmount, l.DueDate
        FROM Loans l
        JOIN Customers c ON l.CustomerID = c.CustomerID
        WHERE l.DueDate >= TRUNC(SYSDATE) 
          AND l.DueDate <= TRUNC(SYSDATE) + 30;
          
    v_count NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Processing Loan Due Reminders (Due within next 30 days) ---');

    FOR r_loan IN c_due_loans LOOP
        v_count := v_count + 1;
        DBMS_OUTPUT.PUT_LINE('REMINDER: Dear ' || r_loan.Name || ', your Loan (ID: ' || r_loan.LoanID || 
                             ') of amount $' || TO_CHAR(r_loan.LoanAmount, '99,999.99') || 
                             ' is due on ' || TO_CHAR(r_loan.DueDate, 'YYYY-MM-DD') || '. Please ensure timely payment.');
    END LOOP;

    IF v_count = 0 THEN
        DBMS_OUTPUT.PUT_LINE('No loans are due within the next 30 days.');
    ELSE
        DBMS_OUTPUT.PUT_LINE('Total reminders generated: ' || v_count);
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/

/*
EXPECTED OUTPUT:
--- Processing Loan Due Reminders (Due within next 30 days) ---
REMINDER: Dear Alice Smith, your Loan (ID: 101) of amount $  5,000.00 is due on [SYSDATE + 20]. Please ensure timely payment.
REMINDER: Dear Charlie Brown, your Loan (ID: 103) of amount $  2,000.00 is due on [SYSDATE + 15]. Please ensure timely payment.
Total reminders generated: 2

PL/SQL procedure successfully completed.
*/

