/*
 * Copyright (C) 2006-2016 Talend Inc. - www.talend.com
 * 
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 * 
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */

package com.amalto.core.objects.routing;

public class CompletedRoutingOrderV2POJO extends AbstractRoutingOrderV2POJO {

    private static final long serialVersionUID = 9143532531849539340L;

    public CompletedRoutingOrderV2POJO() {
        this.setStatus(COMPLETED);
    }

    public CompletedRoutingOrderV2POJO(AbstractRoutingOrderV2POJO roPOJO) {
        super();
        setName(roPOJO.getName());
        setItemPOJOPK(roPOJO.getItemPOJOPK());
        setMessage(roPOJO.getMessage());
        setStatus(COMPLETED);
        setTimeCreated(roPOJO.getTimeCreated());
        setTimeLastRunCompleted(System.currentTimeMillis());
        setTimeLastRunStarted(roPOJO.getTimeLastRunStarted());
        setTimeScheduled(roPOJO.getTimeScheduled());
        setServiceJNDI(roPOJO.getServiceJNDI());
        setServiceParameters(roPOJO.getServiceParameters());
        setBindingUniverseName(roPOJO.getBindingUniverseName());
        setBindingUserToken(roPOJO.getBindingUserToken());
    }

}
