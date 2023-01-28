package com.investment.managment.execution;

import com.investment.managment.Identifier;

import java.util.Objects;

public class ExecutionID extends Identifier<String> {

    private final String id;

    public static ExecutionID from(final String aValue) {
        return new ExecutionID(aValue);
    }

    private ExecutionID(final String id) {
        this.id = id;
    }

    @Override
    public String getValue() {
        return this.id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExecutionID executionID = (ExecutionID) o;
        return id.equals(executionID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
