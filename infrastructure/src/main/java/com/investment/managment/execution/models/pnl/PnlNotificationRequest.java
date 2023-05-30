package com.investment.managment.execution.models.pnl;

import java.math.BigDecimal;

public record PnlNotificationRequest(String id, BigDecimal pnl, BigDecimal pnlOpenPercentage) {

    public static PnlNotificationRequest with(final String id, final BigDecimal pnl, final BigDecimal pnlOpenPercentage) {
        return new PnlNotificationRequest(id, pnl, pnlOpenPercentage);
    }
}
