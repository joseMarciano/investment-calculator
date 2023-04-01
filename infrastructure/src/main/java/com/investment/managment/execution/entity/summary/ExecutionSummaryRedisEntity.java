package com.investment.managment.execution.entity.summary;

import com.investment.managment.execution.sumary.ExecutionSummary;
import com.investment.managment.execution.sumary.ExecutionSummaryID;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@RedisHash("ExecutionSummary")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExecutionSummaryRedisEntity {

    @Id
    private String id;

    private BigDecimal pnlOpen;

    public static ExecutionSummaryRedisEntity from(final ExecutionSummary summary) {
        return builder()
                .id(summary.getId().getValue())
                .pnlOpen(summary.getPnlOpen())
                .build();
    }

    public ExecutionSummary toAggregate() {
        final var walletId = WalletID.from(this.id.split(":")[0]);
        final var stockId = StockID.from(this.id.split(":")[1]);
        return ExecutionSummary.builder()
                .id(ExecutionSummaryID.from(stockId, walletId))
                .pnlOpen(this.pnlOpen)
                .build();
    }
}
