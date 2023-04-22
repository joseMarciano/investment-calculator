package com.investment.managment.api.health;

import com.investment.managment.http.InvestmentManagementFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InvestmentManagmentCheck {

    private final InvestmentManagementFeignClient investmentManagementFeignClient;

    public InvestmentManagmentCheck(final InvestmentManagementFeignClient investmentManagementFeignClient) {
        this.investmentManagementFeignClient = investmentManagementFeignClient;
    }

    @Scheduled(fixedDelay = 60000)
    public void checkInvestmentCalculator() {
        try {
            investmentManagementFeignClient.check();
        } catch (Exception e) {
            log.error("Error on check investiment-managment {}", e.getMessage());
        }
    }
}
