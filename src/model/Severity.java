package model;

public enum Severity {

    CRITICAL(1),
    HIGH(2),
    MODERATE(3),
    LOW(4);

    private final int priority;

    Severity(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}