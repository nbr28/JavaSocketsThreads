package accountType;
import java.math.BigDecimal;

/**
 * 
 */

/**
 * @author natan
 *
 */
public class Savings extends Account {
	
	private double m_intrestRate;


	/**
	 * Constructor
	 * @param name Account name
	 * @param num Account Number
	 * @param bal Account starting Balance
	 * @param intrestRate
	 */
	public Savings(String name, String num, BigDecimal bal,double intrestRate) {
		super(name, num, bal);
		setM_intrestRate(intrestRate);
	}

	/**
	 * Default Constructor set interest rate to %0.3
	 */
	public Savings() {this(null,null,new BigDecimal(0),0.3); };
	
	
	/**
	 * Gets the current interest rate of the account 
	 * @return the interest rate
	 */
	public double getM_intrestRate() {
		return m_intrestRate;
	}
	
	/**
	 * Sets the interest rate of the savings account
	 * @param m_intrestRate The interest rate to be set in %
	 */
	public void setM_intrestRate(double m_intrestRate) {
		this.m_intrestRate = m_intrestRate;
	}
	
	/**
	 * The String includes the super class
	 */
	public  String toString(){
		return super.toString()+"\ntype: SAVINGS\nannual interest rate: "+this.getM_intrestRate()+"%";
	}

	/**
	 * Equals override for Savings Account
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Savings other = (Savings) obj;
		if (Double.doubleToLongBits(m_intrestRate) != Double.doubleToLongBits(other.m_intrestRate)) {
			return false;
		}
		return true;
	}
	

}
