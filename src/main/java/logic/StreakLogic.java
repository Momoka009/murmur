package logic;

import java.time.LocalDate;

public class StreakLogic {

    public static int calculateStreak(int currentStreak, LocalDate lastPostDate) {
        LocalDate today = LocalDate.now();

        if (lastPostDate == null) {
            return 1;
        }
        if (lastPostDate.equals(today)) {
            return currentStreak;
        }
        if (lastPostDate.equals(today.minusDays(1))) {
            return currentStreak + 1;
        }
        return 1;
    }
}

