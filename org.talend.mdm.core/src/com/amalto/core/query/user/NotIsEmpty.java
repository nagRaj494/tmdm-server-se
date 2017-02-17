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

public class NotIsEmpty implements Condition {

    private final TypedExpression field;

    public NotIsEmpty(TypedExpression field) {
        this.field = field;
    }

    public TypedExpression getField() {
        return field;
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
        if (!(o instanceof NotIsEmpty)) {
            return false;
        }
        NotIsEmpty that = (NotIsEmpty) o;
        return !(field != null ? !field.equals(that.field) : that.field != null);
    }

    @Override
    public int hashCode() {
        return field != null ? field.hashCode() : 0;
    }
}
