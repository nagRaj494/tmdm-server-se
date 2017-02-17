/*
 * Copyright (C) 2006-2016 Talend Inc. - www.talend.com
 * 
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 * 
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */

package com.amalto.core.storage.hibernate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.talend.mdm.commmon.metadata.ComplexTypeMetadata;
import org.talend.mdm.commmon.metadata.ContainedTypeFieldMetadata;
import org.talend.mdm.commmon.metadata.DefaultMetadataVisitor;
import org.talend.mdm.commmon.metadata.EnumerationFieldMetadata;
import org.talend.mdm.commmon.metadata.FieldMetadata;
import org.talend.mdm.commmon.metadata.MetadataUtils;
import org.talend.mdm.commmon.metadata.ReferenceFieldMetadata;
import org.talend.mdm.commmon.metadata.SimpleTypeFieldMetadata;

import com.amalto.core.storage.datasource.RDBMSDataSource;

public class StatefulContext implements MappingCreatorContext {

    private final Map<FieldMetadata, String> enforcedUniqueNames = new HashMap<FieldMetadata, String>();

    private final AtomicInteger uniqueInheritanceCounter = new AtomicInteger();

    private final RDBMSDataSource.DataSourceDialect dialect;

    public StatefulContext(RDBMSDataSource.DataSourceDialect dialect) {
        this.dialect = dialect;
    }

    @Override
    public String getFieldColumn(FieldMetadata field) {
        if (!field.getContainingType().getSuperTypes().isEmpty() && !field.getContainingType().isInstantiable()) {
            boolean isUnique = isUniqueWithinTypeHierarchy(field.getContainingType(), field.getName());
            if (field.getDeclaringType().equals(field.getContainingType()) && !isUnique) {
                // Non instantiable types are mapped using a "table per hierarchy" strategy, if field name isn't unique
                // make sure name becomes unique to avoid conflict (Hibernate doesn't issue warning/errors in case of
                // overlap).
                synchronized (enforcedUniqueNames) {
                    String enforcedUniqueName = enforcedUniqueNames.get(field);
                    if (enforcedUniqueName == null) {
                        enforcedUniqueName = getFieldColumn(field.getName()) + uniqueInheritanceCounter.incrementAndGet();
                        enforcedUniqueNames.put(field, enforcedUniqueName);
                    }
                    return enforcedUniqueName;
                }
            } else {
                return getFieldColumn(field.getName());
            }
        } else {
            return getFieldColumn(field.getName());
        }
    }

    /**
     * Controls whether a field name is unique within type hierarchy accessible from <code>type</code> (i.e. go to the
     * top level type and recursively checks for field with name <code>name</code>).
     * 
     * @param type A type part of an inheritance hierarchy
     * @param name A field name.
     * @return <code>true</code> if there's no other field named <code>name</code> in the type hierarchy accessible from
     * <code>type</code>.
     */
    private static boolean isUniqueWithinTypeHierarchy(ComplexTypeMetadata type, final String name) {
        ComplexTypeMetadata topLevelType = (ComplexTypeMetadata) MetadataUtils.getSuperConcreteType(type);
        int occurrenceCount = topLevelType.accept(new DefaultMetadataVisitor<Integer>() {

            int count = 0;

            private void handleField(FieldMetadata simpleField) {
                if (name.equals(simpleField.getName())) {
                    count++;
                }
            }

            @Override
            public Integer visit(FieldMetadata fieldMetadata) {
                handleField(fieldMetadata);
                return count;
            }

            @Override
            public Integer visit(ContainedTypeFieldMetadata containedField) {
                handleField(containedField);
                return count;
            }

            @Override
            public Integer visit(ReferenceFieldMetadata referenceField) {
                handleField(referenceField);
                return count;
            }

            @Override
            public Integer visit(SimpleTypeFieldMetadata simpleField) {
                handleField(simpleField);
                return count;
            }

            @Override
            public Integer visit(EnumerationFieldMetadata enumField) {
                handleField(enumField);
                return count;
            }

            @Override
            public Integer visit(ComplexTypeMetadata complexType) {
                if (complexType.getSubTypes().size() < 1) {
                    super.visit(complexType);
                }
                for (ComplexTypeMetadata subType : complexType.getSubTypes()) {
                    subType.accept(this);
                }
                return count;
            }
        });
        return occurrenceCount <= 1;
    }

    @Override
    public String getFieldColumn(String name) {
        return "x_" + name.replace('-', '_').toLowerCase(); //$NON-NLS-1$
    }

    @Override
    public int getTextLimit() {
        return dialect.getTextLimit();
    }
}
