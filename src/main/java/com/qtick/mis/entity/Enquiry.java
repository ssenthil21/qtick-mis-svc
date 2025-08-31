package com.qtick.mis.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing an enquiry in the sales pipeline.
 * Tracks leads from initial contact through to closure.
 */
@Entity
@Table(name = "enquiries", indexes = {
    @Index(name = "idx_enquiry_biz_stage", columnList = "bizId, stage"),
    @Index(name = "idx_enquiry_biz_created", columnList = "bizId, createdOn"),
    @Index(name = "idx_enquiry_stage_touch", columnList = "stage, lastTouchDate"),
    @Index(name = "idx_enquiry_assignee", columnList = "assigneeId"),
    @Index(name = "idx_enquiry_customer", columnList = "custId")
})
public class Enquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bizId;

    private Long branchId;

    private Long custId;

    @Column(length = 100)
    private String name;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EnquiryType enqType;

    private Long srvcEnq;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EnquiryStage stage;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EnquiryStatus status;

    @Column(length = 50)
    private String source;

    @Column(length = 50)
    private String channel;

    private Long assigneeId;

    private LocalDateTime reContactOn;

    @Column(length = 500)
    private String nextAction;

    private LocalDateTime lastTouchDate;

    private LocalDateTime closureDate;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedOn;

    @OneToMany(mappedBy = "enquiry", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EnquiryThread> threads = new ArrayList<>();

    // Constructors
    public Enquiry() {}

    public Enquiry(Long bizId, String name, String phone, String email, EnquiryStage stage, EnquiryStatus status) {
        this.bizId = bizId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.stage = stage;
        this.status = status;
        this.lastTouchDate = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnquiryType getEnqType() {
        return enqType;
    }

    public void setEnqType(EnquiryType enqType) {
        this.enqType = enqType;
    }

    public Long getSrvcEnq() {
        return srvcEnq;
    }

    public void setSrvcEnq(Long srvcEnq) {
        this.srvcEnq = srvcEnq;
    }

    public EnquiryStage getStage() {
        return stage;
    }

    public void setStage(EnquiryStage stage) {
        this.stage = stage;
    }

    public EnquiryStatus getStatus() {
        return status;
    }

    public void setStatus(EnquiryStatus status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public LocalDateTime getReContactOn() {
        return reContactOn;
    }

    public void setReContactOn(LocalDateTime reContactOn) {
        this.reContactOn = reContactOn;
    }

    public String getNextAction() {
        return nextAction;
    }

    public void setNextAction(String nextAction) {
        this.nextAction = nextAction;
    }

    public LocalDateTime getLastTouchDate() {
        return lastTouchDate;
    }

    public void setLastTouchDate(LocalDateTime lastTouchDate) {
        this.lastTouchDate = lastTouchDate;
    }

    public LocalDateTime getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(LocalDateTime closureDate) {
        this.closureDate = closureDate;
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

    public List<EnquiryThread> getThreads() {
        return threads;
    }

    public void setThreads(List<EnquiryThread> threads) {
        this.threads = threads;
    }

    // Helper methods
    public void addThread(EnquiryThread thread) {
        threads.add(thread);
        thread.setEnquiry(this);
        this.lastTouchDate = LocalDateTime.now();
    }

    public void removeThread(EnquiryThread thread) {
        threads.remove(thread);
        thread.setEnquiry(null);
    }

    @Override
    public String toString() {
        return "Enquiry{" +
                "id=" + id +
                ", bizId=" + bizId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", stage=" + stage +
                ", status=" + status +
                '}';
    }
}