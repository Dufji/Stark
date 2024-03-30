package com.dufji.stark.enums;

import com.dufji.stark.models.StarkPlayer;

// Dufji Task #1
public enum Language {
    ERROR_NO_PERMISSION("I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is an error."),
    ERROR_PLAYER_ONLY("&c&lERROR! &cOnly players can execute this command."),
    ERROR_PLAYER_NOT_FOUND("&c&lERROR! &cPlayer not found."),
    ERROR_SOMETHING_WENT_WRONG("&c&lERROR! &cSomething Went Wrong. Please contact the administrators."),
    ERROR_INVALID_AMOUNT("&c&lERROR! &cInvalid amount."),
    ERROR_NEGATIVE_BALANCE("&cYou cannot set a negative balance."),
    ERROR_NOT_ENOUGH_MONEY("&cYou do not have enough money for this!."),
    ERROR_NOT_ENOUGH_ARGUMENTS("&cYou're missing an argument."),
    NOTICE_PAY("&e&lNOTICE! &6%player% &7has sent you &6%amount%"),
    SUCCESS_PAY("&a&lSUCCESS! &7You have paid &6%amount% &7to &6%player%"),
    SUCCESS_SET_BALANCE("&a&lSUCCESS! &7You have set &6%player%'s &7balance to &6%amount%"),

    NOTICE_INTEREST("&e&lNOTICE! &7You have received &a%amount% &7from daily interest."),

    NOTICE_BALANCE_SET("&e&lNOTICE! &7Your balance has been set to &6%amount%"),
    NOTICE_BALANCE_ADD("&e&lNOTICE! &7You have received &a$%amount% &7from &6%player%"),
    NOTICE_BALANCE_DEATH("&e&lNOTICE! &7You have lost &a$%amount% &7to &6%player%"),
    NOTICE_BALANCE_RESET("&e&lNOTICE! &7Your balance has been reset."),




    ;

    Language(String s) {

    }

    Language(StarkPlayer player, StarkPlayer target, String s) {
    }

}
