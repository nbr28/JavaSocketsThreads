package accountType;

import java.math.BigDecimal;

public class NegitveAmountException extends RuntimeException
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal amount;
	   
	   public NegitveAmountException(BigDecimal amount) {
	      this.amount = amount;
	   }
	   
	   public BigDecimal getAmount() {
	      return amount;
	   }
}
