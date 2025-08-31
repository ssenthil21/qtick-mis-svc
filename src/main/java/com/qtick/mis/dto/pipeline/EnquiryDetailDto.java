package com.qtick.mis.dto.pipeline;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class EnquiryDetailDto extends EnquiryDto {
    
    private List<EnquiryThreadDto> threads;
    private Map<String, Object> attributes;
    private List<String> tags;
    
    // Constructors
    public EnquiryDetailDto() {
        super();
    }
    
    // Getters and Setters
    public List<EnquiryThreadDto> getThreads() { return threads; }
    public void setThreads(List<EnquiryThreadDto> threads) { this.threads = threads; }
    
    public Map<String, Object> getAttributes() { return attributes; }
    public void setAttributes(Map<String, Object> attributes) { this.attributes = attributes; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}