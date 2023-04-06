package org.example;

public class Response {

    private final long sessionId;

    private final int approvedApplicationsAmount;

    public Response(long sessionId, int approvedApplicationsAmount) {
        this.sessionId = sessionId;
        this.approvedApplicationsAmount = approvedApplicationsAmount;
    }

    public long getSessionId() {
        return sessionId;
    }

    public int getApprovedApplicationsAmount() {
        return approvedApplicationsAmount;
    }
}
