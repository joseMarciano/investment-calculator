package com.investment.managment.wallet;

import com.investment.managment.Identifier;

import java.util.Objects;

public class WalletID extends Identifier<String> {

    private final String id;

    public static WalletID from(final String aValue) {
        return new WalletID(aValue);
    }

    private WalletID(final String id) {
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
        final WalletID walletID = (WalletID) o;
        return id.equals(walletID.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
