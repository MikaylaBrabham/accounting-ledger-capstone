package com.pluralsight;
/***
 * Mikayla Brabham
 * Capstone One (Workbook 1a - 3b)
 * Accounting Ledger
 * Instructor: Eric Schwartz
 * YearUpUnited 5/1/2026
 *
 * Citations:
 *
 */

// import buffreader, filereader, scanner

import java.io.BufferedReader;
import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.io.FileWriter;

public class AccountingLedgerApplication {
    //Add main method
    public static void main(String[] args) {
        //add main method to project class
        //Create a scanner to gather user data when called
        Scanner myScanner = new Scanner(System.in);

        //add a header introduction to the file
        System.out.println("***--- Mikayla's Accounting Ledger ---***");

        // add home page
        showHomeScreen(myScanner);

        myScanner.close();

    }

    // Create the home screen
    public static void showHomeScreen(Scanner myScanner) {
        // when appRunning is true run home screen
        boolean appRunning = true;

        //create while loop to prompt user to pick an option from the menu
        while (appRunning) {
            //add prompts for the user to choose from
            System.out.println("Welcome to Mikayla's Bank!\n" +
                    "Please choose an option below!\t");
            System.out.println("D) Add Deposit");
            System.out.println("P) Make a Payment");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");

            // create variable name that starts switch menu that:
            // prints customer choice
            // formats output
            String customerSelection = myScanner.nextLine().trim().toUpperCase();

            //create switch from choices outlined above
            switch (customerSelection) {
                case "D":
                    System.out.println("You Selected: Add Deposition\n" +
                            "Please Enter your Deposit Information: ");
                    break;
                case "P":
                    System.out.println("You Selected: Make Payment\n" +
                            "Please Enter your Debit Information: ");
                    break;
                case "L":
                    System.out.println("You Selected: Add Ledger");
                    break;
                case "X":
                    appRunning = false;
                    break;
                default:
                    System.out.println("That Entry is Invalid.");
            }
        }
    }

    // Create a transaction class that will enter the different variables for the given format
    public class Transactions {

        // create variables with different data properties
        // in the given format date|time|description|vendor|amount
        private String date;
        private String time;
        private String description;
        private String vendor;
        private Double amount;

        // gather constructors for variables
        public Transactions(String date, String time, String description, String vendor, Double amount) {
            this.date = date;
            this.time = time;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }
        // Add getters
        public String getDate() {return date; }
        public String getTime() {return time; }
        public String getDescription() {return description; }
        public String getVendor() {return vendor; }
        public Double getAmount() {return amount; }



    }
}




