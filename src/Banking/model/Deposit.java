package Banking.model;

public class Deposit {
	
	private int deposit_id, account_id;
	private int amount;
	private String date;
	
	public Deposit(int account_id, int deposit_id, int amount, String date) {
		this.deposit_id = deposit_id;
		this.amount = amount;
		this.date = date;
	}

	public int getDeposit_id() {
		return deposit_id;
	}

	public void setDeposit_id(int deposit_id) {
		this.deposit_id = deposit_id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
