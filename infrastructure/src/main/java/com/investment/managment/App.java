package com.investment.managment;

import com.investment.managment.http.InvestmentManagementFeignClient;
import com.investment.managment.http.getAllStocks.GetAllStocksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextStackAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableAutoConfiguration(
        exclude = {
                ContextInstanceDataAutoConfiguration.class,
                ContextStackAutoConfiguration.class,
                ContextRegionProviderAutoConfiguration.class
        }
)
public class App implements CommandLineRunner {
    @Autowired
    private InvestmentManagementFeignClient investmentManagementFeignClient;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
    }
}
