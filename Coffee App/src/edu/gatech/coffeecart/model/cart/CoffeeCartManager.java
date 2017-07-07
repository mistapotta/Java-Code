package edu.gatech.coffeecart.model.cart;

import android.app.Application;

public class CoffeeCartManager extends Application{
	static CoffeeCart coffeeCart;
	static PurchaseOrder activePurchaseOrder;
	 
	private static CoffeeCartManager singleton;
	    public static CoffeeCartManager getInstance() {
	        return singleton;
	    }
	    
	    @Override
	    public void onCreate() {
	        super.onCreate();
	        singleton = this;
			
	    }
	
	public static CoffeeCart getCoffeeCart() {
		if(coffeeCart == null)
			coffeeCart = new CoffeeCart();

		return coffeeCart;
	}
	
}
