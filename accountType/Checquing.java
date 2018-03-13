package accountType;

import java.math.BigDecimal;
import java.util.ArrayList;

import accountType.Account;

/**
 * Checquing account 
 * @author natan
 *
 */
public class Checquing extends Account 
{
	private BigDecimal m_serviceCharge;
	private ArrayList<BigDecimal> m_transLog=new ArrayList<BigDecimal>();
	private int m_serviceFeesApplied=0;
	
	/**
	 * Default constructor service charge set to $0.25
	 */
	public Checquing() {
		super();
		setM_serviceCharge(new BigDecimal(0.25));
	}

	/**
	 * Constructor
	 * @param name Account Name
	 * @param num Account Number
	 * @param bal Account Balance MUST be greater than 0
	 * @param serviceCharge the charge to be added to each transaction
	 */
	public Checquing(String name, String num, BigDecimal bal,BigDecimal serviceCharge) {
		super(name, num, bal);
		this.setM_serviceCharge(serviceCharge);
	}
	
	
	public BigDecimal getM_serviceCharge() {
		return m_serviceCharge;
	}
	
	/**
	 * Sets the service charge for the checking account
	 * @param m_serviceCharge the amount to be charged 
	 * @author natan
	 */
	public void setM_serviceCharge(BigDecimal m_serviceCharge) {
		this.m_serviceCharge = m_serviceCharge;
		
	}
	
	/**
	 * Overridden withdraw for checking accounts adds service charge automatically
	 * Throws Error if amount is negative
	 * @param amount the amount to be withdrawn from the account 
	 * @return true if successful
	 * @author natan
	 */
	@Override
	public boolean withdraw(BigDecimal amount) {
		if( super.withdraw(amount.add(this.getM_serviceCharge())))
		{
			this.m_transLog.add(amount.add(this.getM_serviceCharge()));
			m_serviceFeesApplied++;
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Overridden deposit for checking accounts 
	 * Throws Error if Amount is negative
	 * @param amount the amount to be deposited into the account 
	 * @return true if successful
	 * @author natan
	 */
	@Override
	public boolean deposit(BigDecimal amount)
	{
		if( super.deposit(amount))
		{
			this.m_transLog.add(amount);
			return true;
		}
		else
			return false;
	}

	/**
	 * @author natan
	 */
	@Override
	public String toString() {
		return super.toString() +"\ntype: CHEQUING\r\n" + 
				"service charge: $"+this.getM_serviceCharge()+"\r\n" + 
				"number of transactions: "+this.m_transLog.size()+"\r\n"+
				"total amount of service charge: $"+this.getM_serviceCharge().multiply(new BigDecimal(this.m_serviceFeesApplied));
	}

	
	/**
	 * Equals
	 * @author natan
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Checquing other = (Checquing) obj;
		if (m_serviceCharge == null) {
			if (other.m_serviceCharge != null)
				return false;
		} else if (!m_serviceCharge.equals(other.m_serviceCharge))
			return false;
		if (m_transLog == null) {
			if (other.m_transLog != null)
				return false;
		} else if (!m_transLog.equals(other.m_transLog))
			return false;
		return true;
	}
	
	

}
