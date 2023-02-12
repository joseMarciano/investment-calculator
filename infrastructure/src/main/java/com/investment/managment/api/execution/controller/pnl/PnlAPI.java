package com.investment.managment.api.execution.controller.pnl;

import com.investment.managment.execution.models.pnl.PnlOpenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pnl")
public interface PnlAPI {

    @GetMapping("open/{executionId}")
    PnlOpenResponse getPnlOpen(@PathVariable String executionId);
}
