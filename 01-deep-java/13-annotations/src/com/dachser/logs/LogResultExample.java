package com.dachser.logs;

import com.dachser.annotations.LogResult;

public class LogResultExample {

    @LogResult
    public String auditLogin() {
        return "Login audited";
    }

    @LogResult
    public String auditPayment() {
        return "Payment audited";
    }

    public String internalMethod() {
        return "This is not logged";
    }
}
