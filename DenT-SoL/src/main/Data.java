package main;


public class Data
{
  int SNo;
  
  private String pid;
  
  private String fName;
  
  private String Age;
  private String Contact;
  private String date;
  private String Gender;
  private String Address;
  
  public String getAddress()
  {
    return Address;
  }
  
  public int getSNo()
  {
    return SNo;
  }
  
  public String getPid() {
    return pid;
  }
  
  public String getfName() {
    return fName;
  }
  
  public String getAge() {
    return Age;
  }
  
  public String getContact() {
    return Contact;
  }
  
  public String getDate() {
    return date;
  }
  
  public String getGender() {
    return Gender;
  }
  
  public Data(int SNo, String pid, String fName, String Age, String Contact, String Gender, String date, String address) {
    this.SNo = SNo;
    this.pid = pid;
    this.fName = fName;
    this.Age = Age;
    this.Contact = Contact;
    this.date = date;
    this.Gender = Gender;
    Address = address;
  }
}
