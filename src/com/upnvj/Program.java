package com.upnvj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

class VerifyCard {

  private static int combineDigit(int digit) {
    int sumDigit = 0;

    while (digit > 0) {
        sumDigit += digit % 10;
        digit /= 10;
    }

    return sumDigit;
  }

  private static int luhnAlgorithm(long cardNumber) {
    int result = 0;
    long divisor = 10;

    while(divisor < cardNumber) {
      int digit = (int) ((cardNumber / divisor) % 10) * 2;

      if(digit >= 10) {
        digit = combineDigit(digit);
      }

      result += digit;

      if (divisor * 10 >= cardNumber) {
          break;
      }

      divisor *= 100;
    }

    divisor = 1;
    while (divisor < cardNumber) {
        int digit = (int) ((cardNumber / divisor) % 10);

        if (digit > 10) {
            digit = combineDigit(digit);
        }

        result += digit;

        if (divisor * 10 >= cardNumber) {
            break;
        }

        divisor *= 100;
    }

    return result;
  }

  public static Boolean checkCard(long cardNumber) {
    int result = luhnAlgorithm(cardNumber);

    if (result % 10 == 0)
    {
        return true;
    }
    return false;
  }

  public static String getCardType(long cardNumber) {

    if(checkCard(cardNumber)) {

      String cardNumberString = Long.toString(cardNumber);
      int first_digit = Integer.parseInt(String.valueOf(cardNumberString.charAt(0)));
      int second_digit = Integer.parseInt(String.valueOf(cardNumberString.charAt(1)));
      int length_digit = cardNumberString.length();
      
      if (first_digit == 3 && (second_digit == 7 || second_digit == 4) && (length_digit == 15)) {
        return "AMEX";

      } else if (((first_digit == 2 || first_digit == 5) && (second_digit >= 1 && second_digit <= 5)) && length_digit == 16) {
        return "MASTERCARD";

      } else if (first_digit == 4 && (length_digit == 16 || length_digit == 13)) {
        return "VISA";

      }
    }

    return "INVALID";
  }
}

public class Program {
  private ArrayList<Account> listAccount = new ArrayList<>();
  private ArrayList<Transaction> listTransaction = new ArrayList<>();
  private Account account;
  private Transaction latestTransaction;

  public Account getAccount() {
    return account;
  }

  public String getCardType() {
    return VerifyCard.getCardType(this.account.getCardNumber());
  }

  public String getTransactionOTP() {
    return latestTransaction.getOTP();
  }

  public String getTransactionDate() {
    return latestTransaction.getDate();
  }

  public String getTransactionId() {
    return latestTransaction.getIdTransaction();
  }

  public String formatBalance(int number) {
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    String s = format.format(number) + ",-";
    return s;
  }

  public ArrayList<Account> getListAccount() {
    return listAccount;
  }

  // public ArrayList<Object> getRowTable() {

  //   ArrayList<Object> objList = new ArrayList<>();
  //   ArrayList<Transaction> tr = account.getListTransaction();
  //   Collections.reverse(tr);

  //   for (Transaction t : tr) {
  //     objList.add(new Object[] {t.getDate(), t.getTransactionType(), Integer.toString(t.getCredit())});
  //   }

  //   return objList;
  // }

  public String[][] getRowTable() {
    return account.getListTransaction();
  }

  public int saveMoney(int credit) {
    int multiples = credit % 50000;
    int response = 5;

    if(credit > 10_000_000) {
      response = 1; // Over the limit
    } else if (multiples == 0) {
      account.addBalance(credit);
      createTransaction(credit, "Deposit");
      updateBalance();
      response = 0; // Success
    } else if (multiples != 0) {
      response = 2; // Wrong multiples
    } else {
      response = 5; // Invalid Input
    }

    return response;
  }

  public int withdrawMoney(int credit) {
    int multiples = credit  % 50000;
    int response = 5;

    if(credit > 5_000_000){
      response = 1; // Over the limit
    } else if (credit > account.getBalance()) {
      response = 3; // Not enough credit
    } else if (account.getBalance() >= 50000 && multiples == 0) {
      account.substractBalance(credit);
      createTransaction(credit, "Withdraw");
      updateBalance();
      response = 0; // Success
    } else if (multiples != 0) {
      response = 2; // Wrong multiples
    } else {
      response = 5; // Invalid Input
    }

    return response;
  }

  @SuppressWarnings("unchecked")
  public int transferMoney(int credit, long accountNumber) {
    try {
      Boolean isAccountExist = false;
      Account partner = null;

      FileInputStream fileIn = new FileInputStream("dataAccount.ser");
      GZIPInputStream compressedFile = new GZIPInputStream(fileIn);
      ObjectInputStream objectIn = new ObjectInputStream(compressedFile);
      listAccount = (ArrayList<Account>) objectIn.readObject();

      fileIn.close();
      compressedFile.close();
      objectIn.close();
      
      for (Account existingAcc : listAccount) {
        if (existingAcc.getAccountNumber() == accountNumber ) {
          isAccountExist = true;
          partner = existingAcc;
          break;
        }
      }
      
      if(isAccountExist){
        if(credit >= 10000 && credit <= account.getBalance()) {
          account.substractBalance(credit);
          partner.addBalance(credit);
          listAccount.set(account.getIdAccount() - 1, account);
          createTransaction(credit, "Transfer");
          updateBalance();
          return 0; // Success
        } else {
          return 3; // Not enough credit
        }
      } 
    } catch (Exception e) {
      System.out.println(e);
    }

    return 6; // Destination account doesn't exist
  }

