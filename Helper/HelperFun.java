package Helper;

import java.util.Scanner;

public class HelperFun {
	private Scanner sn=new Scanner(System.in);;
	public String getString()
	{
		return sn.nextLine();
	}
	public void closeScanner()
	{
		sn.close();
	}
	private void flushScanner()
	{
		
	}
}
