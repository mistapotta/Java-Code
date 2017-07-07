package edu.gatech.coffeecart;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;


/**
 * A class to represent customer objects.  It keeps track of a customer's VIP number, first and last names, 
 * phone number, and gold status.
 * 
 * Ideally this class should receive input from the database when the preferred constructor (long num) is called
 * and save the changed parameters in each mutator method.
 * 
 * @author Team 22 (Potter/Raju/Ramos/Sapkota)
 * 
 */
public class Customer 
{

	//used to generate new VIP numbers per instance.
	private static long nextAvailableVIPNumber = 10000l;
	
	//customer's VIP number
	private long vipNumber;
	
	//customer's first and last name;
	private String firstName;
	private String lastName;
	
	//customer's phone number
	private long phoneNumber;
	
	//customer's gold status
	private boolean isGold;
	
	//customer's birthdate
	private Date birthdate;
	
	
	/**
	 * Default constructor for customer.  This constructor shouldn't be used after debugging
	 * 
	 */
	public Customer()
	{
		vipNumber = nextAvailableVIPNumber++;
		firstName = "Default";
		lastName = "Customer";
		phoneNumber = 2127365000l;
		setGold();
		Calendar d = Calendar.getInstance();
		d.set(1969,8,31);
		birthdate = d.getTime();
		
	}
		
	/**
	 * Full parameter customer constructor.  Each instance variable can be specified.  This is used by DatabaseHelper
	 * to creat Customer objects to be manipulated by the program.
	 * 
	 * @param num The customer's VIP number
	 * @param fname The customer's First Name
	 * @param lname The customer's Last Name
	 * @param phone The customer's phone number
	 * @param gold The customer's Gold status
	 * 
	 */
	public Customer (long num, String fname, String lname, long phone, boolean gold, Date date)
	{
		vipNumber = num;
		firstName = fname;
		lastName = lname;
		phoneNumber = phone;
		setGold();
		birthdate = date;
	}
	
	public static long getNewCustomerVIPNumber()
	{
		return nextAvailableVIPNumber++;
	}
	
	/**
	 * Accessor for vipNumber
	 * 
	 * @return VIP Account Number
	 * 
	 */
	public long getVIP()
	{
		return vipNumber;
	}
	
	/**
	 * Accessor for First Name
	 * 
	 * @return Customer's first name
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Accessor for Last Name
	 * 
	 * @return Customer's last name
	 */
	public String getLastName()
	{
		return lastName;
	}
	
	/**
	 * Accessor for customer's formatted name
	 * 
	 * @return Customer's first and last name.
	 */
	public String getFormattedName()
	{
		return firstName + " " + lastName;
	}
	
	/**
	 * Accessor for customer's phone number
	 * 
	 * @return Customer's phone number
	 */
	public long getPhone()
	{
		return phoneNumber;
	}
	
	/**
	 * Accessor for customer's formatted phone number
	 * 
	 * @return Customer's phone number with area code and prefix separated.
	 */
	public String getFormattedPhone()
	{
		return "("+phoneNumber/10000000+") "+(phoneNumber/10000%100000000)+"-"+phoneNumber%10000;
	}
	
	/**
	 * Accessor for customer's gold state
	 * 
	 * @return if a customer is gold
	 */
	public boolean isGold()
	{
		return isGold;
	}

	/**
	 * Accessor for customer's birthdate
	 * 
	 * @return customer's birthdate
	 */
	public Date getBirthdate()
	{
		return birthdate;
	}
	/**
	 * Mutator for vipNumber
	 * 
	 * NOTE: Since vipNumbers are the search index for customers, this method shouldn't be used.
	 * It is just included for completeness.
	 * 
	 * @param VIP The customer's new VIP number
	 * 
	 */
	public void setVIP(long VIP)
	{
		vipNumber = (long)(VIP);
	}
	
	///TODO: Add DB hooks for the following mutators
	/**
	 * Mutator for First Name
	 * 
	 * It should be rewritten so that each state variable sets its value to the database 
	 * in addition to the state variables.
	 * 
	 * @param fname The customer's new first name
	 */
	public void setFirstName(String fname)
	{
		firstName = fname;
	}

	/**
	 * Mutator for Last Name
	 * 
	 * It should be rewritten so that each state variable sets its value to the database 
	 * in addition to the state variables.
	 * 
	 * @param lname The customer's new last name
	 */
	public void setLastName(String lname)
	{
		lastName = lname;
	}

	/**
	 * Mutator for customer's phone number
	 * 
	 * It should be rewritten so that each state variable sets its value to the database 
	 * in addition to the state variables.
	 * 
	 * @param phnum The customer's new phone number 
	 */
	public void setPhone(long phnum)
	{
		phoneNumber = (long)(phnum);
	}
	

	/**
	 * Mutator for customer's gold state
	 * 
	 * It should be rewritten so that each state variable sets its value to the database 
	 * in addition to the state variables.
	 * 
	 * @param gold The customer's new gold state
	 */
	public void setGold()
	{ 
		List<Purchase> purchases = DatabaseHelper.getPurchases(vipNumber);
		BigDecimal totalSpent = new BigDecimal("0.0");
		BigDecimal spentLast30Days = new BigDecimal("0.0");
		for (Purchase purchase: purchases)
		{
			totalSpent.add(purchase.getCost().round(new MathContext(0)));
			if (purchase.withinPast30Days())
				spentLast30Days.add(purchase.getCost().round(new MathContext(0)));
		}
		isGold = totalSpent.compareTo(new BigDecimal("5000")) > 0 || spentLast30Days.compareTo(new BigDecimal("500")) > 0;
	}
	
	/**
	 * Mutator for customer's birthdate
	 * 
	 * It should be rewritten so that each state variable sets its value to the database 
	 * in addition to the state variables.
	 * 
	 * @param date The customer's new birthdate
	 */
	public void setBirthdate(Date date)
	{
		birthdate = date;
	}
	
	/**
	 * Determine all purchases within the last 30 days
	 * 
	 * @return A List of Purchases within the past 30 days.
	 */
	public List<Purchase> summaryPurchasesLast30Days()
	{
		List<Purchase> purchases = DatabaseHelper.getPurchases(vipNumber);
		List<Purchase> recentPurchases = new LinkedList<Purchase>();
		for (Purchase purchase: purchases)
		{
			if (purchase.withinPast30Days())
				recentPurchases.add(purchase);
		}
		return recentPurchases;
	}
	
	/**
	 * Determine all purchases over the lifetime of the account.
	 * 
	 * @return A List of Purchases over the lifetime of the account.
	 */
	public List<Purchase> summaryPurchasesLifetime()
	{
		List<Purchase> purchases = DatabaseHelper.getPurchases(vipNumber);
		return purchases;
	}
	
	
}
