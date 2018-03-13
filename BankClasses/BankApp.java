package BankClasses;

import java.math.BigDecimal;

import accountType.Checquing;
import accountType.GIC;
import accountType.Savings;

public class BankApp extends Bank {
	
//	private static  Account[] bankAccount= {
//			new GIC( "John Doe","D1234",new BigDecimal(6000),1.5, 1),
//			new Checquing("John Doe","E5678",new BigDecimal(15000),new BigDecimal(0.75)),
//			new Savings("John Doe","F9801",new BigDecimal(8000),0.15),
//			new GIC("Mary Ryan","A1234",new BigDecimal(15000),1.5,2),
//			new Checquing("Mary Ryan","B5678",new BigDecimal(15000),new BigDecimal(0.75)),
//			new Savings("Mary Ryan","C9012",new BigDecimal(8000),0.15)
//			};
	//Individual Accounts if needed for testing
	private static GIC D1234 = new GIC( "John Doe","D1234",new BigDecimal(6000),1.5, 2);
	private static Checquing E5678 = new Checquing("John Doe","E5678",new BigDecimal(15000),new BigDecimal(0.75));
	private static Savings F9801 = new Savings("John Doe","F9801",new BigDecimal(8000),0.15);
	private static GIC A1234 = new GIC("Mary Ryan","A1234",new BigDecimal(15000),1.5,4);
	private static Checquing B5678 = new Checquing("Mary Ryan","B5678",new BigDecimal(15000),new BigDecimal(0.75));
	private static Savings C9012 = new Savings("Mary Ryan","C9012",new BigDecimal(8000),0.15);
			
	
	/**
	 * opens two Savings accounts, two Checking Accounts and two GIC accounts with the bank
	 * @param bank The bank for the accounts to be loaded into
	 * @author natan
	 */
	public static void loadBank( Bank bank )
	{
		bank.addAccount(D1234);
		bank.addAccount(E5678);
		bank.addAccount(F9801);
		bank.addAccount(A1234);
		bank.addAccount(B5678);
		bank.addAccount(C9012);

	}
	
	
	public static void main ( String args[] ) {
		Bank bank=new Bank();
		loadBank(bank);
		listAcct(bank);
	}
	
	public static void listAcct(Bank bank) {
		for(int i=0;i<bank.getAcc().size();i++)
			System.out.println(bank.getAcc().get(i)+"\n--------------");
		
	}
}
