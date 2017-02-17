/*
 * Copyright (C) 2006-2016 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement
 * along with this program; if not, write to Talend SA
 * 9 rue Pages 92150 Suresnes, France
 */

package com.amalto.core.query.user;

/**
 *
 */
public class OrderBy implements Expression {

    public static enum Direction {
        ASC,
        DESC
    }

    private final TypedExpression expression;

    private final Direction direction;

    public OrderBy(TypedExpression expression, Direction direction) {
        this.expression = expression;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public TypedExpression getExpression() {
        return expression;
    }

    public Expression normalize() {
        return this;
    }

    @Override
    public boolean cache() {
        return false;
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrderBy)) {
            return false;
        }
        OrderBy orderBy = (OrderBy) o;
        if (direction != orderBy.direction) {
            return false;
        }
        if (expression != null ? !expression.equals(orderBy.expression) : orderBy.expression != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = expression != null ? expression.hashCode() : 0;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }
}
