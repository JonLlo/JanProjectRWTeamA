
<p align="center"> <img src="https://github.com/Anmol-Baranwal/Cool-GIFs-For-GitHub/assets/74038190/80728820-e06b-4f96-9c9e-9df46f0cc0a5" align=center width="1000"> </p>
<br><br>


# SimpleBank - TEAM A

This banking app, SimpleBank, was created to assist bank tellers with their day-to-day tasks by simplifying tasks and catching errors before they happen. These are the main features:

  ```bash
·	Create and authenticate customers
·	Open and manage bank accounts
·	Perform deposits and withdrawals
·	Apply annual interest and fees
·	Persist customer and account data between sessions
  ```
## [Menu](#menu)
- [About](#about)
- [Use](#use)
- [Contributors](#contributors)
- [Responsibilities](#responsibilities)

## About

Our app uses Java and was built on IntelliJ Idea. It will require JDK 8 or later. This is a group project for Robert Walters. 

## Use

To use this project, download all the files with IntelliJ (we used the Community Edition). 
All the classes should have their own file. 

To start the program, open SimpleBank.java and run SimpleBank.main()
This will start it in the console. 

Bank_data.csv and Bank_log.txt will be created. The first will store customers and account, while the other keeps system timestamps. 
All personal accounts require a default balance of £1 and a 6-digit sort code of 60-60-60, and customers can have multiple. ISA accounts are limited to one per customer and have a 60-60-70 sort code with an annual interest that is applied. Business accounts have a 60-70-70 sort code and are also limited to one per customer with an annual customer fee. 

Please see our in-depth User Guide for a full explanation of fees, authentication, interest and prerequisites for each type of account.


## Contributors

  ```bash
·	Kevin Hyde-Opoku
·	Jonny Lloyd
·	Christina Rin
·	Tolu
 ```

<img src="https://github.com/Anmol-Baranwal/Cool-GIFs-For-GitHub/assets/74038190/fa83eeb9-f4e2-4d85-93f0-688af11babf8" width="35">&nbsp;
## Responsibilities      

### Kevin 

Technical Leader

(Planning project layout)

  ```bash
Customer Data and Account
Core Banking Logic
·	CreateCustomer(), Account classes (Personal, ISA, Business), Customer Class
Visual Paradigm
Test cases and assumptions
  ```



### Jonny

Team Organizer 

(Setting up team meetings and checking progress)

  ```bash
Customer and UI/Menus
Customer interface and menus
·	start(), helponInput(), customerMenu(), showMainHelp(), showCustomerHelp(), authenticateCustomer(), openNewAccount(), performDeposit(), performWithdrawal()
Hosted GitHub repo
Testing
  ```

### Christina 

Editor

(Editing)

  ```bash
Persistence and Logging
Saving, loading and logging
·	saveDataToCSV(), loadDataFromCSV(), Logger, AccountNumberGenerator
README 
Editing/ User Guide
Testing
  ```
### Tolu

Payment Control

(Payments)

  ```bash
Payments
Direct Debit and Standing Order
·	managePayments(), addDirectDebit(), addStandingOrder(), DirectDebit, StandingOrder classes
Testing
  ```




