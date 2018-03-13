package accountType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GIC extends Account {
	private double m_intrestRate;
	private int m_investmentPeriod;

	/**
	 * Constructor
	 * @param name Account Name
	 * @param num Account Number 
	 * @param bal Account Balance (Must be >=0)
	 * @param intrestRate The interest rate for the GIC
	 * @param investmentPeriod The investment period in years
	 */
	public GIC(String name, String num, BigDecimal bal,double intrestRate,int investmentPeriod) {
		super(name, num, bal);
		setM_intrestRate(intrestRate);
		setM_investmentPeriod(investmentPeriod);
		
	}

	/**
	 * Default constructor Intrest rate set to 1.25% and a 1 year investment period
	 */
	public GIC() {
		super();
		setM_intrestRate(1.25);
		setM_investmentPeriod(1);
		
	}

	/**
	 * Get the interest rate  
	 * @return
	 */
	public double getM_intrestRate() {
		return m_intrestRate;
	}

	/**
	 * Sets the current intrest rate 
	 * @param m_intrestRate to be set to
	 */
	public void setM_intrestRate(double m_intrestRate) {
		this.m_intrestRate = m_intrestRate;
	}

	/**
	 * @return  Investment period in years
	 */
	public int getM_investmentPeriod() {
		return m_investmentPeriod;
	}
	
	/**
	 * @param m_investmentPeriod set investment period in years 
	 */
	public void setM_investmentPeriod(int m_investmentPeriod) {
		this.m_investmentPeriod = m_investmentPeriod;
	}
	
	/**
	 * CANNOT USE WITH GIC WILL RETURN FALSE 
	 * @return true if successful
	 */
	@Override
	public boolean withdraw(BigDecimal amount) {
		return false;
	}
	
	/**
	 * CANNOT USE WITH GIC WILL RETURN FALSE
	 * @return true if successful
	 */
	@Override
	public boolean deposit(BigDecimal amount)
	{
		return false;
	}
	
	/**
	 * Calculate the balance at Maturity 
	 * @return the balance of the GIC account at the time of maturity
	 */
	public BigDecimal getBalanceAtMaturity( ) 
	{
		BigDecimal multi =new BigDecimal(1+((this.getM_intrestRate()/100)/1));
		return this.getAccountBalance().multiply(multi.pow(this.getM_investmentPeriod()));
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		GIC other = (GIC) obj;
		if (Double.doubleToLongBits(m_intrestRate) != Double.doubleToLongBits(other.m_intrestRate))
			return false;
		if (m_investmentPeriod != other.m_investmentPeriod)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString()+"\ntype: GIC\r\n" + 
				"annual interest rate: "+this.getM_intrestRate()+"%\r\n" + 
				"period of investment:  "+this.getM_investmentPeriod()+" years\r\n" + 
				"new balance at maturity: $"+this.getBalanceAtMaturity().setScale(2,RoundingMode.HALF_UP);
	}

}
