package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        // разбиваем список на отдельные саб-списки связанные одной датой
        HashMap<LocalDate,List<UserMeal>> mapList  = new HashMap<LocalDate,List<UserMeal>>();
        LocalDate tmp = null;

        for (int i= 0; i< mealList.size();i++) {
            List<UserMeal>  listtmp =new ArrayList<UserMeal>();
            if(i == 0){
                tmp = mealList.get(i).getDateTime().toLocalDate();
                listtmp.add(mealList.get(i)) ;
                mapList.put(tmp,listtmp);
                i++;
            }
            LocalDate t = mealList.get(i).getDateTime().toLocalDate();
            if(t.equals(tmp)){
                listtmp = mapList.get(tmp);
                listtmp.add(mealList.get(i)) ;
            }else {
               try{
                   if(!mapList.get(t).isEmpty()) {
                       listtmp = mapList.get(t);
                       listtmp.add(mealList.get(i));
                       mapList.put(t, listtmp);
                   }
               } catch(NullPointerException ex){
                       listtmp.add(mealList.get(i));
                       mapList.put(t, listtmp);
               }

                tmp = t;
            }
              // to do
        }

        mapList.forEach((k,v) -> System.out.println("key: "+k + " val: "+v));
       // System.out.println(mapList.get(0));

        /*for (UserMeal meal : mealList) {
            LocalTime time = LocalTime.of(meal.getDateTime().getHour(), meal.getDateTime().getMinute());
            if (time.isAfter(startTime) && time.isBefore(endTime)) {
                System.out.println(meal.getCalories());
            }
        }*/

        return null;
    }


}
