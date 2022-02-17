package com.arif.cabex.event;

public class OTPSentEvent {
    private String otp;

    public OTPSentEvent(String otp) {
        this.otp = otp;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
