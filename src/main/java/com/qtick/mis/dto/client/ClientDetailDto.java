package com.qtick.mis.dto.client;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

public class ClientDetailDto extends ClientSummaryDto {
    
    private LocalDate dob;
    private String gender;
    private String address;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    
    private Map<String, Object> preferences;
    private Map<String, Object> demographics;
    
    // KPIs
    private Integer totalVisits;
    private Integer totalAppointments;
    private Integer totalBills;
    private Double averageRating;
    private String preferredServices;
    
    // Constructors
    public ClientDetailDto() {
        super();
    }
    
    // Getters and Setters
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    
    public Map<String, Object> getPreferences() { return preferences; }
    public void setPreferences(Map<String, Object> preferences) { this.preferences = preferences; }
    
    public Map<String, Object> getDemographics() { return demographics; }
    public void setDemographics(Map<String, Object> demographics) { this.demographics = demographics; }
    
    public Integer getTotalVisits() { return totalVisits; }
    public void setTotalVisits(Integer totalVisits) { this.totalVisits = totalVisits; }
    
    public Integer getTotalAppointments() { return totalAppointments; }
    public void setTotalAppointments(Integer totalAppointments) { this.totalAppointments = totalAppointments; }
    
    public Integer getTotalBills() { return totalBills; }
    public void setTotalBills(Integer totalBills) { this.totalBills = totalBills; }
    
    public Double getAverageRating() { return averageRating; }
    public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    
    public String getPreferredServices() { return preferredServices; }
    public void setPreferredServices(String preferredServices) { this.preferredServices = preferredServices; }
}