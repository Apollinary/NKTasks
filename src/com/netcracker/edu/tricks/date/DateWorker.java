package com.netcracker.edu.tricks.date;

public class DateWorker {

    public boolean isLeapYear(int year) {
        return (year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0);
    }

    public boolean isValidDate(Date date) {
        return (date.getYear() > 0 && date.getYear() < 9999) && (date.getMonth() > 0 && date.getMonth() < 12) && (date.getDay() > 0 && date.getDay() <= countDaysInMonth(date.getMonth(), date.getYear()));
    }

    public int countDaysInMonth(int month, int year) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            }
            return 28;
        }
        return 30;
    }

    public DayOfWeek getDayOfWeek(Date date) {
        int c = isLeapYear(date.getYear()) && date.getMonth() < 3 ? (date.getYear() - 1) / 100 : date.getYear() / 100;
        int Y = isLeapYear(date.getYear()) && date.getMonth() < 3 ? (date.getYear() - 1) % 100 : date.getYear() % 100;
        int m = date.getMonth() - 2 < 0 ? 11 : date.getMonth() - 2;

        int w = (date.getDay() + (13 * m - 1) / 5 + Y + (Y / 4) + (c / 4) - (2 * c)) % 7;

        return DayOfWeek.getDayByOrderNumber(w);
    }

    public String toString(Date date) {
        return String.format(getDayOfWeek(date) + " %02d.%02d.%04d", date.getDay(), date.getMonth(), date.getYear());
    }

    public int countDays(Date date) {
        Date currentDate = getCurrentDate();

        int diff = getCountDaysSinceYearBeginning(date) - getCountDaysSinceYearBeginning(currentDate);
        for (int i = date.getYear(); i < currentDate.getYear(); ++i) {
            if (isLeapYear(i)) diff += 366;
            else diff += 365;
        }
        return -diff;
    }

    public int getCountDaysSinceYearBeginning(Date date) {
        int dayOfYear = 0;
        for (int i = 1; i < date.getMonth(); ++i) {
            dayOfYear += countDaysInMonth(i, date.getYear());
        }
        return dayOfYear + date.getDay();
    }

    private Date getCurrentDate() {
        long initialYear = 1970;
        long currentTime = System.currentTimeMillis();
        long yearsDiff = (currentTime / 1000 / 60 / 60 / 24) / 365;
        long daysDiff = (currentTime / 1000 / 60 / 60 / 24) % 365;

        int numberOfMonth = 0;
        daysDiff = daysDiff - (int) yearsDiff / 4;

        while (daysDiff > 0) {
            daysDiff -= countDaysInMonth(numberOfMonth, (int) (initialYear + yearsDiff));
            numberOfMonth++;
        }
        daysDiff += countDaysInMonth(numberOfMonth, (int) (initialYear + yearsDiff));

        return new Date((int) daysDiff, numberOfMonth, (int) (initialYear + yearsDiff));
    }

}

