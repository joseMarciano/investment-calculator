package com.investment.managment.execution.create;

import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;

public record CreateExecutionCommandInput(
        ExecutionID id,
        ExecutionID origin,
        StockID stockId,
        WalletID walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal executedVolume,
        ExecutionStatus status

) {

    public static CreateExecutionCommandInput with(
            final ExecutionID id,
            final ExecutionID origin,
            final StockID stockId,
            final WalletID walletId,
            final Double profitPercentage,
            final Long executedQuantity,
            final BigDecimal executedPrice,
            final BigDecimal executedVolume,
            final ExecutionStatus status
    ) {
        return new CreateExecutionCommandInput(id,
                origin,
                stockId,
                walletId,
                profitPercentage,
                executedQuantity,
                executedPrice,
                executedVolume,
                status);
    }
}
