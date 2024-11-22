package com.requestapproval.requestapproval.Constants;

public class Constants {
    public static class UserEntity{

        public static final String STATUS = "A";
        public static final String PREFIX = "uid_";
    }
    public static class UserRole{

        public static final String PREFIX = "rd_";
    }
    public static class RequestStatus {
        public static final String NewRequest = "New_Req";
        public static final String Pending = "Pending";
        public static final String Revoked = "Revoked";
        public static final String NewRevision = "New_Rev";
        public static final String Approved = "Approved";
        public static final String Declined = "Declined";
    }

    public static class ApprovalStatus {
        public static final String New = "New";
        public static final String Pending = "Pending";
        public static final String Approve = "Approve";
        public static final String Decline = "Decline";
    }
}
