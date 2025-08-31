package com.qtick.mis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a client/customer in the system.
 * Contains master data and relationships to bills, appointments, etc.
 */
@Entity
@Table(name = "clients", indexes = {
    @Index(name = "idx_client_biz", columnList = "bizId"),
    @Index(name = "idx_client_phone", columnList = "phone"),
    @Index(name = "idx_client_email", columnList = "email"),
    @Index(name = "idx_client_created", columnList = "createdOn"),
    @Index(name = "idx_client_name", columnList = "name")
})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custId;

    @Column(nullable = false)
    private Long bizId;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String phone;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private Gender gender;

    @Column(length = 500)
    private String address;

    @Column(length = 100)
    private String city;

    @Column(length = 50)
    private String state;

    @Column(length = 20)
    private String pincode;

    @Column(length = 50)
    private String country;

    // Business-specific fields
    @Column(length = 100)
    private String businessName;

    @Column(length = 50)
    private String businessType;

    @Column(length = 100)
    private String contactPerson;

    // Loyalty and preferences
    private Integer loyaltyPoints = 0;

    @Column(length = 50)
    private String preferredChannel;

    @Column(length = 100)
    private String tags;

    private LocalDateTime lastVisitDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ClientStatus status = ClientStatus.ACTIVE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedOn;

    // Relationships
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bill> bills = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PointsTransaction> pointsTransactions = new ArrayList<>();

    // Constructors
    public Client() {}

    public Client(Long bizId, String name, String phone, String email) {
        this.bizId = bizId;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getPreferredChannel() {
        return preferredChannel;
    }

    public void setPreferredChannel(String preferredChannel) {
        this.preferredChannel = preferredChannel;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDateTime getLastVisitDate() {
        return lastVisitDate;
    }

    public void setLastVisitDate(LocalDateTime lastVisitDate) {
        this.lastVisitDate = lastVisitDate;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    public List<PointsTransaction> getPointsTransactions() {
        return pointsTransactions;
    }

    public void setPointsTransactions(List<PointsTransaction> pointsTransactions) {
        this.pointsTransactions = pointsTransactions;
    }

    // Helper methods
    public void addBill(Bill bill) {
        bills.add(bill);
        bill.setClient(this);
        this.lastVisitDate = LocalDateTime.now();
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.setClient(this);
    }

    public void addPointsTransaction(PointsTransaction transaction) {
        pointsTransactions.add(transaction);
        transaction.setClient(this);
        this.loyaltyPoints += transaction.getPoints();
    }

    @Override
    public String toString() {
        return "Client{" +
                "custId=" + custId +
                ", bizId=" + bizId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}