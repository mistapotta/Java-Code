package edu.gatech.coffeecart.model.cart;

public enum Discount {
	
	FULL_PRICE (0),
	HALF_PRIcE (50),
	FREE (100);
	
	int percentOff = 0;
	Discount(int percentOff) {
		this.percentOff = percentOff;
	}
}