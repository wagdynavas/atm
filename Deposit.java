package atm;


public class Deposit extends Transaction{
	private double amount;
	private Keypad keypad;
	private DepositSlot depositSlot;
	private final static int CANCELED = 0; // constant for cancel option
	
	public Deposit(int accountNumber, Screen screen, 
			BankDatabase bankDatabase, Keypad keypad,
			DepositSlot depositSlot){
		super(accountNumber, screen, bankDatabase);
		this.keypad = keypad;
		this.depositSlot = depositSlot;
	}
	
	@Override
	public void execute(){
		BankDatabase bankDatabase = getBankDatabase();
		Screen screen = getScreen();
		
		amount = promptForDepositAmount(); // get deposit amount from user
		
		//check whether user entered a deposit amount or canceled
		if(amount != CANCELED){
			// request deposit envelope containing specified amount
			screen.displayMessage("\nPlease enter a deposit envelop containing");
			screen.displayDollarAmount(amount);
			screen.displayMessageLine(".");
			
			// receive deposit envelope
			boolean envelopReceived = depositSlot.isEnvelopeReceived();
			
			// check whether deposit envelope was received
			if(envelopReceived){
				screen.displayMessageLine("\nYour envelop has been received"
						+ "\nNOTE: The money jus deposited will not be available"
						+ "until we verify the amount of any enclosed cash"
						+ "and your checks clear.");
				
				// credit account to reflect the deposit
				bankDatabase.credit(getAccountNumber(), amount);
			}else {// deposit envelope not received
				screen.displayMessageLine("You did not insert an envelop"
						+ ", so the ATM has canceled your transection.");
			}
			
		}else {// user canceled instead of entering amount
			screen.displayMessageLine("Canceling transection...");
		}
	}
	
	private double promptForDepositAmount(){
		Screen screen = getScreen();
		
		screen.displayMessage("\nPlease enter a deposit amount in CENTS"
				+ "(or 0 to cancel): ");
		
		int input = keypad.getInput();
		
		// check whether the user canceled or entered a valid amount
		if(input == CANCELED){
			return CANCELED;
		}else {
			return (double) input / 100;  // return dollar amount
		}
	}// end method promptForDepositAmount
}
