package atm;

public class CashDispenser {
	// the default initial number of bills in the cash dispenser
	private final static int INITIAL_COUNT = 500;
	private int count;  // number of $20 bills remaining
	
	public CashDispenser(){
		this.count = INITIAL_COUNT; // set count attribute to default
	}
	
	public void dispenseCash(int amount){
		int billsRequired = amount / 20;
		this.count -= billsRequired; // update the count of bills
	}
	
	public boolean isSufficientCashAvailable(int amount){
		int billsRequired = amount / 20;
		
		if(this.count >= billsRequired){
			return true; // enough bills available
		}else {
			return false;	// not enough bills available
		}
	
	}
}
