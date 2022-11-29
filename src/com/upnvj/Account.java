package com.upnvj;

import java.io.Serializable;

public class Account implements Serializable {
  private String name;
  private String country;
  private String expDate;
  private long cardNumber;
  private int balance;
  private int pin;
  private static int idAccount;
  private int accountNumber;

  public Account(long cardNumber, String expDate, int pin, String country, String name) {
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

  public void addBalance(int balance) {
    this.balance += balance;
  }

  public void substractBalance(int balance) {
    this.balance -= balance;
  }
}