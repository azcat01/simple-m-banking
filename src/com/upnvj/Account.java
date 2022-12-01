package com.upnvj;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

public class Account implements Serializable {
  private static int idGenerate = 1;
  private int idAccount;
  private int balance;
  private int pin;
  private String name;
  private String expDate;
  private long accountNumber;
  private long cardNumber;
  private ArrayList<Transaction> listTransaction = new ArrayList<>();

  public Account(long cardNumber, String expDate, int pin, long accountNumber, String name) {
    this.idAccount = idGenerate++;
    this.balance = 100_000;
    this.cardNumber = cardNumber;
    this.expDate = expDate;
    this.pin = pin;
    this.name = name;
    this.accountNumber = accountNumber;
  }

  public int getBalance() {
    return this.balance;
  }

  public long getCardNumber() {
    return this.cardNumber;
  }

  public int getPin() {
    return this.pin;
  }

  public String getName() {
    return name;
  }

  public String getExpDate() {
    return expDate;
  }

  public int getIdAccount() {
    return idAccount;
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  @SuppressWarnings("unchecked")
  public ArrayList<Transaction> getListTransaction() {
    try {
      FileInputStream fileIn = new FileInputStream("dataTransaction.ser");
      GZIPInputStream compressedFile = new GZIPInputStream(fileIn);
      ObjectInputStream objectIn = new ObjectInputStream(compressedFile);
      ArrayList<Transaction> listTransaction = (ArrayList<Transaction>) objectIn.readObject();
      fileIn.close();
      compressedFile.close();
      objectIn.close();

      for (Transaction transaction : listTransaction) {
        if (transaction.getAccount().getAccountNumber() == this.getAccountNumber()) {
          this.listTransaction.add(transaction);
        }
      }

    } catch (Exception e) { }

    return this.listTransaction;
  }

  public void addBalance(int balance) {
    this.balance += balance;
  }

  public void substractBalance(int balance) {
    this.balance -= balance;
  }
}