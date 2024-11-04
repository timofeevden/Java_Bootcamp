package edu.school21.reflection.classes;

import java.util.StringJoiner;

public class Car {
	private String brand;
	private String modelName;
	private int price;

	public Car() {
       this.brand = "Default brand name";
       this.modelName = "Default brand name";
       this.price = 1000;
	}

	public Car(String brand, String modelName, int price) {
       this.brand = brand;
       this.modelName = modelName;
       this.price = price;
	}

	public double addToPrice(int addition) {
       this.price += addition;
       return price;
	}
   
	public void DoBeep() {
		System.out.println("Car: Beep!");
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Car.class.getSimpleName() + "[", "]")
					.add("brand='" + brand + "'")
					.add("modelName='" + modelName + "'")
					.add("price=" + price)
					.toString();
	}
}
