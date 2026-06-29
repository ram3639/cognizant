-- Sample Data insertion script

-- Clean up any existing data (if tables weren't dropped)
DELETE FROM Loans;
DELETE FROM Accounts;
DELETE FROM Employees;
DELETE FROM Customers;

-- 1. Insert Customers
-- Alice: Age 65 (Born 1961), balance $12,000 (eligible for VIP and Discount)
INSERT INTO Customers (CustomerID, Name, DOB, Balance, IsVIP)
VALUES (1, 'Alice Smith', TO_DATE('1961-05-15', 'YYYY-MM-DD'), 12000.00, 'FALSE');

-- Bob: Age 58 (Born 1968), balance $8,000 (not eligible for VIP or Discount)
INSERT INTO Customers (CustomerID, Name, DOB, Balance, IsVIP)
VALUES (2, 'Bob Jones', TO_DATE('1968-11-20', 'YYYY-MM-DD'), 8000.00, 'FALSE');

-- Charlie: Age 72 (Born 1954), balance $500 (eligible for Discount but not VIP)
INSERT INTO Customers (CustomerID, Name, DOB, Balance, IsVIP)
VALUES (3, 'Charlie Brown', TO_DATE('1954-03-10', 'YYYY-MM-DD'), 500.00, 'FALSE');

-- David: Age 30 (Born 1996), balance $25,000 (eligible for VIP but not Discount)
INSERT INTO Customers (CustomerID, Name, DOB, Balance, IsVIP)
VALUES (4, 'David Miller', TO_DATE('1996-08-25', 'YYYY-MM-DD'), 25000.00, 'FALSE');


-- 2. Insert Loans
-- Alice: Due in 20 days (Within 30 days)
INSERT INTO Loans (LoanID, CustomerID, InterestRate, LoanAmount, DueDate)
VALUES (101, 1, 7.50, 5000.00, SYSDATE + 20);

-- Bob: Due in 45 days (Outside 30 days)
INSERT INTO Loans (LoanID, CustomerID, InterestRate, LoanAmount, DueDate)
VALUES (102, 2, 6.50, 15000.00, SYSDATE + 45);

-- Charlie: Due in 15 days (Within 30 days)
INSERT INTO Loans (LoanID, CustomerID, InterestRate, LoanAmount, DueDate)
VALUES (103, 3, 8.00, 2000.00, SYSDATE + 15);


-- 3. Insert Accounts
-- Alice: Savings Account
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance)
VALUES (201, 1, 'Savings', 12000.00);

-- Bob: Checking Account
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance)
VALUES (202, 2, 'Checking', 8000.00);

-- Charlie: Savings Account
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance)
VALUES (203, 3, 'Savings', 500.00);

-- David: Savings Account
INSERT INTO Accounts (AccountID, CustomerID, AccountType, Balance)
VALUES (204, 4, 'Savings', 25000.00);


-- 4. Insert Employees
INSERT INTO Employees (EmployeeID, Name, Department, Salary)
VALUES (501, 'John Doe', 'IT', 5000.00);

INSERT INTO Employees (EmployeeID, Name, Department, Salary)
VALUES (502, 'Jane Doe', 'IT', 6000.00);

INSERT INTO Employees (EmployeeID, Name, Department, Salary)
VALUES (503, 'Bob Johnson', 'HR', 4500.00);

INSERT INTO Employees (EmployeeID, Name, Department, Salary)
VALUES (504, 'Alice Davis', 'Sales', 5500.00);

COMMIT;
