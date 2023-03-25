package com.investment.managment.user.online;

public record UserOnlineCommandInput(
        String id,
        boolean isOnline
) {

    public static UserOnlineCommandInput with(final String id, final boolean isOnline) {
        return new UserOnlineCommandInput(id, isOnline);
    }

}
