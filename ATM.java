package atm;

public class ATM {
	private boolean userAuthenticated; // whether user is authenticated
	private int currentAccountNumber; // current user's account number
	private Screen screen; // ATM's screen
	private Keypad keypad; // ATM's keypad
	private CashDispenser cashDispenser; // ATM's cash dispenser
	private DepositSlot depositSlot;// ATM's cdeposit slot
	private BankDatabase bankDatabase; // account information database
	
	
	// constants corresponding to main menu options
	private static final int BALANCE_INQUIRY = 1;
	private static final int WITHDRAWAL = 2;
	private static final int DEPOSIT = 3;
	private static final int EXIT = 4;
	
	// no-argument ATM constructor initializes instance variables
	public ATM(){
		this.userAuthenticated = false;// user is not authenticated to start
		this.currentAccountNumber = 0;// no current account number to start
		this.screen = new Screen();//create screen
		this.keypad = new Keypad();//create keypad
		this.cashDispenser = new CashDispenser();// create cash dispenser
		this.depositSlot = new DepositSlot();// create deposit slot
		this.bankDatabase = new BankDatabase();// create acct info database
		
	}
	
	public void run(){
		
		// welcome and authenticate user; perform transactions
		while ( true )
			 {
			 // loop while user is not yet authenticated
			 while ( !userAuthenticated )
			 {
			 screen.displayMessageLine( "\nWelcome!" );
			 authenticateUser(); // authenticate user
			 } // end while
			
			 performTransactions(); // user is now authenticated
			 userAuthenticated = false; // reset before next ATM session
			 currentAccountNumber = 0; // reset before next ATM session
			 screen.displayMessageLine( "\nThank you! Goodbye!" );
			 } // end while
	 } // end method run
		
	
	
	
	private void authenticateUser(){
		screen.displayMessage("\nPlease enter your account number: ");
		int accountNumber = keypad.getInput();// input account number
		screen.displayMessage("\nEnter with your PIN: ");
		int pin = keypad.getInput();// input PIN.
		
		// set userAuthenticated to boolean value returned by database
		userAuthenticated = 
				bankDatabase.authenticateUser(accountNumber, pin);
		
		// check whether authentication succeeded
		if(userAuthenticated){
			currentAccountNumber = accountNumber; // save user's account #
		} else {
			screen.displayMessageLine(
				"Invalid account number or PIN. Please try again");
		}
		
	}// end method authenticateUser
	
	
	private void performTransactions(){
		// local variable to store transaction currently being processed
		Transaction currentTransaction = null; 
		boolean userExited = false;// user has not chosen to exit
		
		while(!userExited){
			int mainMenuSelection = displayMainMenu();
			
			// decide how to proceed based on user's menu selection
			switch(mainMenuSelection) {
			//user chose to perform one of three transaction types
			case BALANCE_INQUIRY:
			case WITHDRAWAL:
			case DEPOSIT:
				
				// initialize as new object of chosen type
				currentTransaction =
					createTransaction(mainMenuSelection);
				
				currentTransaction.execute();
				break;
			case EXIT: // user chose to terminate session
				screen.displayMessageLine(
						"\nExiting the system...");
				userExited = true;
				break;
				default:
					screen.displayMessageLine(
							"\nYou did not enter a valid selection. Try again.");
				break;
				
				
			
			} // end switch
		} //end while
	} // end method performTransaction
	
	
	private int displayMainMenu(){
		
		screen.displayMessage("\nMain menu:");
		screen.displayMessage("1 - View my balance ");
		screen.displayMessage("2 - Withdraw cash ");
		screen.displayMessage("3 - Deposit funds ");
		screen.displayMessage("4 - Exit ");
		screen.displayMessage("Enter a choice: ");
		
		return keypad.getInput();// return user's selection
	}//end mainMenu
	
	
	private Transaction createTransaction(int type){
		Transaction temp = null; // temporary Transaction variable
		
		switch(type){
		case BALANCE_INQUIRY: // create new BalanceInquiry transaction
			temp = new BalanceInquiry(
					currentAccountNumber,screen, bankDatabase);
			break;
		case WITHDRAWAL: // create new Withdrawal transaction
			temp = new Withdrawal(currentAccountNumber,screen, bankDatabase,
					keypad, cashDispenser);
			break;
		case DEPOSIT: // create new Deposit transaction
			temp = new Deposit(currentAccountNumber,screen, bankDatabase,
					keypad, depositSlot);
			break;
		}//end switch
		
		return temp;
	}
}
