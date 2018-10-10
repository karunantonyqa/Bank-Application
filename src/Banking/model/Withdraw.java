package Banking.model;

public class Withdraw {
	
	int account_id, withdraw_id, amount;
	String date;
	
	public Withdraw(int account_id, int withdraw_id, int amount, String date) {
		this.withdraw_id = withdraw_id;
		this.amount = amount;
		this.date = date;
	}

	public int getWithdraw_id() {
		return withdraw_id;
	}

	public void setWithdraw_id(int withdraw_id) {
		this.withdraw_id = withdraw_id;
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
