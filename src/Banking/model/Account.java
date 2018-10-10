package Banking.model;

public class Account {
	
	private int account_id;
	private String name, address;
	
	public Account(int account_id, String name, String address) {
		this.account_id = account_id;
		this.name = name;
		this.address = address;
	}

	public int getAccount_id() {
		return account_id;
	}

	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
