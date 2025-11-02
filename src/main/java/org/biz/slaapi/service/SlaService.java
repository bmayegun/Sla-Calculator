package org.biz.slaapi.service;

import org.biz.slaapi.model.SlaRequest;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SlaService {
    public LocalDateTime getDueDate(SlaRequest slaRequest) {
        LocalDateTime current = slaRequest.getStartDateTime();
        double durationHours = slaRequest.getDurationHours();

        while (durationHours > 0) {

            if(!slaRequest.getWorkingDays().contains(current.getDayOfWeek())) {
                current = current.plusDays(1).withHour(slaRequest.getWorkingHoursStart().getHour())
                        .withMinute(slaRequest.getWorkingHoursStart().getMinute())
                        .withSecond(0);
                continue;
            }

            LocalDateTime workDayStart = current.withHour(slaRequest.getWorkingHoursStart().getHour())
                    .withMinute(slaRequest.getWorkingHoursStart().getMinute())
                    .withSecond(0)
                    .withNano(0);
            LocalDateTime workDayEnd = current.withHour(slaRequest.getWorkingHoursEnd().getHour())
                    .withMinute(slaRequest.getWorkingHoursEnd().getMinute())
                    .withSecond(0)
                    .withNano(0);

            if (current.isAfter(workDayEnd)) {
                current = current.plusDays(1).withHour(slaRequest.getWorkingHoursStart().getHour())
                        .withMinute(slaRequest.getWorkingHoursStart().getMinute())
                        .withSecond(0);
                continue;
            }
            if (current.isBefore(workDayStart)) {
                current = workDayStart;
            }

            Duration duration = Duration.between(current, workDayEnd);
            double available = duration.toMinutes() / 60.0;

            if (available >= durationHours) {
                return current.plusMinutes((long) (durationHours * 60));
            } else {
                durationHours -= available;
                current = workDayEnd.plusSeconds(1);
            }
        }
        return current;
    }

//    public static Set<Integer> getExcludedDays(Set<DayOfWeek> workingDays) {
//
//    }

//    public static int getWorkHours(){
//        int workHours;
//        int resumptionHour = getVehicleMaintenanceWorkshopResumptionHour(), closingHour = getVehicleMaintenanceWorkshopClosingHour();
//        if (closingHour < resumptionHour) {
//            workHours = (24 - resumptionHour) + closingHour;
//        } else {
//            workHours = closingHour - resumptionHour;
//        }
//        return workHours;
//    }
//    public static int getNoOfWorkDaysFromHours(int hours){
//        int workHours = getWorkHours();
//        return hours / workHours;
//    }
//
//    public static int getRemainingWorkHoursAfterDays(int days, int hours){
//        int workHours = getWorkHours();
//        return hours - (days * workHours);
//    }

//    public LocalDateTime getNoOfExcludedDaysBetweenDates(SlaRequest slaRequest) {
//
//    }

//    public static Date getNextWorkDay(Date date){
//        int resumptionHour = getVehicleMaintenanceWorkshopResumptionHour();
//        int closingHour = getVehicleMaintenanceWorkshopClosingHour();
//        Set<Integer> excludedDaysSet = getExcludedDays();
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//        if(c.get(Calendar.HOUR_OF_DAY) >= closingHour){
//            c.add(Calendar.DAY_OF_YEAR, 1);
//            c.set(Calendar.HOUR_OF_DAY, resumptionHour);
//        } else if (c.get(Calendar.HOUR_OF_DAY) < resumptionHour) {
//            c.set(Calendar.HOUR_OF_DAY, resumptionHour);
//        }
//        while (excludedDaysSet.contains(c.get(Calendar.DAY_OF_WEEK))){
//            c.add(Calendar.DAY_OF_YEAR, 1);
//            c.set(Calendar.HOUR_OF_DAY, resumptionHour);
//        }
//        return c.getTime();
//    }
//    public static Date incrementDateWithWorkHours(Date date, int field, int value) {
//        int resumptionHour = getVehicleMaintenanceWorkshopResumptionHour();
//        int closingHour = getVehicleMaintenanceWorkshopClosingHour();
//        Calendar c = Calendar.getInstance();
//        c.setTime(date);
//
//        if (Calendar.DAY_OF_YEAR == field || Calendar.MONTH == field ) {
//            c.add(field, value);
//            int excludedDaysCount = getNoOfExcludedDaysBetweenDates(date, c.getTime());
//            c.add(Calendar.DAY_OF_YEAR, excludedDaysCount);
//            return getNextWorkDay(c.getTime());
//        } else if (Calendar.HOUR_OF_DAY == field) {
//            c.setTime(getNextWorkDay(c.getTime()));
//            int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
//            if (hourOfDay + value >= closingHour) {
//                int diff = 0;
//                if (closingHour > hourOfDay) {
//                    diff = closingHour - hourOfDay;
//                }
//                c.add(Calendar.HOUR_OF_DAY, closingHour - hourOfDay);
//                c.setTime(getNextWorkDay(c.getTime()));
//                c.set(Calendar.HOUR_OF_DAY, resumptionHour);
//                int minute = c.get(Calendar.MINUTE);
//                c.set(Calendar.MINUTE, minute);
//                c.set(Calendar.SECOND, 0);
//                c.set(Calendar.MILLISECOND, 0);
//                value -= diff;
//            }
//            int days = getNoOfWorkDaysFromHours(value);
//            int remainingHours = getRemainingWorkHoursAfterDays(days, value);
//            c.add(Calendar.DAY_OF_YEAR, days);
//            c.add(Calendar.HOUR_OF_DAY, remainingHours);
//            getNextWorkDay(c.getTime());
//        }
//        return c.getTime();
//    }
}
