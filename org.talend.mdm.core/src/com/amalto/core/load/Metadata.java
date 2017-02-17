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

package com.amalto.core.load;

/**
 *
 */
public interface Metadata {
    void setContainer(String container);

    void setName(String name);

    void setDmn(String dmn);

    String[] getId();

    String getContainer();

    String getName();

    String getDMR();

    String getSP();

    String getTaskId();

    String getVersion();

    String getDMN();

    void setId(String idElementName, String id);

    void reset();

    String getDataClusterName();

    void setDataClusterName(String dataClusterName);
}
