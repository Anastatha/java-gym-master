package ru.yandex.practicum.gym;

import ru.yandex.practicum.gym.model.Coach;
import ru.yandex.practicum.gym.model.DayOfWeek;
import ru.yandex.practicum.gym.model.TimeOfDay;
import ru.yandex.practicum.gym.model.TrainingSession;
import ru.yandex.practicum.gym.utils.CounterOfTrainings;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();
        timetable.computeIfAbsent(dayOfWeek, k -> new TreeMap<>())
                .computeIfAbsent(timeOfDay, k -> new ArrayList<>()).add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimeWithTrainingSession = timetable.get(dayOfWeek);
        if (dayTimeWithTrainingSession == null) {
            return Collections.emptyList();
        }

        List<TrainingSession> trainingSessions = new ArrayList<>();
        for (List<TrainingSession> trainingSession : dayTimeWithTrainingSession.values()) {
            trainingSessions.addAll(trainingSession);
        }
        return trainingSessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //сложность должна быть О(1)
        TreeMap<TimeOfDay, List<TrainingSession>> dayTimeWithTrainingSession = timetable.get(dayOfWeek);
        if (dayTimeWithTrainingSession == null) {
            return Collections.emptyList();
        }

        return dayTimeWithTrainingSession.getOrDefault(timeOfDay, Collections.emptyList());
    }

    public Set<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> counters = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> dayTimeWithTrainingSession : timetable.values()) {
            for (List<TrainingSession> trainingSessions : dayTimeWithTrainingSession.values()) {
                for (TrainingSession session : trainingSessions) {
                    counters.merge(session.getCoach(), 1, Integer::sum);
                }
            }
        }

        Set<CounterOfTrainings> counterOfTrainings = new TreeSet<>();
        for (Map.Entry<Coach, Integer> entry : counters.entrySet()) {
            counterOfTrainings.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }

        return counterOfTrainings;
    }

}
