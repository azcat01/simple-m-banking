package com.upnvj;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Transaction implements Serializable {
  private static int idGenerator = 1;
  private int idTransaction;
  private int credit;
  private String OTP = generateOTP();
  private Account account;
  private String transactionType;
  private LocalDateTime date = LocalDateTime.now();

  public Transaction(Account account, String transactionType, int credit) {
    this.idTransaction = idGenerator++;
    this.account = account;
    this.transactionType = transactionType;
    this.credit = credit;
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

  public int getIdTransaction() {
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