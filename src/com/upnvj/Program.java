package com.upnvj;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    long num = 341241351673932L;
    long num2 = 341241351673931L;
    long num3 = 341241351673930L;
    System.out.println(num);
    System.out.println(VerifyCard.checkCard(num));
    System.out.println(VerifyCard.getCardType(num));

    Program p = new Program();
    // p.createAccount(num, "01/22", 321321, "Indonesia", "Irsyad");
    // p.createAccount(num2, "01/24", 123123, "Indonesia", "Daffa");
    // p.createAccount(num3, "01/23", 213123, "Indonesia", "Siddi");
    p.login();
    // Account acc = p.getAccount();
    // System.out.println(acc.getName());
  }
}

public class Program {
  private ArrayList<Account> listAccount = new ArrayList<>();
  private Account account;
  private Window window;
  private String OTP;

  public Account getAccount() {
    return account;
  }

  public String getOTP() {
    return OTP;
  }
  
  public String generateOTP() {
    Random otp = new Random();
    Integer code = 1000 + otp.nextInt(8999);
    return code.toString();
  }

  public String saveMoney(int credit) {
    int multiples = credit % 50000;
    String response = "";

    if(credit > 10_000_000) {
      response = "Anda melebihi maksimum setoran tunai! Maksimum : Rp5.000.000";
    } else if (credit < 50000 ) {
      response = "Minimum setoran tunai : 50.000 Rupiah";
    } else if (multiples == 0) {
      account.addBalance(credit);
      OTP = generateOTP();
      response = "Permintaan Setor Tunai Berhasil!";
    } else if (multiples != 0){
      response = "Masukkan Jumlah kelipatan 50.000 Rupiah!";
    } else {
      response = "Masukkan nominal rupiah dengan angka!";
    }

    return response;
  }

  public String withdrawMoney(int credit) {
    int multiples = credit  % 50000;
    String response = "";

    if(credit > 5_000_000){
      response = "Anda melebihi Maksimum Penarikan Uang! Maksimum : Rp5.000.000";
    } else if (credit > account.getBalance()) {
      response = "Uang anda tidak cukup untuk withdraw dengan jumlah : "  + credit;
    } else if (account.getBalance() < 50000){
      response = "Uang Anda Kurang dari 50.000 Rupiah Untuk Melakukan Withdraw";
    } else if (account.getBalance() >= 50000 && multiples == 0) {
      account.substractBalance(credit);;
      OTP = generateOTP();
      response = "Tarik Tunai Berhasil!";
    } else if (multiples != 0) {
      response = "Masukkan jumlah kelipatan 50.000 Rupiah!";
    } else {
      response = "Masukkan nominal rupiah dengan angka!";
    }

    return response;
  
  }

  public Boolean  verifyAccount(long cardNumber, int pin) {
    
    return true;
  }

  @SuppressWarnings("unchecked") 
  public void createAccount(long cardNumber, String expDate, int pin, String country, String name) {
    try {

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
        return;
      }

      Account acc = new Account(cardNumber, expDate, pin, country, name);
      listAccount.add(acc);
      this.account = acc;

      ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("dataAccount.ser"));
      objectOut.writeObject(listAccount);
      objectOut.flush();
      objectOut.close();

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  @SuppressWarnings("unchecked")
  public void login() {
    try {
      ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream("dataAccount.ser"));
      this.listAccount = (ArrayList<Account>) objectIn.readObject();
      this.account = listAccount.get(1);
      System.out.println(account.getName());
      System.out.println(listAccount);

      objectIn.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
