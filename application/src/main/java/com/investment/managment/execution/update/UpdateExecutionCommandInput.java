package com.investment.managment.execution.update;

import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;
import java.util.Set;

public record UpdateExecutionCommandInput(
        ExecutionID id,
        ExecutionID origin,
        StockID stockId,
        WalletID walletId,
        Double profitPercentage,
        Long executedQuantity,
        BigDecimal executedPrice,
        BigDecimal executedVolume,
        ExecutionStatus status,
        Set<ExecutionID> executionsSold,
        BigDecimal pnlClose,
        BigDecimal pnlClosePercentage) {

    public static UpdateExecutionCommandInput with(
            final ExecutionID id,
            final ExecutionID origin,
            final StockID stockId,
            final WalletID walletId,
            final Double profitPercentage,
            final Long executedQuantity,
            final BigDecimal executedPrice,
            final BigDecimal executedVolume,
            final ExecutionStatus status,
            final Set<ExecutionID> executionsSold,
            final BigDecimal pnlClose,
            final BigDecimal pnlClosePercentage) {
        return new UpdateExecutionCommandInput(id,
                origin,
                stockId,
                walletId,
                profitPercentage,
                executedQuantity,
                executedPrice,
                executedVolume,
                status,
                executionsSold,
                pnlClose,
                pnlClosePercentage
        );
    }
}
