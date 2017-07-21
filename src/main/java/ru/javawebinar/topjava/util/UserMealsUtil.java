package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 20,20,0), "Ужин", 500),
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

      //  mapList.forEach((k,v) -> System.out.println("key: "+k + " val: "+v));
        Map<LocalDate,Integer> mapList2  = new HashMap<>();
        Collections.sort(mealList,(left, right) -> left.getDateTime().compareTo(right.getDateTime()));
        mealList.forEach((k) -> System.out.println(k.getDateTime()+" "+k.getDescription()+" " +k.getCalories()));
        Integer summ =0;
        for(int i=0; i < mealList.size();i++){
            if(i == 0){
                tmp = mealList.get(i).getDateTime().toLocalDate();
                summ = mealList.get(i).getCalories();
                mapList2.put(tmp,summ);
                i++;
            }
            LocalDate t = mealList.get(i).getDateTime().toLocalDate();
            if(t.equals(tmp)){
                summ = mapList2.get(tmp) + mealList.get(i).getCalories() ;
                mapList2.put(tmp,summ);
            }else {
                try{
                    if(!mapList.get(t).isEmpty()) {
                        summ = mapList2.get(t) + mealList.get(i).getCalories();
                        mapList2.put(t,summ);

                    }
                } catch(NullPointerException ex){
                    summ =  mealList.get(i).getCalories();
                    mapList2.put(t, summ);
                }

                tmp = t;
            }
        }
        mapList2.forEach((k,v) -> System.out.println("key: "+k + " val: "+v));
        List<UserMealWithExceed> listUMEx = new ArrayList<>();
        for(UserMeal um : mealList){
            boolean ex =true;
            if( mapList2.get(um.getDateTime().toLocalDate()) <= caloriesPerDay )
            { ex = false;}
            listUMEx.add(new UserMealWithExceed(
                    um.getDateTime(),
                    um.getDescription(),
                    um.getCalories(),
                    ex)
            ) ;

        }
       listUMEx.forEach(v -> System.out.println(v.getDateTime()+" "+v.getDescription()+" "+v.getCalories()+" "+v.isExceed()));
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
