package com.upnvj;

import java.io.Serializable;

public class Account implements Serializable {
  private static int idGenerate = 1;
  private int idAccount;
  private int balance;
  private int pin;
  private int accountNumber;
  private String name;
  private String country;
  private String expDate;
  private long cardNumber;

  public Account(long cardNumber, String expDate, int pin, String country, String name) {
    this.idAccount = idGenerate++;
    this.balance = 100_000;
    this.cardNumber = cardNumber;
    this.expDate = expDate;
    this.pin = pin;
    this.country = country;
    this.name = name;
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

  public String getCountry() {
    return country;
  }

  public String getExpDate() {
    return expDate;
  }

  public int getIdAccount() {
    return idAccount;
  }

  public void addBalance(int balance) {
    this.balance += balance;
  }

  public void substractBalance(int balance) {
    this.balance -= balance;
  }
}