package Simulation;

public class Date {
    private static int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static String[] monthName = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    private int minute;
    private int hour;
    private int day;
    private int month;
    private int year;

    public Date(long minute) {
        int[] yearData = getYear(minute);
        this.year = yearData[0];
        int remainder = yearData[1];

        int i = 0;
        int total = (daysInMonth[0]) * 60 * 24;
        while (total <= remainder && ++i < daysInMonth.length) {
            total += (i == 1 && isLeap(this.year) ? daysInMonth[i] + 1 : daysInMonth[i]) * 60 * 24;
        }
        this.month = i;
        remainder -= total - (i == 1 && isLeap(this.year) ? daysInMonth[i] + 1 : daysInMonth[i]) * 60 * 24;

        this.day = 1 + remainder / (60 * 24);
        remainder = remainder % (60 * 24);

        this.hour = remainder / 60;
        remainder = remainder % 60;

        this.minute = remainder;
    }

    @Override
    public String toString() {
        return monthName[month] + " " + day + " " + year + " " +
                (hour < 10 ? "0" : "") + hour + ":" + (minute < 10 ? "0" : "") + minute;
    }

    public int[] getYear(long minute) {
        int i = 3000;

        while (true) {
            if (isLeap(++i)) {
                if (minute < 60 * 24 * 366) break;
                minute -= 60 * 24 * 366;
            }
            else {
                if (minute < 60 * 24 * 365) break;
                minute -= 60 * 24 * 365;
            }
        }
        return new int[]{i, (int) minute};
    }

    public boolean isLeap(int year) {
        return (year % 100 == 0 && year % 400 == 0) || (year % 100 != 0 && year % 4 == 0);
    }
}
