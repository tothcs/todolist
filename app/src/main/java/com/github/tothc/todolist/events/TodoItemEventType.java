package com.github.tothc.todolist.events;

public enum TodoItemEventType {
    DISPLAY(0),
    MODIFY(1),
    CREATE(2),
    DELETE(3),
    LIST_TODOS(4);

    private final int value;
    TodoItemEventType(int value) {
        this.value = value;
    }

    public int getEventTypeIntValue() {
        return value;
    }

    public static TodoItemEventType getEventByIntValue(int intValue) {
        for (TodoItemEventType item : TodoItemEventType.values()) {
            if (item.value == intValue) {
                return item;
            }
        }
        throw new UnsupportedOperationException("Cannot find enum by int value.");
    }
}
