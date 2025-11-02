package org.biz.slaapi.model;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Data
public class SlaRequest {
    private LocalDateTime startDateTime;
    private double durationHours;
    private Set<DayOfWeek> workingDays;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
}
