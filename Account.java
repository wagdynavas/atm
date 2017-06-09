package atm;

public class Account {
	private int accountNumber;
	private int pin;
	private double availableBalance;
	private double totalBalance;
	
	
	public Account(){
		
	}
	
	
	
	public Account(int accountNumber, int pin,
			double availableBalance, double totalBalance) {
		super();
		this.accountNumber = accountNumber;
		this.pin = pin;
		this.availableBalance = availableBalance;
		this.totalBalance = totalBalance;
	}



	public int getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}


	public int getPin() {
		return pin;
	}


	public void setPin(int pin) {
		this.pin = pin;
	}


	public double getAvailableBalance() {
		return availableBalance;
	}


	public void setAvailableBalance(double availableBalance) {
		this.availableBalance = availableBalance;
	}


	public double getTotalBalance() {
		return totalBalance;
	}


	public void setTotalBalance(double totalBalance) {
		this.totalBalance = totalBalance;
	}
				/*End Getters and Setters*/
	

	// determines whether a user-specified PIN matches PIN in Account
	public boolean validatePin(int userPIN){
		if(userPIN == this.pin) {
			return true;
		}else {
			return false;
		}
		
	}// end method validatePIN
	
	public void credit(double amount){
		this.totalBalance += amount; // add to total balance
	}
	
	public void debit(double amount){
		this.availableBalance -= amount; // subtract from available balance
		totalBalance -= amount; // subtract from total balance
	}
	
}
