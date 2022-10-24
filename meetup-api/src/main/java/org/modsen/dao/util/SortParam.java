package org.modsen.dao.util;

public enum SortParam {
    SUBJECT_ASC("subject:asc", "subject", SortDirection.ASC),
    SUBJECT_DESC("subject:desc", "subject", SortDirection.DESC),
    ORGANIZER_ASC("organizer:asc", "organizer", SortDirection.ASC),
    ORGANIZER_DESC("organizer:desc", "organizer", SortDirection.DESC),
    DATE_ASC("date:asc", "scheduledTime", SortDirection.ASC),
    DATE_DESC("date:desc", "scheduledTime", SortDirection.DESC);

    private final String expression;
    private final String field;
    private final SortDirection direction;

    SortParam(String expression, String field, SortDirection direction) {
        this.expression = expression;
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public String getExpression() {
        return expression;
    }

    public static SortParam fromExpression(String expression) {
        for (SortParam param: SortParam.values()) {
            if (param.getExpression().equalsIgnoreCase(expression)) {
                return param;
            }
        }
        throw new IllegalArgumentException("Wrong sort expression " + expression);
    }
}
