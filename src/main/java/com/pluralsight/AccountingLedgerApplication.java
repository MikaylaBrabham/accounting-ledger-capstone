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

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

//import file management
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;



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
                    Transactions deposit = transactionData(myScanner, false);
                    saveTransaction(deposit);
                    break;
                case "P":
                    Transactions payment = transactionData(myScanner, true);
                    saveTransaction(payment);
                    break;
                case "L":
                    showLedgerScreen(myScanner);
                    break;
                case "X":
                    appRunning = false;
                    break;
                default:
                    System.out.println("That Entry is Invalid.");
            }
        }
    }

    // Create a transaction class that will enter the different variables for the ledger
    public static class Transactions {

        // create variables with different data properties
        private String date;
        private String time;
        private String description;
        private String vendor;
        private Double amount;

        // gather constructors for created variables
        public Transactions(String date, String time, String description, String vendor, Double amount) {
            this.date = date;
            this.time = time;
            this.description = description;
            this.vendor = vendor;
            this.amount = amount;
        }

        // Add getters
        public String getDate() {
            return date;
        }

        public String getTime() {
            return time;
        }

        public String getDescription() {
            return description;
        }

        public String getVendor() {
            return vendor;
        }

        public Double getAmount() {
            return amount;
        }

        // create a variable the formats the returned data in the given format
        // date|time|description|vendor|amount
        public String formattedTransaction() {
            return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;

        }
    }

    // Create method to get d) deposit (-) and p) payment (+) amount information
    public static Transactions transactionData(Scanner scanner, boolean paymentInfo) {

        // prompt user for the description and format output
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        // prompt user for the vendor and format output
        System.out.print("Vendor: ");
        String vendor = scanner.nextLine().trim();

        //prompt user for the amount and format output
        System.out.print("Amount: ");
        Double amount = Double.parseDouble(scanner.nextLine().trim());

        // add if statements to get amount
        if (paymentInfo && amount > 0) {
            amount = amount * -1;
        }
        // add time and date
        String date = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();
        String time = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss:")).toString();
        // print all transactions
        Transactions transactions = new Transactions(date, time, description, vendor, amount);
        return transactions;
    }

    // add all transactions to new transactions file
    public static void saveTransaction(Transactions transaction) {
        try {
            // create and add file writer to transactions.csv and buffered
            BufferedWriter toWriter = new BufferedWriter(new FileWriter("transactions.csv", true));

            // get transaction to convert to csv file
            toWriter.write(transaction.formattedTransaction());
            toWriter.newLine();
            toWriter.close();

            // add a statement to let user know transaction completed
            System.out.println("Your Transaction Was Successfully Completed.");

            //Add exception and display
        } catch (IOException e) {
            System.out.println("Oops, There Was An Error Saving Your Transaction.");
            e.printStackTrace();
        }


    }

    // Add new ledger and new screen
    public static void showLedgerScreen(Scanner myscanner) {
        boolean ledgerRunning = true;

        // add while loop to make menu to give user options
        while (ledgerRunning) {
            System.out.println("Here's What you've done so far\n" +
                    "Please choose your view");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("H) Home");

            // add variable name that starts ledger switch menu
            String mySelection = myscanner.nextLine().trim().toUpperCase();

            // create switch from choices outlined above
            switch (mySelection) {
                case "A":
                    readTransaction("ALL");
                    break;
                case "D":
                    readTransaction("DEPOSIT");
                    break;
                case "P":
                    readTransaction("PAYMENT");
                    break;
                case "H":
                    ledgerRunning = false;
                    break;
                default:
                    System.out.println("That Entry Is Invalid");
            }

        }
    }

    // read the transaction file
    public static void readTransaction(String write) {
        try {
            BufferedReader toReader = new BufferedReader(new FileReader("transactions.csv"));


            String readline;

            // add while statement to read transaction file
            while ((readline = toReader.readLine()) != null) {
                //get reader to read data by parsing data by pipe
                String[] readparts = readline.split("\\|");
                double amount = Double.parseDouble(readparts[4]);

                //add if statement to print the display menu options based on user choice
                if (write.equals("ALL")) {
                    System.out.println(readline);
                }else if(write.equals("DEPOSIT")&& amount > 0) {
                    System.out.println(readline);
                } else if (write.equals("PAYMENT") && amount < 0) {
                    System.out.println(readline);

                }
            }
            //close scanner
            toReader.close();

            // set exceptions
        } catch (IOException e) {
            System.out.println("Oops, The File Ran Into An Issue Reading The File.");
            e.printStackTrace();
        }


    }
}












