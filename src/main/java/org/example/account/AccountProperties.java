package org.example.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final int defaultAccountAmount;
    private final double transferCommision;

    public AccountProperties(@Value("${account.default-amount}")int defaultAccountAmount,
                             @Value("${account.transfer-commission}")double transferCommision) {
        this.defaultAccountAmount = defaultAccountAmount;
        this.transferCommision = transferCommision;
    }

    public int getDefaultAccountAmount() {
        return defaultAccountAmount;
    }

    public double getTransferCommision() {
        return transferCommision;
    }
}
