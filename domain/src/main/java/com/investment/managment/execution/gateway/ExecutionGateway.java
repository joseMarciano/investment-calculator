package com.investment.managment.execution.gateway;

import com.investment.managment.execution.Execution;
import com.investment.managment.execution.ExecutionID;

import java.util.List;
import java.util.Optional;

public interface ExecutionGateway {
    Execution create(Execution anExecution);

    Execution update(Execution anExecution);

    Optional<Execution> findById(ExecutionID anId);

    void deleteById(ExecutionID id);

    List<Execution> findAll();
}
