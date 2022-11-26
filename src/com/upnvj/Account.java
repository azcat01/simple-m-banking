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
    int setor = credit % 50000;
    if (credit < 50000 ){
      System.out.println("Minimum setoran tunai : 50.000 Rupiah");
    }else if (setor == 0){
      this.balance += credit;
      System.out.println("Setor Tunai Berhasil, Saldo Anda Sekarang : " + balance);
    }else{
      System.out.println("Masukkan Jumlah kelipatan 50.000 Rupiah!");
    }
}
  public void withdrawMoney(int credit){
    int tarik = credit  % 50000;
    if(credit > this.balance){
      System.out.println("Uang anda tidak cukup untuk withdraw dengan jumlah : " +credit );
      System.out.println("Saldo anda sekarang : "+ this.balance);
    }
    else if (this.balance < 50000){
      System.out.println("Uang Anda Kurang dari 50.000 Rupiah Untuk Melakukan Withdraw");
    }else if(this.balance >= 50000 && tarik == 0){
      this.balance -= credit;
      System.out.println("Tarik Tunai Berhasil, Saldo Anda Sekarang : "+ balance);
    }else{
      System.out.println("Masukkan jumlah kelipatan 50.000 Rupiah!");
    }
}
}

class Mains {
  public static void main(String[] args)  {
    Account abc = new Account(200000, 23039, 4893, "Indonesia", "David");
    System.out.println("Saldo Anda : "+ abc.getBalance());
    abc.saveMoney(50200);
    abc.withdrawMoney(250000);
    
  }
}