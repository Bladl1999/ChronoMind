package org.valor.enums;

public enum PriorityTaskEnum {
    LOW(0),
    MIDDLE(1),
    HIGH(2);

    private final Integer priority;

    PriorityTaskEnum(Integer priority) {
        this.priority = priority;
    }

    public static PriorityTaskEnum getPriorityTask(Integer priority) {
       switch (priority) {
           case 1 -> {
               return MIDDLE;
           }
           case 2 -> {
               return HIGH;
           }
           default -> {
               return LOW;
           }
       }
    }

    public Integer getPriority() {
        return priority;
    }
}
