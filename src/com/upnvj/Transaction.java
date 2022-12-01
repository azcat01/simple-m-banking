package com.upnvj;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class Transaction implements Serializable {
  private String idTransaction;
  private int credit;
  private String OTP;
  private Account account;
  private String transactionType;
  private LocalDateTime date = LocalDateTime.now();

  public Transaction(Account account, String transactionType, int credit) {
    this.idTransaction = UUID.randomUUID().toString();
    this.account = account;
    this.transactionType = transactionType;
    this.credit = credit;
    this.OTP = generateOTP();
  }

  public String getDate() {
    DateTimeFormatter formatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String formattedDate = date.format(formatObj);
    return formattedDate;
  }

  public Account getAccount() {
    return account;
  }

  public int getCredit() {
    return credit;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public String getIdTransaction() {
    return idTransaction;
  }

  public String getOTP() {
    return OTP;
  }

  public String generateOTP() {
    Random otp = new Random();
    Integer code = 1000 + otp.nextInt(8999);
    return code.toString();
  }
}