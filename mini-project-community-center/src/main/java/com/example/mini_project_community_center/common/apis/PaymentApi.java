package com.example.mini_project_community_center.common.apis;

public class PaymentApi {
    private PaymentApi() {}
    public static final String ROOT = ApiBase.BASE + "/payments";
    public static final String BY_ID = "/{paymentId}";
    public static final String PAYMENT_BY_ID = ROOT + BY_ID;
    public static final String PAYMENT_REFUND = PAYMENT_BY_ID + "/refund";
}
