package com.investment.managment.api.stock.scheduler;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.gateway.ExecutionGateway;
import com.investment.managment.http.HgFeignClient;
import com.investment.managment.http.InvestmentManagementFeignClient;
import com.investment.managment.http.getAllStocks.GetAllStocksResponse;
import com.investment.managment.http.getAllStocks.StockItemResponse;
import com.investment.managment.http.getstockprice.StockLastTradePrice;
import com.investment.managment.stock.Stock;
import com.investment.managment.stock.StockID;
import com.investment.managment.stock.StockUsed;
import com.investment.managment.stock.create.CreateStockCommandInput;
import com.investment.managment.stock.create.CreateStockUseCase;
import com.investment.managment.stock.gateway.StockGateway;
import com.investment.managment.stock.update.UpdateStockCommandInput;
import com.investment.managment.stock.update.UpdateStockUseCase;
import org.apache.commons.collections4.ListUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StockAPIScheduleTasksImpl implements StockAPIScheduleTasks {

    private static final int LIMIT = 5000;
    private final InvestmentManagementFeignClient investmentManagementFeignClient;
    private final HgFeignClient hgFeignClient;
    private final StockGateway stockGateway;
    private final ExecutionGateway executionGateway;
    private final UpdateStockUseCase updateStockUseCase;
    private final CreateStockUseCase createStockUseCase;

    public StockAPIScheduleTasksImpl(final InvestmentManagementFeignClient investmentManagementFeignClient,
                                     final HgFeignClient hgFeignClient,
                                     final StockGateway stockGateway,
                                     final ExecutionGateway executionGateway,
                                     final UpdateStockUseCase updateStockUseCase,
                                     final CreateStockUseCase createStockUseCase) {
        this.investmentManagementFeignClient = investmentManagementFeignClient;
        this.hgFeignClient = hgFeignClient;
        this.stockGateway = stockGateway;
        this.executionGateway = executionGateway;
        this.updateStockUseCase = updateStockUseCase;
        this.createStockUseCase = createStockUseCase;
    }


    /**
     * Every week day at 1:30am
     */
    @Override
    @Scheduled(cron = "0 30 1 * * MON-FRI")
    public void updateOrCreateStocks() {
        Optional.ofNullable(this.investmentManagementFeignClient.getAllTickers(LIMIT))
                .map(GetAllStocksResponse::items)
                .ifPresent(items -> items.forEach(this::updateOrCreateStock));
    }

    /**
     * Every week day at 1:45am
     */
    @Override
    @Scheduled(cron = "0 45 1 * * MON-FRI")
    public void verifyUpdateUsedStocks() {
        final var stocksUsed = this.executionGateway.findAll()
                .stream().map(Execution::getStockId)
                .collect(Collectors.toSet());

        this.stockGateway.clearAllStockUsed();

        final var newStocks = stocksUsed.stream()
                .map(stockGateway::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(stock -> StockUsed.builder().id(stock.getId()).symbol(stock.getSymbol()).build())
                .collect(Collectors.toSet());

        this.stockGateway.addStockUsed(newStocks);
    }

    @Override
    @Scheduled(cron = "0 */25 9-18 * * MON-FRI")
    public void updateLastTradePrice() {
        final var usedStocks = new ArrayList<>(this.stockGateway.findUsedStocks());

        if (usedStocks.size() > 20) {
            ListUtils.partition(usedStocks, 20)
                    .forEach(this::updateLastTradePrice);
        } else if (!usedStocks.isEmpty()) {
            updateLastTradePrice(usedStocks);
        }
    }

    private void updateLastTradePrice(final List<StockUsed> stocksUsed) {
        final var response = this.hgFeignClient.getStockPrice(stocksUsed.stream().map(StockUsed::getSymbol).toList().toArray(new String[]{}));
        response.buildItems().stream().map(buildStock(stocksUsed)).forEach(stock -> this.updateStockUseCase.execute(
                UpdateStockCommandInput.with(
                        stock.getId(),
                        stock.getSymbol(),
                        stock.getLastTradePrice()
                )
        ));
    }

    private Function<StockLastTradePrice, Stock> buildStock(final List<StockUsed> stocksUsed) {
        return stockLastTradePrice -> stocksUsed.stream()
                .filter(it -> it.getSymbol().equals(stockLastTradePrice.symbol()))
                .findFirst()
                .map(stockUsed -> Stock.builder().id(stockUsed.getId()).symbol(stockUsed.getSymbol()).lastTradePrice(stockLastTradePrice.lastTradePrice()).build())
                .orElse(null);
    }

    private void updateOrCreateStock(final StockItemResponse stockResponse) {
        final var anId = StockID.from(stockResponse.id());

        this.stockGateway.findById(anId)
                .ifPresentOrElse(update(stockResponse), save(stockResponse));
    }

    private Consumer<Stock> update(final StockItemResponse stockResponse) {
        return stock -> this.updateStockUseCase.execute(UpdateStockCommandInput.with(
                stock.getId(),
                stockResponse.symbol(),
                stock.getLastTradePrice()
        ));
    }

    private Runnable save(final StockItemResponse stockResponse) {
        return () -> this.createStockUseCase
                .execute(CreateStockCommandInput.with(StockID.from(stockResponse.id()), stockResponse.symbol()));
    }


}
