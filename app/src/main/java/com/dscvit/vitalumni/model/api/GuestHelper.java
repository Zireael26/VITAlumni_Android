package com.dscvit.vitalumni.model.api;

public class GuestHelper {
	

	private int age;	
	private String foodPreference;
	private String relationship;		
	private String name;
	
	public GuestHelper() {
		
	}

	public GuestHelper(int age, String foodPreference, String relationship, String name) {
		this.age = age;
		this.foodPreference = foodPreference;
		this.relationship = relationship;
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getFoodPreference() {
		return foodPreference;
	}

	public void setFoodPreference(String foodPreference) {
		this.foodPreference = foodPreference;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
