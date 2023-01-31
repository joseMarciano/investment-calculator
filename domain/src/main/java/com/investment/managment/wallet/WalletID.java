package com.investment.managment.wallet;

import com.investment.managment.Identifier;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public class WalletID extends Identifier<String> {

    private String value;

    public static WalletID from(final String aValue) {
        return new WalletID(aValue);
    }

    private WalletID(final String value) {
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
        final WalletID walletID = (WalletID) o;
        return value.equals(walletID.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
