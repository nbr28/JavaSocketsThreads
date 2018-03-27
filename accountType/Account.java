package accountType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import accountType.NegitveAmountException;


/*
 * Task : Account java
 * Author : Yoosuk Sim and Natan Ross
 * ID : ysim2 nross9
 * Date:
 */
public class Account implements Serializable{
	private String m_name;
	private String m_accountNum;
	private BigDecimal m_startingBalance;
	private BigDecimal m_balance;
	
	private static final long serialVersionUID = 1234567L;
	
	
	
	/**
	 * Creates a new account 
	 * @param name Name of the Account 
	 * @param num Account Number
	 * @param bal Starting Balance of the Account cannot be negitive
	 */
	public Account( String name , String num , BigDecimal bal )
	{
		setStartingAccountBalance(bal);
		setAccountBalance(bal);
		setAccountNumber(num);
		setFullName(name);
	};

	/**
	 * Creates a blank account 
	 */
	public Account() { this(null,null,new BigDecimal(0)); };

	/**
	 * 
	 */
	public String toString () 
	{
		return "number: " + this.getAccountNumber() + ", name: " + this.getFullName() + "\nstarting balance: $" + this.getStartingAccountBalance().setScale(2,RoundingMode.HALF_UP) + ", current balance: $"+this.getAccountBalance().setScale(2,RoundingMode.HALF_UP);
	};
	
	/**
	 * Sets the account Number
	 * @param num Sets the account Number
	 */
	public void setAccountNumber ( String num ){
		m_accountNum = "";
		if(num != null) m_accountNum = num;
	};
	
	/**
	 * Sets the starting account balance (NR)
	 * @param bal the balance to be set to
	 */
	public void setStartingAccountBalance ( BigDecimal bal ){
		Account.CheckNegitive(bal);//Will throw an exception if negative
		//m_accountBal = new BigDecimal(0);
		m_startingBalance = bal;
	};
	
	/**
	 * Sets the account balance
	 * @param bal the balance to be set to
	 */
	public void setAccountBalance ( BigDecimal bal ){
		Account.CheckNegitive(bal);
		m_balance = bal;
	};
	
	/**
	 * Sets the Full name of the account
	 * @param name the new name
	 */
	public void setFullName ( String name)
	{
		m_name = "";
		if(name != null) m_name=name;
	};

	/**
	 * 
	 * @return The account Number
	 */
	public String getAccountNumber () { return m_accountNum; };
	
	/**
	 * 
	 * @return the account holders full name
	 */
	public String getFullName() { return m_name; };
	
	/**
	 *  
	 * @return the account starting balance (NR)
	 */
	public BigDecimal getStartingAccountBalance () { return m_startingBalance; };
	
	/**
	 * 
	 * @return the current balance
	 */
	public BigDecimal getAccountBalance () { return m_balance; };
	
/**
 * Deposits money into the account. (NR)
 * @param amount The amount to add
 * @return True if Successful 
 */
	public boolean deposit(BigDecimal amount)
	{
		try {
			Account.CheckNegitive(amount);
		}
		catch (NegitveAmountException e) {
			return false;
		}
		
		this.setAccountBalance(this.getAccountBalance().add(amount));
		return true;		
	}
	
	/**
	 * Withdraws money into the account. (NR)
	 * @param amount The amount to remove
	 * @return True if Successful 
	 */
	public boolean withdraw(BigDecimal amount)
	{
		try
		{
			Account.CheckNegitive(amount);
		}
		catch (NegitveAmountException e) {
			return false;
		}
		
		
		if(amount.compareTo(this.getAccountBalance())<=0)
		{
			this.setAccountBalance(this.getAccountBalance().subtract(amount));
			return true;
		}
		return false;
	}

	/**
	 * If the account name, balance and number all match
	 */
	public boolean equals( Object rhs ) 
	{
		boolean result = false;
		if ( rhs instanceof Account ) {
			Account temp = (Account) rhs ;
			result = (
					temp.getFullName().equals(m_name) 
					&& temp.getAccountNumber().equals(m_accountNum)
					&& temp.getAccountBalance()==m_startingBalance
					);
		}
		return result;
	};
	
	/**
	 * Checks to see if an amount is negative and throws an amount negative exception if it is (NR)
	 * @param amount the amount to check
	 */
	public static void CheckNegitive(BigDecimal amount) throws NegitveAmountException
	{
		if(amount.signum()==-1)
		{
			throw new NegitveAmountException(amount);
		}
	}
}
