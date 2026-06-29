-- Exercise 3, Scenario 3: Transfer Funds Stored Procedure
-- Transfers a specified amount from one account to another, checking that 
-- the source account has sufficient balance before making the transfer.

SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE TransferFunds (
    p_source_acc IN NUMBER,
    p_dest_acc IN NUMBER,
    p_amount IN NUMBER
) AS
    v_source_bal NUMBER;
    v_dest_exists NUMBER := 0;
    insufficient_funds EXCEPTION;
    invalid_amount EXCEPTION;
    source_not_found EXCEPTION;
    dest_not_found EXCEPTION;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Processing Fund Transfer ---');
    DBMS_OUTPUT.PUT_LINE('Request: Transfer $' || TO_CHAR(p_amount, '99,999.99') || 
                         ' from Account ID ' || p_source_acc || ' to Account ID ' || p_dest_acc);
    
    -- 1. Validate transfer amount is positive
    IF p_amount <= 0 THEN
        RAISE invalid_amount;
    END IF;

    -- 2. Validate source account existence and fetch balance
    BEGIN
        SELECT Balance INTO v_source_bal
        FROM Accounts
        WHERE AccountID = p_source_acc;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE source_not_found;
    END;

    -- 3. Validate destination account existence
    SELECT COUNT(*) INTO v_dest_exists
    FROM Accounts
    WHERE AccountID = p_dest_acc;
    
    IF v_dest_exists = 0 THEN
        RAISE dest_not_found;
    END IF;

    -- 4. Check for sufficient funds
    IF v_source_bal < p_amount THEN
        RAISE insufficient_funds;
    END IF;

    -- 5. Perform the transfer
    -- Debit source account
    UPDATE Accounts
    SET Balance = Balance - p_amount
    WHERE AccountID = p_source_acc;

    -- Credit destination account
    UPDATE Accounts
    SET Balance = Balance + p_amount
    WHERE AccountID = p_dest_acc;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Transfer Completed Successfully!');
    DBMS_OUTPUT.PUT_LINE('New Source Account (' || p_source_acc || ') Balance: $' || TO_CHAR(v_source_bal - p_amount, '99,999.99'));
    
EXCEPTION
    WHEN invalid_amount THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer Error: Transfer amount must be positive.');
        RAISE_APPLICATION_ERROR(-20003, 'Transfer amount must be positive.');
        
    WHEN source_not_found THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer Error: Source account ID ' || p_source_acc || ' does not exist.');
        RAISE_APPLICATION_ERROR(-20004, 'Source account does not exist.');
        
    WHEN dest_not_found THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer Error: Destination account ID ' || p_dest_acc || ' does not exist.');
        RAISE_APPLICATION_ERROR(-20005, 'Destination account does not exist.');
        
    WHEN insufficient_funds THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer Error: Insufficient funds in source account ID ' || p_source_acc || 
                             '. Available: $' || TO_CHAR(v_source_bal, '99,999.99') || 
                             ', Requested: $' || TO_CHAR(p_amount, '99,999.99'));
        RAISE_APPLICATION_ERROR(-20006, 'Insufficient funds in source account.');
        
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Transfer Error (Unexpected): ' || SQLERRM);
        RAISE;
END TransferFunds;
/

/*
EXPECTED COMPILATION OUTPUT:
Procedure created.

EXPECTED EXECUTION OUTPUTS:

1. Successful Transfer (TransferFunds(201, 203, 500.00)):
--- Processing Fund Transfer ---
Request: Transfer $    500.00 from Account ID 201 to Account ID 203
Transfer Completed Successfully!
New Source Account (201) Balance: $11,620.00

2. Failed Transfer - Insufficient Funds (TransferFunds(201, 203, 100000.00)):
--- Processing Fund Transfer ---
Request: Transfer $100,000.00 from Account ID 201 to Account ID 203
Transfer Error: Insufficient funds in source account ID 201. Available: $11,620.00, Requested: $100,000.00
ORA-20006: Insufficient funds in source account.

3. Failed Transfer - Negative Amount (TransferFunds(201, 203, -50.00)):
--- Processing Fund Transfer ---
Request: Transfer $    -50.00 from Account ID 201 to Account ID 203
Transfer Error: Transfer amount must be positive.
ORA-20003: Transfer amount must be positive.

4. Failed Transfer - Non-existent Account (TransferFunds(201, 999, 100.00)):
--- Processing Fund Transfer ---
Request: Transfer $    100.00 from Account ID 201 to Account ID 999
Transfer Error: Destination account ID 999 does not exist.
ORA-20005: Destination account does not exist.
*/

