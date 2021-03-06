package com.kikichante.utils;

public class ColorOutput {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void redMessage(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    public static void blueMessage(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }

    public static void greenMessage(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    public static void purpleMessage (String message) {
        System.out.println(ANSI_PURPLE + message + ANSI_RESET);
    }

    public static void yellowMessage (String message) {
        System.out.println(ANSI_YELLOW + message + ANSI_RESET);
    }

    public static void cyanMessage (String message) {
        System.out.println(ANSI_CYAN + message + ANSI_RESET);
    }

    public static void redBgMessage (String message) {
        System.out.println(ANSI_RED_BACKGROUND + message + ANSI_RESET);
    }

}
