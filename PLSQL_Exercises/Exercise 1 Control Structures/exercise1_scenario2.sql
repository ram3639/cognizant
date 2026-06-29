-- Exercise 1, Scenario 2: Promote Customers to VIP Status
-- Iterates through all customers and sets IsVIP to 'TRUE' for those with a balance over $10,000.

SET SERVEROUTPUT ON;

DECLARE
    CURSOR c_customers IS
        SELECT CustomerID, Name, Balance, IsVIP FROM Customers;
    v_promoted_count NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Processing VIP Promotions (Balance > $10,000) ---');

    FOR r_cust IN c_customers LOOP
        IF r_cust.Balance > 10000.00 THEN
            IF r_cust.IsVIP = 'TRUE' THEN
                DBMS_OUTPUT.PUT_LINE('Customer: ' || r_cust.Name || ' is already VIP.');
            ELSE
                UPDATE Customers
                SET IsVIP = 'TRUE'
                WHERE CustomerID = r_cust.CustomerID;
                
                v_promoted_count := v_promoted_count + 1;
                DBMS_OUTPUT.PUT_LINE('Promoted Customer: ' || r_cust.Name || ' (Balance: $' || TO_CHAR(r_cust.Balance, '99,999.99') || ') to VIP.');
            END IF;
        END IF;
    END LOOP;

    COMMIT;
    DBMS_OUTPUT.PUT_LINE('VIP promotions completed. Total promoted: ' || v_promoted_count);
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/

/*
EXPECTED OUTPUT:
--- Processing VIP Promotions (Balance > $10,000) ---
Promoted Customer: Alice Smith (Balance: $ 12,000.00) to VIP.
Promoted Customer: David Miller (Balance: $ 25,000.00) to VIP.
VIP promotions completed. Total promoted: 2

PL/SQL procedure successfully completed.
*/

