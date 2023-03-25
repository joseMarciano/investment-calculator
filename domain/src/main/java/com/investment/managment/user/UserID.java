package com.investment.managment.user;

import com.investment.managment.Identifier;

import java.util.Objects;

public class UserID extends Identifier<String> {

    private final String value;

    public static UserID from(final String aValue) {
        return new UserID(aValue);
    }

    private UserID(final String value) {
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
        final UserID userID = (UserID) o;
        return value.equals(userID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
