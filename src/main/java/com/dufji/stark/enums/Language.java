package com.dufji.stark.enums;

public enum Language {
    ERROR_NO_PERMISSION("error-no-permission", "&cYou do not have permission to execute this command."),
    ERROR_PLAYER_ONLY("error-player-only", "&cOnly players can execute this command."),
    ERROR_PLAYER_NOT_FOUND("error-player-not-found", "&cPlayer not found."),
    ERROR_INVALID_AMOUNT("error-invalid-amount", "&cInvalid amount."),
    ERROR_NEGATIVE_BALANCE("error-negative-balance", "&cYou cannot set a negative balance."),
    ERROR_NOT_ENOUGH_MONEY("error-not-enough-money", "&cYou do not have enough money."),
    ERROR_NOT_ENOUGH_ARGUMENTS("error-not-enough-arguments", "&cYou're missing an argument."),
    ;

    Language(String s, String s1) {
    }
}
