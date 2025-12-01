package com.example.mini_project_community_center.common.apis;

public class PaymentApi {
    private PaymentApi() {}
    public static final String ROOT = ApiBase.BASE + "/payments";
    public static final String BY_ID = "/{paymentId}";
    public static final String PAYMENT_REFUND = BY_ID + "/refund";
}
