package com.investment.managment.http.getAllStocks;

import java.util.List;

public record GetAllStocksResponse(
        List<StockItemResponse> items
) {
}
