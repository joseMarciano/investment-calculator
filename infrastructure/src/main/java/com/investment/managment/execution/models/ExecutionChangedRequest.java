package com.investment.managment.execution.models;

import com.investment.managment.execution.ExecutionChangeReason;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;

public record ExecutionChangedRequest(
        ExecutionDTO execution,
        ExecutionChangeReason reason
) {

    public record ExecutionDTO(
            ExecutionID id,
            ExecutionID origin,
            StockID stockId,
            WalletID walletId,
            Double profitPercentage,
            Long executedQuantity,
            BigDecimal executedPrice,
            BigDecimal executedVolume,
            ExecutionStatus status,
            BigDecimal pnlClose,
            BigDecimal pnlClosePercentage

    ) {

        public static ExecutionDTO with(final ExecutionID anId, final ExecutionID originId, final ExecutionStatus status) {
            return new ExecutionDTO(anId, originId, null, null, null, null, null, null, status, null, null);
        }

    }
}


