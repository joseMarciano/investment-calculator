package com.investment.managment;

public abstract class AggregateRoot<T extends Identifier<?>> {

    protected abstract T getId();
}
