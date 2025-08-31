package com.qtick.mis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Entity representing a communication thread/message in an enquiry.
 * Tracks all interactions with the prospect/customer.
 */
@Entity
@Table(name = "enquiry_threads", indexes = {
    @Index(name = "idx_thread_enquiry", columnList = "enquiry_id"),
    @Index(name = "idx_thread_created", columnList = "createdOn")
})
public class EnquiryThread {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enquiry_id", nullable = false)
    private Enquiry enquiry;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ThreadType threadType;

    @Column(length = 100)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ThreadDirection direction;

    private Long userId;

    @Column(length = 100)
    private String userName;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    // Additional metadata fields
    @Column(length = 50)
    private String phoneNumber;

    @Column(length = 100)
    private String emailAddress;

    private Integer durationMinutes;

    @Column(length = 500)
    private String attachments;

    // Constructors
    public EnquiryThread() {}

    public EnquiryThread(Enquiry enquiry, ThreadType threadType, String message, ThreadDirection direction, Long userId, String userName) {
        this.enquiry = enquiry;
        this.threadType = threadType;
        this.message = message;
        this.direction = direction;
        this.userId = userId;
        this.userName = userName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enquiry getEnquiry() {
        return enquiry;
    }

    public void setEnquiry(Enquiry enquiry) {
        this.enquiry = enquiry;
    }

    public ThreadType getThreadType() {
        return threadType;
    }

    public void setThreadType(ThreadType threadType) {
        this.threadType = threadType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ThreadDirection getDirection() {
        return direction;
    }

    public void setDirection(ThreadDirection direction) {
        this.direction = direction;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    @Override
    public String toString() {
        return "EnquiryThread{" +
                "id=" + id +
                ", threadType=" + threadType +
                ", direction=" + direction +
                ", createdOn=" + createdOn +
                '}';
    }
}