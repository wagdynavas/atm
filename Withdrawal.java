package atm;


//Class Withdrawal represents an ATM withdrawal transaction
public class Withdrawal extends Transaction{

	private int amount;// amount to withdraw
	private Keypad keypad;// reference to keypad 
	private CashDispenser cashDispenser; // reference to cash dispenser
	
	// constant corresponding to menu option to cancel
	private final static int CANCELED = 6;
	
	// Withdrawal constructor
	public Withdrawal(int accountNumber, Screen screen, 
			BankDatabase bankDatabase, Keypad keypad, 
			CashDispenser cashDispenser){
		super(accountNumber, screen, bankDatabase);
		this.keypad = keypad;
		this.cashDispenser = cashDispenser;
		
	}// end  Withdrawal constructor
	
	@Override
	public void execute(){
		boolean cashDispensed = false; //cash wasn't dispensed yet
		double availableBalance; // amount available for withdrawal4
		
		// get references to bank database and screen
		BankDatabase bankDatabase = getBankDatabase();
		Screen screen = getScreen();
		
		// loop until cash is dispensed or the user cancels
		do{
			// obtain a chosen withdrawal amount from the user
			amount = displayMenuOfAmounts();
			

			
		  if(amount != CANCELED){
				availableBalance =
						bankDatabase.getAvailableBalance(getAccountNumber());
				
			// check whether the user has enough money in the account
			if(amount <= availableBalance){
				// check whether the cash dispenser has enough money
				if(cashDispenser.isSufficientCashAvailable(amount)){
				
				cashDispenser.dispenseCash(amount);// dispense cash
				cashDispensed = true;
				
				// instruct user to take cash
				screen.displayMessageLine("\nYour cash has been"
						+ "dispensed. Please take your cash now.");
				
				//debit amount from the total available.
				bankDatabase.debit(getAccountNumber(), amount);
			
				}else {// cash dispenser does not have enough cash
				screen.displayMessageLine("\nInsufficient cash available"
						+ "in the ATM. Please choose a smaller amount.");
				}
		    }else {// not enough money available in user's account
		    	screen.displayMessageLine("\nInsufficient funds in your account"
		    			+ "\n\nPlease choose a smaller amount.");
		    }
		  }else {// user chose cancel menu option
			  screen.displayMessageLine("\nCanceling transaction...");
			  return; // return to main menu because user canceled
		  }
		}while(!cashDispensed);
	}//end method execute
	
	
	// display a menu of withdrawal amounts and the option to cancel;
	// return the chosen amount or 0 if the user chooses to cancel
	private int displayMenuOfAmounts(){
		
		int userChoice = 0; // local variable to store return value
		
		Screen screen = getScreen();
		
		// array of amounts to correspond to menu numbers
		int[] amounts = {0,20,40,60,100,200};
		
		// loop while no valid choice has been made
		while(userChoice == 0){
			// display the withdrawal menu
			screen.displayMessageLine("\nWithdrawal Menu:");
			screen.displayMessageLine("1 - $20");
			screen.displayMessageLine("2 - $40");
			screen.displayMessageLine("3 - $60");
			screen.displayMessageLine("4 - $100");
			screen.displayMessageLine("5 - $200");
			screen.displayMessageLine("6 - Cancel transection");
			screen.displayMessage("\nChoose a withdrawal amount:");
			
			int input = keypad.getInput();// get user input through keypad
			
			// determine how to proceed based on the input value
			switch(input){
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			userChoice = amounts[input];
			break;
			case CANCELED:// the user chose to cancel
				userChoice = CANCELED;// save user's choice
				break;
				default:
					screen.displayMessageLine("\nInvalid selection. Try again.");
			}//end switch
		}//end while
		
		return userChoice;
	}
}// end class Withdrawal
