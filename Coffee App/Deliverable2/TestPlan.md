# Test Plan - Team 22

## 1. Introduction

The CoffeeCart program will be used by coffee cart owners to keep track of points customers incur through purchases of coffee and desserts in their establishment.  

- It will use the Android interface to create and edit accounts, add points to accounts for purchases, allow VIP level members to pre-order desserts up to a month in advance, and generate reports for management about daily sales and upcoming preorder purchases.

## 2. Quality Control

- In determining quality control, we need to evaluate sample testing situations from various viewpoints (account parameters, point-of-sale and daily reports), each of which use differing inpurs and expected behaviors.

### 2.1 - Test Plan Quality

- In the first prototype and later iterations, we will be testing proof of concept ideas such as creating and adding accounts, point of sale purchases of both coffee and desserts, and getting daily reports.  

- In the final version, we will simulate input in various screens from multiple devices, determining how the system handles account creation, purchases, reservations, and reports in real time.

### 2.2 - Adequacy Criterion

- In the first prototype and later iterations, we will be using known input data for which we know the expected value.  

### 2.3 - Bug Tracking

- During development, bugs and enhancement requests will be tracked by the GitHub communication protocols as well as team communications through chat and e-mail.

- After development, it is assumed bugs found by the users would be brought to the attention of the coffee shop management, which in turn would relay those bugs as well as preferred enhancement requests to the development team.  Recurring user errors should be accommodated for as reasonably as possible, and client errors should be eliminated.

# 3 - Test Strategy

- The prototype phase will involve ensuring that the program can handle input of parameters correctly, such as personal information in the case of account creation, properly attributing purchase points to an account during point of sale to existing testing accounts, and gathering input correctly to prepare purchase reports for a given day or month.

- Later steps of testing should ensure that accounts can be created from the GUI interface, point of sale transactions can be properly applied to these new accounts, and daily and monthly reports correctly indicate updates to the purchases over these time periods from these new accounts.  The output should be determined independently and checked against our program for further refinements.  User error should be handled gracefully and client errors should be as close to zero as possible.

## 3.1 - Testing Process

- A prototype for data entry was developed, and data was entered in an Android emulator as close to the manner management is expected to use.  

## 3.2 - Technology

- Simple debugging statements as popup messages should allow testing of prototype input verification.  Later on, output of account state and POS reports to text files will verify correct behavior is being executed.

# 4 - Test Cases

Testing cases are divided by categories indicating expected behaviors of the program suite:

- Data Entry - Account Setup

  - Enter with all fields (First Name, Last Name, Phone Number, Birthdate) set

    - Expected Input: Correctly formatted data in fields
    
    - Expected Result: Account created with unused account number, correctly set fields, and no VIP points.

    - Test Status: 
        
  - Enter with all fields set, except for phone number
  
    - Expected Input: All fields but Phone Number correctly formatted (a long is expected for phone number.)
    
    - Expected Result: Error message shown, no account created.
    
    - Test Status: 
    
  - Enter with all fields set, except for birthdate
  
    - Expected Input: All fields but birth date correctly formatted (a long is expected for phone number.)
    
    - Expected Result: Error message shown, no account created.
    
    - Test Status: 
    
  - Enter with only name fields set
  
    - Expected Input: All name fields set, phone number and birthdate fields blank.
    
    - Expected Result: Error message shown, no account created.
    
    - Test Status: 
    
  - Enter with only one name field set (test once with first name, once with second name.)
  
    - Expected Input: One name field set, phone number and birthdate fields set.
    
    - Expected Result: Error message shown, no account created.
    
    - Test Status: 
    
  - Enter with no fields set
  
    - Expected Input: All fields empty
    
    - Expected Result: Error message shown, no account created.
    
    - Test Status: 
    