  public int topupEmoney(int credit, String noTelp){
    int response = 5;

    if (credit < 10000) {
      response = 2; // Minimal credit is 10000
    } else if (credit > account.getBalance()) {
      response = 1; // Not enough credit
    } else if (credit >= 10000 && credit <= account.getBalance()) {
      account.substractBalance(credit);
      createTransaction(credit, "Topup");
      updateBalance();
      response = 0; // Success
    } else {
      response = 5; // Unexpected error
    }

    return response;
  }

  @SuppressWarnings("unchecked")
  public int login(long cardNumber, int pin) {
    try {
      FileInputStream fileIn = new FileInputStream("dataAccount.ser");
      GZIPInputStream compressedFile = new GZIPInputStream(fileIn);
      ObjectInputStream objectIn = new ObjectInputStream(compressedFile);
      listAccount = (ArrayList<Account>) objectIn.readObject();
      fileIn.close();
      compressedFile.close();
      objectIn.close();

      for (Account existingAcc : listAccount) {
        if (existingAcc.getCardNumber() == cardNumber && existingAcc.getPin() == pin) {
          this.account = existingAcc;
          return 0; // Success
        }
      }

      return 1; // Wrong number or pin

    } catch (Exception e) {
      System.out.println(e);
    }

    return 2; // Unexpected error
  }

  public void logout() {
    this.account = null;
  }

  @SuppressWarnings("unchecked") 
  public int createAccount(long cardNumber, String expDate, int pin, long accountNumber, String name) {
    try {
      if(VerifyCard.checkCard(cardNumber)) {
        File f = new File("dataAccount.ser");
        Boolean isCardNumExist = false;
  
        if (f.exists()) {
          FileInputStream fileIn = new FileInputStream("dataAccount.ser");
          GZIPInputStream compressedFile = new GZIPInputStream(fileIn);
          ObjectInputStream objectIn = new ObjectInputStream(compressedFile);
          listAccount = (ArrayList<Account>) objectIn.readObject();
          fileIn.close();
          compressedFile.close();
          objectIn.close();
          
          for (Account existingAcc : listAccount) {
            if (existingAcc.getCardNumber() == cardNumber) {
              isCardNumExist = true;
              break;
            }
          }
        }
  
        if (isCardNumExist) {
          return 1; // Card is exist
        }
  
        Account acc = new Account(cardNumber, expDate, pin, accountNumber, name);
        listAccount.add(acc);
        this.account = acc;
  
        FileOutputStream fileOut = new FileOutputStream("dataAccount.ser");
        GZIPOutputStream compressedFile = new GZIPOutputStream(fileOut);
        ObjectOutputStream objectOut = new ObjectOutputStream(compressedFile);
        objectOut.writeObject(listAccount);
        objectOut.flush();
        objectOut.close();
        fileOut.close();
        compressedFile.flush();
        compressedFile.close();

        return 0;
      } else {
        return 2; // Invalid number
      }

    } catch (Exception e) {
      System.out.println(e);
    }

    return 3; // Unexpected error
  }

  @SuppressWarnings("unchecked")
  public void createTransaction(int credit, String transactionType) {
    try {
      File f = new File("dataTransaction.ser");
      
      if (f.exists()) {
        FileInputStream fileIn = new FileInputStream("dataTransaction.ser");
        GZIPInputStream compressedFile = new GZIPInputStream(fileIn);
        ObjectInputStream objectIn = new ObjectInputStream(compressedFile);
        listTransaction = (ArrayList<Transaction>) objectIn.readObject();

        fileIn.close();
        compressedFile.close();
        objectIn.close();
      }

      Transaction transaction = new Transaction(this.account, transactionType, credit);    
      latestTransaction = transaction;
      listTransaction.add(transaction);

      FileOutputStream fileOut = new FileOutputStream("dataTransaction.ser");
      GZIPOutputStream compressedFile = new GZIPOutputStream(fileOut);
      ObjectOutputStream objectOut = new ObjectOutputStream(compressedFile);

      objectOut.writeObject(listTransaction);
      objectOut.flush();
      objectOut.close();
      fileOut.close();
      compressedFile.flush();
      compressedFile.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void updateBalance() {
    try {
      FileOutputStream fileOut = new FileOutputStream("dataAccount.ser");
      GZIPOutputStream compressedFile = new GZIPOutputStream(fileOut);
      ObjectOutputStream objectOut = new ObjectOutputStream(compressedFile);

      objectOut.writeObject(listAccount);
      objectOut.flush();
      objectOut.close();
      fileOut.close();
      compressedFile.flush();
      compressedFile.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
