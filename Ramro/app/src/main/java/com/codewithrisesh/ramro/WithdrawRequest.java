package com.codewithrisesh.ramro;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawRequest {

    private String requestedBy;
    private String emailId;
    private String userId;

    @ServerTimestamp
    private Date createdAt;

    public WithdrawRequest(){

    }

    public WithdrawRequest(String requestedBy, String emailId, String userId) {
        this.requestedBy = requestedBy;
        this.emailId = emailId;
        this.userId = userId;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
