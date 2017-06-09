package atm;

public class BalanceInquiry extends Transaction{
	
	public BalanceInquiry(
			int accountNumber, Screen screen, BankDatabase bankDatabase) {
		super(accountNumber, screen, bankDatabase);
	}
	
	@Override
	public void execute(){
		// get references to bank database and screen
		 BankDatabase bankDatabase = getBankDatabase();
		 Screen screen = getScreen();
		 
		// get the available balance for the account involved
		 double availableBalance = 
				 bankDatabase.getAvailableBalance(getAccountNumber());
		 
		 double totalBalance = 
				 bankDatabase.getTotalBalance(getAccountNumber());
		 
		// display the balance information on the screen
		 screen.displayMessageLine("\nBalance informations:");
		 screen.displayMessage(" - Available balance: ");
		 screen.displayDollarAmount(availableBalance);
		 screen.displayMessage("\n - Total balance:    ");
		 screen.displayDollarAmount(totalBalance);
		 screen.displayMessageLine(""); 
		 
	}// end method execute
}//end class
