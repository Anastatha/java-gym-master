package ru.yandex.practicum.gym.utils;

import ru.yandex.practicum.gym.model.Coach;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private Coach coach;
    private int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        int count = Integer.compare(this.count, o.count);
        if (count != 0) return count;

        int surname = this.coach.getSurname()
                .compareTo(o.coach.getSurname());
        if (surname != 0) {
            return surname;
        }

        int name = this.coach.getName()
                .compareTo(o.coach.getName());
        if (name != 0) {
            return name;
        }

        return this.coach.getMiddleName()
                .compareTo(o.coach.getMiddleName());

    }
}
