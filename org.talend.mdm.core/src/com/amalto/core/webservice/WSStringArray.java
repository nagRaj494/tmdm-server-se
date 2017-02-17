/*
 * Copyright (C) 2006-2016 Talend Inc. - www.talend.com
 * 
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 * 
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
package com.amalto.core.webservice;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="WSStringArray")
public class WSStringArray {
    protected java.lang.String[] strings;
    
    public WSStringArray() {
    }
    
    public WSStringArray(java.lang.String[] strings) {
        this.strings = strings;
    }
    
    public java.lang.String[] getStrings() {
        return strings;
    }
    
    public void setStrings(java.lang.String[] strings) {
        this.strings = strings;
    }
}