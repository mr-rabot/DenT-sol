package main;

public class Data {
	private String Address;

	private String Age;

	private String Contact;

	private String date;
	private String fName;
	private String Gender;
	private String pid;
	int SNo;

	public Data(int SNo, String pid, String fName, String Age, String Contact, String Gender, String date,
			String address) {
		this.SNo = SNo;
		this.pid = pid;
		this.fName = fName;
		this.Age = Age;
		this.Contact = Contact;
		this.date = date;
		this.Gender = Gender;
		Address = address;
	}

	public String getAddress() {
		return Address;
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

	public String getfName() {
		return fName;
	}

	public String getGender() {
		return Gender;
	}

	public String getPid() {
		return pid;
	}

	public int getSNo() {
		return SNo;
	}
}
