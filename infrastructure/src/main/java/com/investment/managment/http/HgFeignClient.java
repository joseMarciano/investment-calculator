package com.investment.managment.http;

import com.investment.managment.http.getAllStocks.GetAllStocksResponse;
import com.investment.managment.http.getstockprice.GetStockPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "${feign-client.hg-console.name}", url = "${feign-client.hg-console.url}")
public interface HgFeignClient {
    @RequestMapping(method = GET, value = "/stock_price")
    GetStockPriceResponse getStockPrice(@RequestParam(value = "symbol") String... symbols);
}
