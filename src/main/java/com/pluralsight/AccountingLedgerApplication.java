package com.pluralsight;

/***
 * Mikayla Brabham
 * Capstone One (Workbook 1a - 3b)
 * Accounting Ledger
 ***/


//import file & buff reader/writer
  import java.io.BufferedReader;
  import java.io.BufferedWriter;
  import java.io.FileReader;
  import java.io.FileWriter;

  //import exceptions
  import java.io.IOException;
  import java.time.LocalDate;
  import java.time.format.DateTimeFormatter;
  import java.util.ArrayList;
  import java.util.Collections;
  import java.util.Scanner;

  public class AccountingLedgerApplication {
//add main method
      public static void main(String[] args) {
          try (Scanner myScanner = new Scanner(System.in)) {
              System.out.println("***--- Mikayla's Accounting Ledger ---***");
              showHomeScreen(myScanner);
          }
      }
//add home screen
      public static void showHomeScreen(Scanner myScanner) {
          boolean appRunning = true;

          // add user options for the home screen
          while (appRunning) {
              System.out.println("Welcome to Mikayla's Bank!\nPlease choose an option below!\t");
              System.out.println("D) Add Deposit");
              System.out.println("P) Make a Payment");
              System.out.println("L) Ledger");
              System.out.println("X) Exit");
                // format user scanner to their selection
              String customerSelection = myScanner.nextLine().trim().toUpperCase();
                // create switch choice options from user selection
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
// add transaction data
      public static Transactions transactionData(Scanner scanner, boolean paymentInfo) {
          System.out.print("Description: ");
          String description = scanner.nextLine().trim();

          System.out.print("Vendor: ");
          String vendor = scanner.nextLine().trim();

          System.out.print("Amount: ");
          Double amount = 0.0;
          try {
              amount = Double.parseDouble(scanner.nextLine().trim());
          } catch (NumberFormatException e) {
              System.out.println("Invalid amount entered.");
          }

          if (paymentInfo && amount > 0) {
              amount = -amount;
          }
            //format date and time
          String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
          String time = java.time.LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

          return new Transactions(date, time, description, vendor, amount);
      }
//add transaction file
      public static void saveTransaction(Transactions transaction) {
          try (BufferedWriter toWriter = new BufferedWriter(new FileWriter("transactions.csv", true))) {
              toWriter.write(transaction.formattedTransaction());
              toWriter.newLine();
              System.out.println("Your Transaction Was Successfully Completed.");
          } catch (IOException e) {
              System.out.println("Oops, There Was An Error Saving Your Transaction.");
              e.printStackTrace();
          }
      }
//add ledger screen
      public static void showLedgerScreen(Scanner myscanner) {
          boolean ledgerRunning = true;
//add user options
          while (ledgerRunning) {
              System.out.println("Please choose your view\n");
              System.out.println("A) All Entries");
              System.out.println("D) Deposits Only");
              System.out.println("P) Payments Only");
              System.out.println("R) Reports");
              System.out.println("H) Home");

              String mySelection = myscanner.nextLine().trim().toUpperCase();
            // add switch to tell users what to do with info
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

      public static void readTransaction(String write) {
          try (BufferedReader toReader = new BufferedReader(new FileReader("transactions.csv"))) {
              ArrayList<String> transactions = new ArrayList<>();
              String line;

              while ((line = toReader.readLine()) != null) {
                  transactions.add(line);
              }

              Collections.reverse(transactions);

              for (String txn : transactions) {
                  String[] readParts = txn.split("\\|");
                  if (readParts.length < 5) {
                      continue;
                  }
                  double amount;
                  try {
                      amount = Double.parseDouble(readParts[4]);
                  } catch (NumberFormatException nfe) {
                      continue;
                  }

                  if (write.equals("ALL")) {
                      System.out.println(txn);
                  } else if (write.equals("DEPOSIT") && amount > 0) {
                      System.out.println(txn);
                  } else if (write.equals("PAYMENT") && amount < 0) {
                      System.out.println(txn);
                  }
              }
          } catch (IOException e) {
              System.out.println("Oops, The File Ran Into An Issue Reading The File.");
              e.printStackTrace();
          }
      }

      public static void readReportScreen(Scanner myScanner) {
          boolean reportsRunning = true;

          while (reportsRunning) {
              System.out.println("Reports Screen\nPlease make your selection\t");
              System.out.println("1) Month To Date");
              System.out.println("2) Previous Month");
              System.out.println("3) Year To Date");
              System.out.println("4) Previous Year");
              System.out.println("5) Search by Vendor");
              System.out.println("0) Back");

              String mySelection = myScanner.nextLine().trim().toUpperCase();

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
                      break;
                  case "5":
                      byVendorReport(myScanner);
                      break;
                  case "0":
                      reportsRunning = false;
                      break;
                  default:
                      System.out.println("That Entry Is Invalid");
              }
          }
      }
// start report menu
      public static void byDateReport(String reportTyping) {
          LocalDate todayNow = LocalDate.now();
    //enter file /buff reader
          try (BufferedReader toReader = new BufferedReader(new FileReader("transactions.csv"))) {
              String dateTimeLine;

              while ((dateTimeLine = toReader.readLine()) != null) {
                  String[] readParts = dateTimeLine.split("\\|");
                  if (readParts.length == 0) {
                      continue;
                  }

                  LocalDate transactionDates;
                  try {
                      transactionDates = LocalDate.parse(readParts[0]);
                  } catch (Exception ex) {
                      continue;
                  }
                //enter formatting for the report selection
                  if (reportTyping.equals("MONTHTODATE")
                          && transactionDates.getMonth() == todayNow.getMonth()
                          && transactionDates.getYear() == todayNow.getYear()) {
                      System.out.println(dateTimeLine);
                  } else if (reportTyping.equals("PREVIOUSMONTH")
                          && transactionDates.getMonth() == todayNow.minusMonths(1).getMonth()
                          && transactionDates.getYear() == todayNow.minusMonths(1).getYear()) {
                      System.out.println(dateTimeLine);
                  } else if (reportTyping.equals("YEARTODATE")
                          && transactionDates.getYear() == todayNow.getYear()) {
                      System.out.println(dateTimeLine);
                  } else if (reportTyping.equals("PREVIOUSYEAR")
                          && transactionDates.getYear() == todayNow.minusYears(1).getYear()) {
                      System.out.println(dateTimeLine);
                  }
              }
          } catch (IOException e) {
              System.out.println("Error reading date report.");
              e.printStackTrace();
          }
      }
    // get vendor report
      public static void byVendorReport(Scanner myScanner) {
          System.out.print("Enter The Vendors Name: ");
          String byVendor = myScanner.nextLine().trim().toLowerCase();
    //get reader and buffer reader
          try (BufferedReader toReader = new BufferedReader(new FileReader("transactions.csv"))) {
              String readline;

              while ((readline = toReader.readLine()) != null) {
                  String[] readparts = readline.split("\\|");
                  if (readparts.length < 4) continue;
                  String vendor = readparts[3].toLowerCase();

                  if (vendor.contains(byVendor)) {
                      System.out.println(readline);
                  }
              }
          } catch (IOException e) {
              System.out.println("Oops, The File Ran Into An Issue Searching By The Vendor.");
              e.printStackTrace();
          }
      }
//enter transactions necessary variables
      public static class Transactions {
          private final String date;
          private final String time;
          private final String description;
          private final String vendor;
          private final Double amount;
//get constructors
          public Transactions(String date, String time, String description, String vendor, Double amount) {
              this.date = date;
              this.time = time;
              this.description = description;
              this.vendor = vendor;
              this.amount = amount;
          }
//add getters
          public String getDate() { return date; }
          public String getTime() { return time; }
          public String getDescription() { return description; }
          public String getVendor() { return vendor; }
          public Double getAmount() { return amount; }
//format and add all parsed data together
          public String formattedTransaction() {
              return date + "|" + time + "|" + description + "|" + vendor + "|" + amount;
          }
      }
  }





