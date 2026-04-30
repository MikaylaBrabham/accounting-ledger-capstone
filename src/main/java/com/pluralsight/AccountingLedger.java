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
//Import Scanner
import java.io.BufferedReader;
import java.io.*;
import java.util.Scanner;
//Import FileWriter
import java.io.FileWriter;



public class AccountingLedger {

    //add main method to project class
    public static void main(String[] args) {
        //Create a scanner to gather user data when called
        Scanner myScanner = new Scanner(System.in);

        //add a header introduction to the file
        System.out.println("***--- Mikayla's Accounting Ledger ---***");

        myScanner.close();
    }
}

public static void transactionsFile(Transaction transaction){
        try {
            // craete buffreaderand filereader
            Bufferedreader bufReader = new BufferedReader(new FileReader("transactions.csv"));

            //Create a CSV File called transactions using FileWriter
            FileWriter myWriter = new FileWriter("transactions.csv")
            //Format input to CSV File to accept single line
            //Format input to match given examples
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
