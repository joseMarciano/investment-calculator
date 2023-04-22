package com.investment.managment.http;

import com.investment.managment.http.getAllStocks.GetAllStocksResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@FeignClient(name = "${feign-client.investment-management.name}", url = "${feign-client.investment-management.url}")
public interface InvestmentManagementFeignClient {
    @RequestMapping(method = GET, value = "/stocks")
    GetAllStocksResponse getAllTickers(@RequestParam(value = "limit") int limit);

    @RequestMapping(method = GET, value = "/health")
    void check();
}
