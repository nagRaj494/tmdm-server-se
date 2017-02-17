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

package com.amalto.core.history;

import com.amalto.core.history.accessor.Accessor;
import com.amalto.core.history.accessor.NoOpAccessor;
import org.apache.commons.lang.StringUtils;
import org.talend.mdm.commmon.metadata.ComplexTypeMetadata;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
*
*/
public class EmptyDocument implements MutableDocument {

    public static final MutableDocument INSTANCE = new EmptyDocument();

    public static final org.w3c.dom.Document EMPTY_DOCUMENT;

    static {
        try {
            EMPTY_DOCUMENT = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private String taskId;

    private EmptyDocument() {
    }

    public String exportToString() {
        return StringUtils.EMPTY;
    }

    public Accessor createAccessor(String path) {
        return NoOpAccessor.INSTANCE;
    }

    public org.w3c.dom.Document asDOM() {
        return EMPTY_DOCUMENT;
    }

    @Override
    public org.w3c.dom.Document asValidationDOM() {
        return EMPTY_DOCUMENT;
    }

    public Document transform(DocumentTransformer transformer) {
        if (transformer == null) {
            throw new IllegalArgumentException("Transformer argument cannot be null");
        }
        return transformer.transform(this);
    }

    public void restore() {
    }

    @Override
    public ComplexTypeMetadata getType() {
        return null;
    }

    @Override
    public String getDataModel() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getDataCluster() {
        return StringUtils.EMPTY;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public DeleteType getDeleteType() {
        return null;
    }

    public MutableDocument create(MutableDocument content) {
        return this;
    }

    public MutableDocument setContent(MutableDocument content) {
        return this;
    }

    public MutableDocument delete(DeleteType deleteType) {
        return this;
    }

    public MutableDocument recover(DeleteType deleteType) {
        return this;
    }

    public Document applyChanges() {
        return this;
    }

    public MutableDocument copy() {
        return this;
    }

    @Override
    public void clean() {
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public boolean considerMissingElementsAsEmpty() {
        return false;
    }
}
