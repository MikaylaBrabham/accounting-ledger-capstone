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
import java.time.LocalDate;
import java.util.Scanner;

//import file management
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

//import array management
import java.util.ArrayList;
import java.util.Collections;



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
            System.out.println("R) Reports");
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
                case "R":
                    readReportScreen(myscanner);
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

            //create array list
            ArrayList<String> transactions = new ArrayList<>();
            String readline;

            // add while statement to read transaction file
            while ((readline = toReader.readLine()) != null) {
                transactions.add(readline);
            }
            //close scanner
            toReader.close();
            //format so  transactions show newest to oldest
            Collections.reverse(transactions);

            //get reader to read data by parsing data by pipe
            for (String readLine : transactions) {
                String[] readParts = readline.split("\\|");
                double amount = Double.parseDouble(readParts[4]);

                //add if statement to print the display menu options based on user choice
                if (write.equals("ALL")) {
                    System.out.println(readline);
                } else if (write.equals("DEPOSIT") && amount > 0) {
                    System.out.println(readline);
                } else if (write.equals("PAYMENT") && amount < 0) {
                    System.out.println(readline);

                }
            }


            // set exceptions
        } catch (IOException e) {
            System.out.println("Oops, The File Ran Into An Issue Reading The File.");
            e.printStackTrace();
        }

    }


    // add report screen
    public static void readReportScreeneadReportScreen(Scanner myScanner) {
        boolean reportsRunning = true;

        // create while loop to create menu report options for user to choose
        while (reportsRunning) {
            System.out.println("Reports Screen\n" +
                    "Please make your selection\t");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Vendor");
            System.out.println("0) Back");

            //print users choice from menu
            String mySelection = myScanner.nextLine().trim().toUpperCase();

            // create switch to exapnd on options
            switch (mySelection) {
                case "1":
                    byDateReport("MONTHTODATE");
                    break;
                case "2":
                    byDateReport("PREVIOUSMONTH");
                    break;
                case "3":
                    byDateReport("YEARTODATE");
                    break;
                case "4":
                    byDateReport("PREVIOUSYEAR");
                case "5":
                    byVendorReport(myScanner);
                    break;
                case "0":
                    reportsRunning = false;
                default:
                    System.out.println("That Entry Is Invalid");
            }
        }
        //add searchbyvendor report
        public static void byVendorReport (Scanner myScanner){
            //prompt userf or vendor
            System.out.println("Enter The Vendors Name: ");
            String byVendor = myScanner.nextLine().trim().toLowerCase();

            //enter try statement  to read and pass to transactionfile
            try {
                BufferedReader toReader = new BufferedReader(new FileReader("transactions.csv"));

                String readline;

                // add while statement to read transaction file
                while ((readline = toReader.readLine()) != null) {
                    //get reader to read data by parsing data by pipe
                    String[] readparts = readline.split("\\|");
                    String vendor = readparts[3].toLowerCase();

                    //add if statement to print the display menu options based on user choice
                    if (vendor.contains(byVendor)) {
                        System.out.println(readline);
                    }
                }
                //close reader
                toReader.close();
            }
            //add catch exception
            catch (IOException e) {
                System.out.println("Oops, The File Ran Into An Issue Searching By The Vendor.");
                e.printStackTrace();
            }
        }
    }

    //add date report method
    public static void byDateReport(String reportTyping) {
            //create variable for now
            LocalDate todayNow = LocalDate.now();
            //create try to read file and assign variables to the correct date and format

        try {
                BufferedReader toReader = new BufferedReader(new FileReader("transactions.csv"));

            String dateTimeLine;

            while ((dateTimeLine = toReader.readLine()) != null) {
                String [] readParts = dateTimeLine.split("\\|");

                LocalDate transactionDates = LocalDate.parse(readParts[0]);

                //create if statement to assign parsed varaiables to datereport
                if (reportTyping.contentEquals("MONTHTODATE" && transactionDates.getMonth() == todayNow.getMonth()
                    &&   transactionDates.getYear() == todayNow.getYear()){
                    System.out.println(dateTimeLine);

                }
                else if(reportTyping.contentEquals("PERMONTH" && transactionDates.getMonth().() == todayNow.minusMonths(1).getMonth()
                        && todayNow.getYear() == transactionDates.getYear(){
                    System.out.println(dateTimeLine);

                }





                    }
            }
        }
    }
}













