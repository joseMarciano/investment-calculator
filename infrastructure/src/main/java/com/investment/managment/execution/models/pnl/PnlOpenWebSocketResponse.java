package com.investment.managment.execution.models.pnl;

import java.math.BigDecimal;

public record PnlOpenWebSocketResponse(
        String executionID,
        BigDecimal pnl,
        BigDecimal pnlOpenPercentage) {
}
