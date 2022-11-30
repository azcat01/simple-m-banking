package com.upnvj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.*;

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

class Main {
  public static void main(String[] args) {
    long num = Long.parseLong("341241351673932");
    long num2 = 4485703204713403L;
    long num3 = 5472158029631581L;

    Program p = new Program();
    // System.out.println(p.formatBalance(1000000));
    // System.out.println(VerifyCard.getCardType(num));
    // p.createAccount(num, "01/22", 321321, 124534, "Irsyad");
    // p.createAccount(num2, "01/24", 123123, 5394281, "Daffa");
    // p.createAccount(num3, "01/23", 213123, 2495301, "Siddi");
    p.login(num, 321321);
    // p.login(num2, 1231231);
    // p.login(num3, 213123);
    // p.topupEmoney(50000, "085695403201");
    // System.out.println(p.transferMoney(50000, 5394281));
    // p.saveMoney(50000);
    // p.saveMoney(50000);
    // p.withdrawMoney(50000);

    Account acc = p.getAccount();
    // System.out.println(acc.getBalance());
    ArrayList<Transaction> listTr = acc.getListTransaction();
    ArrayList<Account> listacc = p.getListAccount() ;
    ArrayList<Object> listObj = p.getRowTable() ;
    // System.out.println(p.getTransactionOTP());

    for (Object a : listObj) {
      System.out.println(a);
    }

    // for (Account a : listacc) {
    //   System.out.println(a.getName());
    //   System.out.println(a.getBalance());
    // }
    
    // for (Transaction tr : listTr) {
    //   System.out.println(tr.getAccount().getBalance());
    //   System.out.println(tr.getAccount().getName());
    // }
  }
}

public class Program {
  private ArrayList<Account> listAccount = new ArrayList<>();
  private ArrayList<Transaction> listTransaction = new ArrayList<>();
  private Account account;

  public Account getAccount() {
    return account;
  }

  public String formatBalance(int number) {
    NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
    String s = format.format(number) + ",-";
    return s;
  }

  public String getTransactionOTP() {
    ArrayList<Transaction> tr = account.getListTransaction();
    return tr.get(tr.size() - 1).getOTP();
  }

  public ArrayList<Account> getListAccount() {
    return listAccount;
  }

  public ArrayList<Object> getRowTable() {
    ArrayList<Object> objList = new ArrayList<>();
    ArrayList<Transaction> tr = account.getListTransaction();
    Collections.reverse(tr);
    for (Transaction t : tr) {
      objList.add(new Object[] {t.getDate(), t.getTransactionType(), Integer.toString(t.getCredit())});
    }

    return objList;
  }

  public int saveMoney(int credit) {
    int multiples = credit % 50000;
    int response = 5;

    if(credit > 10_000_000) {
      response = 1; // Not enough credits
    } else if (multiples == 0) {
      account.addBalance(credit);
      createTransaction(credit, "Deposit");
      updateBalance();
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
      response = 3; // Over the limit
    } else if (credit > account.getBalance()) {
      response = 1; // Not enough credit
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

      ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("dataAccount.ser"));
      listAccount = (ArrayList<Account>) objectIn.readObject();
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
          System.out.println("Transfer dana berhasil!");
          account.substractBalance(credit);
          partner.addBalance(credit);
          createTransaction(credit, "Transfer");
          listAccount.set(account.getIdAccount() - 1, this.account);
          updateBalance();
          return 0; // Success
        } else {
          System.out.println("Saldo anda tidak mencukupi atau nominal salah!");
          return 2; // Not enough credit
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
    } else if (credit > 10000 && credit <= account.getBalance()) {
      account.substractBalance(credit);
      createTransaction(credit, "Topup");
      listAccount.set(account.getIdAccount() - 1, this.account);
      updateBalance();
      response = 0;
    } else {
      response = 5;
    }

    return response;
  }

  @SuppressWarnings("unchecked")
  public int login(long cardNumber, int pin) {
    try {
      ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("dataAccount.ser"));
      listAccount = (ArrayList<Account>) objectIn.readObject();
      objectIn.close();

      for (Account existingAcc : listAccount) {
        if (existingAcc.getCardNumber() == cardNumber && existingAcc.getPin() == pin) {
          this.account = existingAcc;
          System.out.println("Success!");
          return 0; // Success
        }
      }
      
      System.out.println("Wrong Number or Pin!");
      return 1; // Wrong number or pin

    } catch (Exception e) {
      System.out.println(e);
    }

    return 2;
  }

  public int logout() {
    this.account = null;
    return 0;
  }

  @SuppressWarnings("unchecked") 
  public int createAccount(long cardNumber, String expDate, int pin, int accountNumber, String name) {
    try {
      if(VerifyCard.checkCard(cardNumber)) {
        File f = new File("dataAccount.ser");
        Boolean isCardNumExist = false;
  
        if (f.exists()) {
          ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("dataAccount.ser"));
          listAccount = (ArrayList<Account>) objectIn.readObject();
          objectIn.close();
          
          for (Account existingAcc : listAccount) {
            if (existingAcc.getCardNumber() == cardNumber) {
              isCardNumExist = true;
              break;
            }
          }
        }
  
        if (isCardNumExist) {
          System.out.println("Failed Exist!");
          return 1; // Card is exist
        }
  
        Account acc = new Account(cardNumber, expDate, pin, accountNumber, name);
        listAccount.add(acc);
        this.account = acc;
  
        ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("dataAccount.ser"));
        objectOut.writeObject(listAccount);
        objectOut.flush();
        objectOut.close();
        System.out.println("Success");

      } else {
        System.out.println("Failed Invalid Number!");
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
        ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("dataTransaction.ser"));
        listTransaction = (ArrayList<Transaction>) objectIn.readObject();
        objectIn.close();

      }

      Transaction transaction = new Transaction(this.account, transactionType, credit);    
      listTransaction.add(transaction);
      ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("dataTransaction.ser"));
      objectOut.writeObject(listTransaction);
      objectOut.flush();
      objectOut.close();

    } catch (Exception e) {
      System.out.println(e);
    }

  }

  public void updateBalance() {
    try {
      ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("dataAccount.ser"));
      objectOut.writeObject(listAccount);
      objectOut.flush();
      objectOut.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
