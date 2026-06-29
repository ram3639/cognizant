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
PROMPT 3. EXECUTING EXERCISE 1 (CONTROL STRUCTURES)
PROMPT ======================================================================

PROMPT [Executing Scenario 1: Loan Discount for Seniors (>60)]
@exercise1_scenario1.sql

PROMPT [Executing Scenario 2: Promote VIPs (Balance > 10,000)]
@exercise1_scenario2.sql

PROMPT [Executing Scenario 3: Due Loan Reminders (Due in next 30 days)]
@exercise1_scenario3.sql


PROMPT 
PROMPT ======================================================================
PROMPT 4. VERIFYING EXERCISE 1 EFFECTS
PROMPT ======================================================================
PROMPT --- Customers (Expect Alice Smith and David Miller to have IsVIP = TRUE) ---
SELECT CustomerID, Name, Balance, IsVIP FROM Customers;

PROMPT --- Loans (Expect Alice Smith and Charlie Brown interest rates reduced by 1.00) ---
SELECT LoanID, CustomerID, InterestRate, LoanAmount, DueDate FROM Loans;


PROMPT ======================================================================
PROMPT VERIFICATION SCRIPTS COMPLETE
PROMPT ======================================================================

