/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006 Sun Microsystems Inc. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://opensso.dev.java.net/public/CDDLv1.0.html or
 * opensso/legal/CDDLv1.0.txt
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at opensso/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * $Id: SetServiceSchemaI18nKey.java,v 1.3 2008/06/25 05:42:19 qcheng Exp $
 *
 */

package com.sun.identity.cli.schema;


import com.sun.identity.cli.CLIException;
import com.sun.identity.cli.ExitCodes;
import com.sun.identity.cli.IArgument;
import com.sun.identity.cli.IOutput;
import com.sun.identity.cli.LogWriter;
import com.sun.identity.cli.RequestContext;
import com.iplanet.sso.SSOException;
import com.sun.identity.sm.SMSException;
import com.sun.identity.sm.ServiceSchemaManager;
import java.text.MessageFormat;
import java.util.logging.Level;

/**
 * Sets service schema i18n key.
 */
public class SetServiceSchemaI18nKey extends SchemaCommand {
    static final String ARGUMENT_I18N_KEY = "i18nkey";
    
    /**
     * Services a Commandline Request.
     *
     * @param rc Request Context.
     * @throws CLIException if the request cannot serviced.
     */
    public void handleRequest(RequestContext rc) 
        throws CLIException {
        super.handleRequest(rc);
        ldapLogin();

        String serviceName = getStringOptionValue(IArgument.SERVICE_NAME);
        String i18nKey = getStringOptionValue(ARGUMENT_I18N_KEY);
        
        ServiceSchemaManager ssm = getServiceSchemaManager();
        IOutput outputWriter = getOutputWriter();

        try {
            String[] params = {serviceName, i18nKey};
            writeLog(LogWriter.LOG_ACCESS, Level.INFO,
                "ATTEMPT_SET_SERVICE_SCHEMA_I18N_KEY", params);
            ssm.setI18NKey(i18nKey);
            writeLog(LogWriter.LOG_ACCESS, Level.INFO,
                "SUCCEED_SET_SERVICE_SCHEMA_I18N_KEY", params);
            outputWriter.printlnMessage(MessageFormat.format(
                getResourceString("service-schema-set-i18n-key-succeed"),
                (Object[])params));
        } catch (SSOException e) {
            String[] args = {serviceName, i18nKey, e.getMessage()};
            debugError("SetServiceSchemaI18nKey.handleRequest", e);
            writeLog(LogWriter.LOG_ERROR, Level.INFO,
                "FAILED_SET_SERVICE_SCHEMA_I18N_KEY", args);
            throw new CLIException(e, ExitCodes.REQUEST_CANNOT_BE_PROCESSED);
        } catch (SMSException e) {
            String[] args = {serviceName, i18nKey, e.getMessage()};
            debugError("SetServiceSchemaI18nKey.handleRequest", e);
            writeLog(LogWriter.LOG_ERROR, Level.INFO,
                "FAILED_SET_SERVICE_SCHEMA_I18N_KEY", args);
            throw new CLIException(e, ExitCodes.REQUEST_CANNOT_BE_PROCESSED);
        }
    }
}
