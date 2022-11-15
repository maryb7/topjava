package ru.javawebinar.topjava.util;

import org.w3c.dom.ls.LSOutput;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(21,0), 2000));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> mealWithExceeds = new ArrayList<>();
//        LocalDate newLocalDate;
//        LocalDate lastLocalDate = null;
//        int calories = 0;
//        for (UserMeal userMeal: mealList) {
//            newLocalDate = userMeal.getDateTime().toLocalDate();
//            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
//                if ( lastLocalDate != null && newLocalDate.isEqual(lastLocalDate)) {
//                    calories += userMeal.getCalories();
//                    if (calories > caloriesPerDay) {
//                        mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
//                    }
//                }
//                if (lastLocalDate == null || !newLocalDate.isEqual(lastLocalDate)) {
//                    calories = 0;
//                    calories += userMeal.getCalories();
//                    if (calories > caloriesPerDay) {
//                        mealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), true));
//                    }
//                }
//            }
//            lastLocalDate = userMeal.getDateTime().toLocalDate();
//        }
        Map<LocalDate, Integer> mapPerDay = new HashMap<>();
        mealList.forEach(u -> mapPerDay.merge(u.getDateTime().toLocalDate(), u.getCalories(), Integer::sum));
        mealList.forEach(u -> {
            if (TimeUtil.isBetween(u.getDateTime().toLocalTime(), startTime, endTime) && mapPerDay.get(u.getDateTime().toLocalDate()) > caloriesPerDay) {
                mealWithExceeds.add(new UserMealWithExceed(u.getDateTime(), u.getDescription(), u.getCalories(), true));
            }
        });
        return mealWithExceeds;
    }
}
