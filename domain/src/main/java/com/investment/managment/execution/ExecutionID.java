package com.investment.managment.execution;

import com.investment.managment.Identifier;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class ExecutionID extends Identifier<String> {

    private String value;

    public static ExecutionID from(final String aValue) {
        return new ExecutionID(aValue);
    }

    private ExecutionID(final String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExecutionID executionID = (ExecutionID) o;
        return value.equals(executionID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
