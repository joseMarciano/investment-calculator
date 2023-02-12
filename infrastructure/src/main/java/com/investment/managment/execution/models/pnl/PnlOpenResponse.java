package com.investment.managment.execution.models.pnl;

import java.math.BigDecimal;

public record PnlOpenResponse(
        String id,
        BigDecimal pnlOpen
) {
}
