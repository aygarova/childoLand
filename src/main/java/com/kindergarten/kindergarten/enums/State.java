package com.kindergarten.kindergarten.enums;

public enum State {
        AT_KINDERGARTEN("В детската градина"),
        AT_HOME("Взето от детската градина"),
        EAT("Хапване"),
        SLEEP("Сън"),
        GAME("Игра"),
        STUDY("Учебни занимания"),
        DO_NOT_FEEL_GOOD("Не се чувства добре"),
        TEMPERATURE("Температура"),
        COUGH("Кашлица"),
        RUNNY_NOSE("Хрема");

        private final String description;

        State(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
}
