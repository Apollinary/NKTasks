package com.netcracker.edu.tricks.date;

public enum DayOfWeek {
    Sunday(0),
    Monday(1),
    Tuesday(2),
    Wednesday(3),
    Thursday(4),
    Friday(5),
    Saturday(6);
    private final int orderNumber;

    DayOfWeek(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    private int getOrderNumber() {
        return orderNumber;
    }

    public static DayOfWeek getDayByOrderNumber(int orderNumber) {
        if (orderNumber >= 0 && orderNumber <= 6) {
            DayOfWeek result = null;
            for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
                if (dayOfWeek.getOrderNumber() == orderNumber) {
                    result = dayOfWeek;
                }
            }
            return result;
        } else {
            throw new IllegalArgumentException("Number of day should be between 0 and 6");
        }
    }

}
