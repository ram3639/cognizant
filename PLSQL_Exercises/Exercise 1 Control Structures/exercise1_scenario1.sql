-- Exercise 1, Scenario 1: Interest Rate Discount for Senior Citizens
-- Apply a 1% discount to the loan interest rates of customers who are above 60 years old.

SET SERVEROUTPUT ON;

DECLARE
    CURSOR c_customers IS
        SELECT CustomerID, Name, DOB FROM Customers;
    v_age NUMBER;
    v_updated_count NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Processing Loan Interest Discounts for Seniors (> 60 years old) ---');
    
    FOR r_cust IN c_customers LOOP
        -- Calculate age in years
        v_age := MONTHS_BETWEEN(SYSDATE, r_cust.DOB) / 12;
        
        IF v_age > 60 THEN
            -- Update interest rates for all loans of this customer
            -- Apply a 1% discount (reducing the interest rate value by 1, e.g. from 7.5% to 6.5%)
            UPDATE Loans
            SET InterestRate = InterestRate - 1
            WHERE CustomerID = r_cust.CustomerID;
            
            v_updated_count := SQL%ROWCOUNT;
            
            IF v_updated_count > 0 THEN
               DBMS_OUTPUT.PUT_LINE('Customer: ' || r_cust.Name || ' (Age: ' || TRUNC(v_age) || ') - Updated ' || v_updated_count || ' loan(s) with 1% discount.');
            ELSE
               DBMS_OUTPUT.PUT_LINE('Customer: ' || r_cust.Name || ' (Age: ' || TRUNC(v_age) || ') - No active loans found.');
            END IF;
        END IF;
    END LOOP;
    
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Interest rate discount processing completed.');
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error occurred: ' || SQLERRM);
END;
/

/*
EXPECTED OUTPUT:
--- Processing Loan Interest Discounts for Seniors (> 60 years old) ---
Customer: Alice Smith (Age: 65) - Updated 1 loan(s) with 1% discount.
Customer: Charlie Brown (Age: 72) - Updated 1 loan(s) with 1% discount.
Interest rate discount processing completed.

PL/SQL procedure successfully completed.
*/

