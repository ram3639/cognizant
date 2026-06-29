-- Master Run and Verification Script for PL/SQL Exercises
-- This script runs schema initialization, sample data insertions, executes 
-- control structure scenarios, compiles and tests the stored procedures, 
-- and prints out table states to verify correct execution.

SET SERVEROUTPUT ON SIZE UNLIMITED;
SET LINESIZE 150;
SET PAGESIZE 100;

PROMPT ======================================================================
PROMPT 1. INITIALIZING SCHEMA & SAMPLE DATA
PROMPT ======================================================================
@schema.sql
@sample_data.sql

PROMPT 
PROMPT ======================================================================
PROMPT 2. INITIAL DATABASE STATE
PROMPT ======================================================================
PROMPT --- Customers ---
SELECT CustomerID, Name, DOB, Balance, IsVIP FROM Customers;

PROMPT --- Loans ---
SELECT LoanID, CustomerID, InterestRate, LoanAmount, DueDate FROM Loans;

PROMPT --- Accounts ---
SELECT AccountID, CustomerID, AccountType, Balance FROM Accounts;

PROMPT --- Employees ---
SELECT EmployeeID, Name, Department, Salary FROM Employees;


PROMPT 
PROMPT ======================================================================
PROMPT 3. COMPILING & EXECUTING EXERCISE 3 (STORED PROCEDURES)
PROMPT ======================================================================


PROMPT [Compiling ProcessMonthlyInterest Procedure]
@exercise3_scenario1.sql

PROMPT [Compiling UpdateEmployeeBonus Procedure]
@exercise3_scenario2.sql

PROMPT [Compiling TransferFunds Procedure]
@exercise3_scenario3.sql

PROMPT 
PROMPT [Executing Stored Procedure: ProcessMonthlyInterest]
BEGIN
    ProcessMonthlyInterest;
END;
/

PROMPT --- Savings Accounts (Expect 1% Interest Addition for Savings) ---
SELECT AccountID, CustomerID, AccountType, Balance FROM Accounts;


PROMPT 
PROMPT [Executing Stored Procedure: UpdateEmployeeBonus for IT department by 10%]
BEGIN
    UpdateEmployeeBonus('IT', 10);
END;
/

PROMPT --- Employees (Expect IT Employee Salaries to increase by 10%) ---
SELECT EmployeeID, Name, Department, Salary FROM Employees;


PROMPT 
PROMPT [Executing Stored Procedure: TransferFunds (Valid: Transfer $500 from Account 201 to Account 203)]
BEGIN
    TransferFunds(201, 203, 500);
END;
/

PROMPT --- Accounts (Expect Account 201 to be decremented, and Account 203 incremented) ---
SELECT AccountID, CustomerID, AccountType, Balance FROM Accounts;


PROMPT 
PROMPT [Executing Stored Procedure: TransferFunds (Invalid: Insufficient Funds, Transfer $100,000)]
BEGIN
    TransferFunds(201, 203, 100000.00);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Caught Expected Application Exception: ' || SQLERRM);
END;
/

PROMPT 
PROMPT [Executing Stored Procedure: TransferFunds (Invalid: Negative Amount, Transfer -$50.00)]
BEGIN
    TransferFunds(201, 203, -50.00);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Caught Expected Application Exception: ' || SQLERRM);
END;
/

PROMPT 
PROMPT [Executing Stored Procedure: TransferFunds (Invalid: Non-existent Destination Account)]
BEGIN
    TransferFunds(201, 999, 100.00);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Caught Expected Application Exception: ' || SQLERRM);
END;
/

PROMPT ======================================================================
PROMPT VERIFICATION SCRIPTS COMPLETE
PROMPT ======================================================================
