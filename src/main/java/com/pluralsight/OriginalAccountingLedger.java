package com.pluralsight;
/***
 Mikayla Brabham
 Capstone One (Workbook 1a - 3b)
 Accounting Ledger
 Instructor: Eric Schwartz
 YearUpUnited 5/1/2026
 Citations: BroCode, W3Schools, Peers(Thank you!)
 */

// import date time
import java.time.LocalDate
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
//import file management
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OriginalAccountingLedger {
    //Add main method
    public static void main(String[] args) {//add main method to project class
        // Create a scanner to gather user data when called
        Scanner myScanner = new Scanner(System.in);


        //add a header introduction to the file
        System.out.print("***--- Mikayla's Accounting Ledger ---***");

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
        private final String date;
        private final String time;
        private final String description;
        private final String vendor;
        private final Double amount;

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
        double amount = 0;
        boolean validAmount = false;
        //create a while loop that ensures my amount won't crash
        while (!validAmount) {
            System.out.print("Amount: ");
            String amountInput = scanner.nextLine().trim();

            try {
                amount = Double.parseDouble(amountInput);
                validAmount = true;
            } catch (NumberFormatException e) {
                System.out.println("That Amount Entry Is Invalid..");
            }
        }

        // add if statements to get amount
        if (paymentInfo && amount > 0) {
            amount = amount * -1;
        }
        // add time and date
        String date = java.time.LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
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
            System.out.println("Please choose your view\n");
            System.out.println("A) All Entries");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");

            // add variable name that starts ledger switch menu
            String mySelection = myscanner.nextLine().trim().toUpperCase();

            // create switch from choices outlined above
            switch (mySelection) {
                case "A":
                    displayTransaction("ALL");
                    break;
                case "D":
                    displayTransaction("DEPOSIT");
                    break;
                case "P":
                    displayTransaction("PAYMENT");
                    break;
                case "R":
                    showReportsScreen(myscanner);
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
    public static void displayTransaction(String type) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                double amount = Double.parseDouble(parts[4]);

                if (type.equals("ALL")) {
                    System.out.println(line);
                } else if (type.equals("DEPOSIT") && amount > 0) {
                    System.out.println(line);
                } else if (type.equals("PAYMENT") && amount < 0) {
                    System.out.println(line);
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
        }
    }
    // Reports screen
    public static void showReportsScreen(Scanner myscanner) {
        boolean reportsRunning = true;

        while (reportsRunning) {
            System.out.println("Reports Screen");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            String reportSelection = myscanner.nextLine().trim();

            switch (reportSelection) {
                case "1":
                    System.out.println("Month To Date.");
                    break;
                case "2":
                    System.out.println("Previous Month.");
                    break;
                case "3":
                    System.out.println("Year To Date.");
                    break;
                case "4":
                    System.out.println("Previous Year.");
                    break;
                case "5":
                    searchByVendor(myscanner);
                    break;
                case "0":
                    reportsRunning = false;
                    break;
                default:
                    System.out.println("That Entry Is Invalid");
            }
        }
    }

    // Search by vendor report
    public static void searchByVendor(Scanner myscanner) {
        System.out.print("Enter vendor name: ");
        String vendorSearch = myscanner.nextLine().trim().toLowerCase();
        //enter
        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String vendor = parts[3].toLowerCase();

                if (vendor.contains(vendorSearch)) {
                    System.out.println(line);
                }
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Oops, Error searching by vendor.");
            e.printStackTrace();
        }
    }


}