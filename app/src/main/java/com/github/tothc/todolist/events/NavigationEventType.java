package com.github.tothc.todolist.events;

import java.util.Arrays;

public enum NavigationEventType {
    DISPLAY(0),
    MODIFY(1),
    CREATE(2);

    private final int value;
    NavigationEventType(int value) {
        this.value = value;
    }

    public int getEventTypeIntValue() {
        return value;
    }

    public static NavigationEventType getEventByIntValue(int intValue) {
        for (NavigationEventType item : NavigationEventType.values()) {
            if (item.value == intValue) {
                return item;
            }
        }
        throw new UnsupportedOperationException("Cannot find enum by int value.");
    }
}
