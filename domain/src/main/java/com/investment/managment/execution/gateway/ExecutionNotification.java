package com.investment.managment.execution.gateway;

import com.investment.managment.execution.Execution;

public interface ExecutionNotification {

    void notifyPnlOpen(Execution execution);

}
