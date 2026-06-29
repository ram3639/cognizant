-- Exercise 3, Scenario 2: Update Employee Bonus
-- Creates a stored procedure UpdateEmployeeBonus that updates the salary of employees 
-- in a given department by adding a bonus percentage passed as a parameter.

SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus (
    p_dept IN VARCHAR2,
    p_bonus_pct IN NUMBER
) AS
    v_rows_updated NUMBER := 0;
BEGIN
    DBMS_OUTPUT.PUT_LINE('--- Updating Employee Salaries with Bonus ---');
    
    -- Parameter validation: check that bonus percentage is non-negative
    IF p_bonus_pct < 0 THEN
        RAISE_APPLICATION_ERROR(-20001, 'Bonus percentage cannot be negative. Provided: ' || p_bonus_pct);
    END IF;
    
    -- Parameter validation: check department name is not null
    IF p_dept IS NULL THEN
        RAISE_APPLICATION_ERROR(-20002, 'Department name cannot be null.');
    END IF;

    -- Update salary
    UPDATE Employees
    SET Salary = Salary * (1 + (p_bonus_pct / 100))
    WHERE UPPER(Department) = UPPER(p_dept);
    
    v_rows_updated := SQL%ROWCOUNT;
    
    IF v_rows_updated = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Warning: No employees found in department: ' || p_dept);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Successfully updated salaries for ' || v_rows_updated || 
                             ' employee(s) in department: ' || p_dept || 
                             ' with a bonus of ' || p_bonus_pct || '%.');
    END IF;
    
    COMMIT;
EXCEPTION
    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Error in UpdateEmployeeBonus: ' || SQLERRM);
        RAISE;
END UpdateEmployeeBonus;
/

/*
EXPECTED COMPILATION OUTPUT:
Procedure created.

EXPECTED EXECUTION OUTPUT (when calling UpdateEmployeeBonus('IT', 10)):
--- Updating Employee Salaries with Bonus ---
Successfully updated salaries for 2 employee(s) in department: IT with a bonus of 10%.
*/

