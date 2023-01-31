package com.investment.managment.execution.entity;

import com.investment.managment.Identifier;
import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;
import com.investment.managment.execution.ExecutionStatus;
import com.investment.managment.stock.StockID;
import com.investment.managment.wallet.WalletID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@RedisHash("Execution")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ExecutionRedisEntity {

    @Id
    private String id;

    private String origin;

    private String stockId;

    private String walletId;

    private Double profitPercentage;

    private Long executedQuantity;

    private BigDecimal executedPrice;

    private BigDecimal executedVolume;

    private ExecutionStatus status;

    public static ExecutionRedisEntity from(final Execution execution) {
        return builder()
                .id(execution.getId().getValue())
                .origin(getIdentifier(execution.getOrigin()))
                .stockId(getIdentifier(execution.getStockId()))
                .walletId(getIdentifier(execution.getWalletId()))
                .profitPercentage(execution.getProfitPercentage())
                .executedQuantity(execution.getExecutedQuantity())
                .executedPrice(execution.getExecutedPrice())
                .executedVolume(execution.getExecutedVolume())
                .status(execution.getStatus())
                .build();
    }

    private static <T> T getIdentifier(final Identifier<T> identifier) {
        return Optional.ofNullable(identifier)
                .map(Identifier::getValue)
                .orElse(null);
    }

    public Execution toAggregate() {
        return Execution.builder()
                .id(ExecutionID.from(this.id))
                .origin(ofNullable(this.origin).map(ExecutionID::from).orElse(null))
                .stockId(ofNullable(this.stockId).map(StockID::from).orElse(null))
                .walletId(ofNullable(this.walletId).map(WalletID::from).orElse(null))
                .profitPercentage(this.profitPercentage)
                .executedQuantity(this.executedQuantity)
                .executedPrice(this.executedPrice)
                .executedVolume(this.executedVolume)
                .status(this.status)
                .build();
    }
}
