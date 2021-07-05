# VendingMachine

Project Title: Designing Vending Machine

Motivation: To Design a vending machine to achieve following functionalities and test with testing framework:

              1. Accepts coins of 1,5,10,25 Cents i.e. penny, nickel, dime, and quarter.

              2. Allow user to select products Coke(25), Pepsi(35), Soda(45)

              3.Allow user to take refund by cancelling the request.

              4. Return selected product and remaining change if any.

              5. Allow reset operation for vending machine supplier.

Design:

Interface: Interface class to declare the above functionalities
Implemenataion : Implementation class defines the above functionalities.
Utility: This folder contains the support functionalities:
  Constants -  Single Placeholder for all the constants to avoid hardcoding
  Inventory -  Utility class to maintain the product and price in the inventory.
  Product - Enum Class to define the products in the inventory.
  Coin - Enum Class to define the coins that are accepted by Vending machine.
  Bucket - Utility class to hold the product and price for every buy.
 
 Exception: Exception to class to catch custom errors in the vending machine
 
 Testing Framework: Junit Framework to validate the vending machine functionalities
    The following scenarios are covered:
      1.Buying a product with exact price
      2.Buying a product with excess price
      3.Refund of amount after cancellation
      4.Product unavailability
      5.Insufficient balance in the account
      
 Steps to execute:
  The framework is developed using Maven and added <build> with org.apache.maven.plugins to run the application.
  Open command prompt
  Move to the Project location
  Give the command "mvn clean test" which will execute the test scripts to the validate the application
