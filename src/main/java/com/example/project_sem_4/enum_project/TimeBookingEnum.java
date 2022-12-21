package com.example.project_sem_4.enum_project;

public enum TimeBookingEnum {
    TIME_8H ("8H","08:00:00"),
    TIME_9H ("9H","09:00:00"),
    TIME_10H ("10H","10:00:00"),
    TIME_11H ("11H","11:00:00"),
    TIME_13H ("13H","13:00:00"),
    TIME_14H ("14H","14:00:00"),
    TIME_15H ("15H","15:00:00"),
    TIME_16H ("16H","16:00:00"),
    ;
    public String timeName;
    public String timeValue;
    TimeBookingEnum(String timeName,String timeValue) {
        this.timeName = timeName;
        this.timeValue = timeValue;
    }
}
