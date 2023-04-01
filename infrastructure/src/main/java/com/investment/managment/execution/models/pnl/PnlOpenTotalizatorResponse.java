package com.investment.managment.execution.models.pnl;

import java.math.BigDecimal;

public record PnlOpenTotalizatorResponse(
        String id,
        BigDecimal pnlOpen
) {
}
