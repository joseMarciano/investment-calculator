package com.investment.managment.execution.models.pnl;

import java.math.BigDecimal;

public record PnlNotificationRequest(String id, BigDecimal pnl) {

    public static PnlNotificationRequest with(final String id, final BigDecimal pnl) {
        return new PnlNotificationRequest(id, pnl);
    }
}
