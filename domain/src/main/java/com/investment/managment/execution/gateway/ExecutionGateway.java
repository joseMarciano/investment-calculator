package com.investment.managment.execution.gateway;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;

public interface ExecutionGateway {
    Execution create(Execution anExecution);
}
