package com.investment.managment.execution.gateway;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;

import java.util.Optional;

public interface ExecutionGateway {
    Execution create(Execution anExecution);

    Optional<Execution> findById(ExecutionID anId);
}
