package edu.gatech.coffeecart.model.util;

public class Money {
	float value;

	public Money(float value) {
		this.value = value;
	}
	
	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Money [value=" + value + "]";
	}
}