- Data Entry - Change Information

  - Change any of four fields (test once for each field)
  
    - Expected Input: One field changed, other fields empty
    
    - Expected Result: Indicated account field changed, no change in other fields, no change in VIP points
    
    - Test Status: 
    
  - Change any two of four fields (test once for each group of two fields)
  
    - Expected Input: Two fields changed, two fields empty
    
    - Expected Result: Indicated account fields changed, no change in other field, no change in VIP points
    
    - Test Status: 
    
  - Change any three of four fields (test once for each group of two fields)
  
    - Expected Input: three fields changed, one field empty
    
    - Expected Result: Indicated account fields changed, no change in other field, no change in VIP points
    
    - Test Status: 
    
  - Change all four fields
  
    - Expected Input: Four fields changed
    
    - Expected Result: Indicated account fields changed, no change in VIP points
    
    - Test Status: 
    
  - Delete Entry
  
    - Expected Input: Request to delete entry
    
    - Expected Result: Popup Message that result cannot be undone, Verification request in popup box, change all account fields to blank, change VIP points to 0.
    
    - Test Status: 
    
- Ordering Items

  -  With no account
     
    - Expected Input: Purchase request for given amount, no account number given
    
    - Expected Result: Popup Message asking if the customer would like to create an account or look up account number, allow creation of account and attribution of points
  
    - Test Status: 
      
  - With forgotten account number
       
    - Expected Input: Purchase request for given amount, no account number given
    
    - Expected Result: Popup Message asking if the customer would like to create an account or look up account number, allow search based on phone number field, properly attribute points to new/existing account
    
    - Test Status: 
    
  - With Account (not yet Gold status)
       
    - Expected Input: Purchase request for given amount, account number given
    
    - Expected Result: Properly attribute points to existing account
    
    - Test Status: 
    
  - With Account (currently Gold status)
  
    - Expected Input: Purchase request for given amount, account number given
    
    - Expected Result: Popup Message to remind POS that refills are free for VIP customers, properly attribute points to existing account.
    
    - Test Status: 
    
- Pre Ordering Items

  - Non Gold Pre-Order an item
  
    - Expected Input: Request dessert for given date, account number given
    
    - Expected Result: Verify Gold status, Popup Message indicating only VIP members may pre-order desserts.
    
    - Test Status: 

  - Gold Pre-Order an In Stock item within one month of current date
  
    - Expected Input: Request dessert for given date, account number given
    
    - Expected Result: Verify Gold status, verify dessert is available for date, update dessert availability count, give confirmation number for dessert order.
    
    - Test Status: 
    
  - Pre-Order an item more than one month of current date
  
    - Expected Input: Request dessert for given date, account number given
    
    - Expected Result: Popup Message indicating dessert is not available for pre-order for this date
    
    - Test Status: 
    
  - Pre-Order an item for a date in the past
  
    - Expected Input: Request dessert for given date, account number given
    
    - Expected Result: Popup Message indicating dessert is not available for pre-order for this date
    
    - Test Status: 
    
  - Pre-Order an item no longer available on given date
  
    - Expected Input: Request dessert for given date, account number given
    
    - Expected Result: Verify Gold status, verify dessert is available for date, Popup Message indicating dessert is not available for pre-order for this date
    
    - Test Status: 
    
  - Cancel existing Pre-Order, confirmation number given
  
    - Expected Input: Cancel dessert for given date, confirmation number given
    
    - Expected Result: Verify reservation of dessert with confirmation number, update dessert availability count, give confirmation number for cancellation
    
    - Test Status: 
    
  - Cancel existing Pre-Order, confirmation number not given, account number given
  
    - Expected Input: Lookup pre-order by account number
    
    - Expected Result: Verify reservation of dessert with account number, update dessert availability count, give confirmation number for cancellation
    
    - Test Status: 
    
  - Cancel nonexisting Pre-Order
  
    - Expected Input: Cancel dessert for given date, confirmation number given
    
    - Expected Result: Popup Message indicating no reservation for that date is held, request customer check confirmation number.
    
    - Test Status:
    
- Get Daily Report

  - Request daily report for current date
  
    - Expected Input: Request Daily Report, Enter current date
    
    - Expected Result: Produce CSV with all purchases for the given date, all pre-orders for current date.
    
    - Test Status:
    
  - Request pre order report for current date
  
    - Expected Input: Request Best Seller Report, Enter current date
    
    - Expected Result: Produce CSV with all purchases for the given date, all pre-orders for given date
    
    - Test Status:
    

