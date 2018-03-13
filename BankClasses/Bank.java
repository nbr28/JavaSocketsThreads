/*
 * Task : Bank.java
 * Author : Yoosuk Sim
 * ID : ysim2
 * Date : 2018/02/06
 */
package BankClasses;

import java.math.BigDecimal;
import java.util.ArrayList;


import accountType.Account;

public class Bank {
	private String m_name;
	private ArrayList<Account> m_acc;

	/**
	 * Bank Constructor
	 * @param name: Name of the Bank
	 */
	public Bank(String name)
	{ 
		m_name = name; 
		m_acc = new ArrayList<Account>(); 
	}
	
	/**
	 * Creates a new bank with the name "Seneca@York"
	 */
	public Bank() { this("Seneca@York"); }

	/**
	 * Gets the Name of the bank
	 * @return the bank name
	 * @author natan
	 */
	public String getName(){ return m_name; }
	
	/**
	 * Gets all the accounts in the bank 
	 * @return ArrayList<Account>
	 * @author natan
	 */
	public ArrayList<Account> getAcc() { return m_acc; }
	
	/**
	 * To string
	 */
	public String toString()
	{
		String s = "*** Welcome to the Bank of " + m_name + "***\nIt has " + m_acc.size() + " accounts.\n";
		for ( int i = 0 ; i < m_acc.size() ; i++ )
			s += i+1 + ". " + m_acc.get(i).toString();
		return s;
	}


	/**
	 * Adds a bank to the arraylist
	 * @param newAccount add the account
	 * @return success or fail
	 */
	public boolean addAccount( Account newAccount )
	{
		
		if ( newAccount instanceof Account ){
			//see if the account number is already used if it is return false
			if(this.searchByAccountNumber(newAccount.getAccountNumber())!=null)
				return false;
			
			
			m_acc.add(newAccount);
			return true;
		}
		//Catchall return false
		return false;
	}
	
	/**
	 * compares bank names to check they match
	 * @return if they eual 
	 */
	public boolean equals( Object rhs )
	{
		boolean state = false;
		/* make sure rhs is bank */
		if(rhs instanceof Bank){
			Bank temp = (Bank) rhs;
			state = temp.getName().equals(m_name) && temp.getAcc().equals(m_acc) ;
		}
		return state;
	}

	/**
	 * Searches by balance for an account 
	 * Source TS Modified for BigDecimal NR
	 * @param balance find all account whose balance matches the target
	 * @return list of matching account
	 */
	public ArrayList<Account> searchByBalance(BigDecimal balance)
	{
		ArrayList<Account> result = new ArrayList<Account>();
		for(int i = 0; i < m_acc.size() ; i++)
			if(balance.equals(m_acc.get(i).getAccountBalance()) ) result.add(m_acc.get(i));
		return result;
	}
	
	/**
	 * Searches by name for an account 
	 * @param accountName the account holders name
	 * @return the list of matching accounts
	 */
	public ArrayList<Account> searchByAccountName(String accountName )
	{
		ArrayList<Account> result = new ArrayList<Account>();
		for(int i = 0; i < m_acc.size() ; i++)
			if(accountName.equals(m_acc.get(i).getFullName()) ) 
				result.add(m_acc.get(i));
		return result;
	}
	
	
	/**
	 * Searches by Account number for an account 
	 * @param accountNumber the account number
	 * @return the account
	 */
	public Account searchByAccountNumber(String accountNumber )
	{
		for(int i = 0; i < m_acc.size() ; i++)
			if(accountNumber.equals(m_acc.get(i).getAccountNumber()) )
				return m_acc.get(i);
		return null;
	}
	
	/**
	 * removes a particular account from the Bank object
	 * @param accountNumber the account number
	 * @return It returns null if the particular account does not exist or the value of the parameter is a null reference.
	 */
	public Account removeAccount( String accountNumber )
	{
		Account Temp= this.searchByAccountNumber(accountNumber);
		if(this.getAcc().remove(Temp))
			return Temp;
		else
			return null;
	}
	
	public static void PrintAccounts(ArrayList<Account> accounts) {
		for(int i=0;i<accounts.size();i++)
			System.out.println(accounts.get(i)+"\n--------------");
	}
	

};
