package ru.javawebinar.topjava.util;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class UserMealsUtil {

  public static void main(String[] args) {
    List<UserMeal> meals = Arrays.asList(

        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение",
            100),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
        new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0),
        LocalTime.of(12, 0), 2000);
    mealsTo.forEach(System.out::println);

    System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
  }

  public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
      LocalTime endTime, int caloriesPerDay) {
    List<UserMealWithExcess> result = new ArrayList<>();
    HashMap<LocalDate, Integer> caloriesDay = new HashMap<>();
    meals.forEach(meal -> {
      caloriesDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
    });
    meals.forEach(meal -> {
      if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
        result.add(
            new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                caloriesDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
      }
    });
    return result;
  }

  public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals,
      LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
    List<UserMealWithExcess> result;
    HashMap<LocalDate, Integer> caloriesDay = meals.stream().collect(Collectors
        .toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum,
            HashMap::new));
    result = meals.stream().filter(
        meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
        .map(meal -> new UserMealWithExcess(meal.getDateTime(), meal.getDescription(),
            meal.getCalories(),
            caloriesDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
        .collect(Collectors.toList());
    return result;
  }
}
