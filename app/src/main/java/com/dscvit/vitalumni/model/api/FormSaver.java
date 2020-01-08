package com.dscvit.vitalumni.model.api;

import java.util.List;

public class FormSaver {
	
	private String dateOfBirth;
	private boolean isEntrepreneur;
	private String companyName;
	private String busType;
	private String companyAddress;
	private String natureOfBusiness;
	private String companyWebsite;
	private long mobileNumber;
	private long mobileNumberFor;
	private String emailId;
	private String school;
	private String fbid;
	private String linkId;
	private String twitterId;
	private String gender;
	private String foodPref;
	private String maritalStatus;
	private String mariedDate;
	private List<GuestHelper> guestList;
	private boolean isBusNeeded;
	private int travellersCount;

	public FormSaver() {

	}

	public FormSaver(String dateOfBirth, boolean isEntrepreneur, String companyName, String busType, String companyAddress, String natureOfBusiness, String companyWebsite, long mobileNumber, long mobileNumberFor, String emailId, String school, String fbid, String linkId, String twitterId, String gender, String foodPref, String maritalStatus, String mariedDate, List<GuestHelper> guestList, boolean isBusNeeded, int travellersCount) {
		this.dateOfBirth = dateOfBirth;
		this.isEntrepreneur = isEntrepreneur;
		this.companyName = companyName;
		this.busType = busType;
		this.companyAddress = companyAddress;
		this.natureOfBusiness = natureOfBusiness;
		this.companyWebsite = companyWebsite;
		this.mobileNumber = mobileNumber;
		this.mobileNumberFor = mobileNumberFor;
		this.emailId = emailId;
		this.school = school;
		this.fbid = fbid;
		this.linkId = linkId;
		this.twitterId = twitterId;
		this.gender = gender;
		this.foodPref = foodPref;
		this.maritalStatus = maritalStatus;
		this.mariedDate = mariedDate;
		this.guestList = guestList;
		this.isBusNeeded = isBusNeeded;
		this.travellersCount = travellersCount;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isEntrepreneur() {
		return isEntrepreneur;
	}

	public void setEntrepreneur(boolean entrepreneur) {
		isEntrepreneur = entrepreneur;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(String natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public String getCompanyWebsite() {
		return companyWebsite;
	}

	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public long getMobileNumberFor() {
		return mobileNumberFor;
	}

	public void setMobileNumberFor(long mobileNumberFor) {
		this.mobileNumberFor = mobileNumberFor;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getFbid() {
		return fbid;
	}

	public void setFbid(String fbid) {
		this.fbid = fbid;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFoodPref() {
		return foodPref;
	}

	public void setFoodPref(String foodPref) {
		this.foodPref = foodPref;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getMariedDate() {
		return mariedDate;
	}

	public void setMariedDate(String mariedDate) {
		this.mariedDate = mariedDate;
	}

	public List<GuestHelper> getGuestList() {
		return guestList;
	}

	public void setGuestList(List<GuestHelper> guestList) {
		this.guestList = guestList;
	}

	public boolean isBusNeeded() {
		return isBusNeeded;
	}

	public void setBusNeeded(boolean busNeeded) {
		isBusNeeded = busNeeded;
	}

	public int getTravellersCount() {
		return travellersCount;
	}

	public void setTravellersCount(int travellersCount) {
		this.travellersCount = travellersCount;
	}
}
