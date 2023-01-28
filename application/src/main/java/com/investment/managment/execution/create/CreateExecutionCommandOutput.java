package com.investment.managment.execution.create;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;

import java.math.BigDecimal;

public record CreateExecutionCommandOutput(
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
    public static CreateExecutionCommandOutput from(final Execution anExecution) {
        return new CreateExecutionCommandOutput(
                anExecution.getId(),
                anExecution.getOrigin(),
                anExecution.getStockId(),
                anExecution.getWalletId(),
                anExecution.getProfitPercentage(),
                anExecution.getExecutedQuantity(),
                anExecution.getExecutedPrice(),
                anExecution.getExecutedVolume(),
                anExecution.getStatus()
        );
    }
}
