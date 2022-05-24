package com.arif.cabex.event;

public class CodeSentEvent {
    String verificationCode;

    public CodeSentEvent(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
