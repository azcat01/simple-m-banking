package com.upnvj;

public class Account {
  private String name;
  private long cardNumber;
  private int balance;
  private int pin;
  private String country;
  private int credit;

  public Account(int balance, long cardNumber,int pin,String country, String name) {
    this.balance = balance;
    this.cardNumber = cardNumber;
    this.pin = pin;
    this.country = country;
    this.name = name;
  }

  public int getBalance(){
    return this.balance;
  }

  public void saveMoney(int credit){
    this.balance += credit;
    System.out.println("Setor Tunai Berhasil, Saldo Anda Sekarang : " + balance);
    
  }

  public void withdrawMoney(int credit){
    
    this.balance -= credit;
    System.out.println("Tarik Tunai Berhasil, Saldo Anda Sekarang : "+ balance);
  }

}

class Mains {
  public static void main(String[] args)  {
    Account abc = new Account(50000, 23039, 4893, "Indonesia", "David");
    System.out.println("Saldo Anda : "+ abc.getBalance());
    abc.saveMoney(5000);
    abc.withdrawMoney(25000);
    
  }
}