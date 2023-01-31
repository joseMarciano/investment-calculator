package com.investment.managment.execution.deleteById;

import com.investment.managment.execution.ExecutionID;

public record DeleteExecutionByIdCommandInput(
        ExecutionID id
) {

    public static DeleteExecutionByIdCommandInput with(
            final ExecutionID id
    ) {
        return new DeleteExecutionByIdCommandInput(id);
    }
}
