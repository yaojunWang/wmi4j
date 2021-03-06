/*
 * Copyright 2014-2014 Chen Lichao
 *
 * Licensed to the Apache  Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.chenlichao.wmi4j;

import org.apache.commons.lang3.StringUtils;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.IJIComObject;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.jinterop.dcom.impls.JIObjectFactory;
import org.jinterop.dcom.impls.automation.IJIDispatch;

/**
 * An SWbemObjectSet object is a collection of {@link SWbemObject} objects.
 *
 * You can get an SWbemObjectSet object by calling any of the following methods or their asynchronous equivalents:
 *
 * <ul>
 *     <li>{@link SWbemObject#associators(String, String, String, String, Boolean, Boolean, String, String, SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.AssociatorsFlag...) SWbemObject.associators}</li>
 *     <li>{@link SWbemObject#instances(SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.InstancesFlag...) SWbemObject.instances}</li>
 *     <li>{@link SWbemObject#references(String, String, Boolean, Boolean, String, SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.ReferenceFlag...) SWbemObject.references}</li>
 *     <li>{@link SWbemObject#subclasses(SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.SubclassesFlag...) SWbemObject.subclasses}</li>
 *     <li>{@link SWbemServices#associatorsOf(String, String, String, String, String, Boolean, Boolean, String, String, SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.AssociatorsFlag...) SWbemService.associatorsOf}</li>
 *     <li>{@linkplain SWbemServices#execQuery(String, String, SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.ExecQueryFlag...) SWbemServices.execQuery}</li>
 *     <li>{@linkplain SWbemServices#instancesOf(String, SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.InstancesFlag...) SWbemServices.instancesOf}</li>
 *     <li>{@link SWbemServices#referencesTo(String, String, String, Boolean, Boolean, String, SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.ReferenceFlag...) SWbemServices.referencesTo}</li>
 *     <li>{@link SWbemServices#subclassesOf(String, SWbemNamedValueSet, cn.chenlichao.wmi4j.consts.Flags.SubclassesFlag...) SWbemServices.subclassesOf}</li>
 * </ul>
 *
 * <p><strong>Note:</strong>  The SWbemObjectSet object does not support the optional Add and Remove collection methods.</p>
 * <p><strong>Note:</strong>  Because the call-back to the sink might not be returned at the same authentication level as the client requires, it is recommended that you use semisynchronous instead of asynchronous communication. For more information, see Calling a Method.</p>
 *
 * Created by chenlichao on 14-7-22.
 */
public class SWbemObjectSet extends AbstractWbemSet<SWbemObject> {
    SWbemObjectSet(IJIDispatch dispatch) {
        super(dispatch);
    }

    /**
     * The Item method of the SWbemObjectSet object gets an {@link SWbemObject} from the collection.
     * @param objectPath Relative path of the object to retrieve from the collection. Example: Win32_LogicalDisk="C:"
     * @return If successful, the requested {@link SWbemObject} object returns.
     * @throws WMIException
     * @exception  IllegalArgumentException Object path is empty.
     */
    public SWbemObject item(String objectPath) throws WMIException {
        if(StringUtils.isEmpty(objectPath)) {
            throw new IllegalArgumentException("Object path is empty.");
        }
        return callMethod(SWbemObject.class, "Item", new JIString(objectPath));
    }

    /**
     * The itemIndex method returns the {@link SWbemObject} associated with the specified index into the collection.
     * The index indicates the position of the element within the collection. Collection numbering starts at zero.
     * <p><strong>Important: This method only supported by Windows Server 2008 or higher versions.</strong></p>
     * @param index Index of the item in the collection.
     * @return If successful, the requested {@link SWbemObject} object returns.
     * @throws WMIException
     */
    public SWbemObject itemIndex(int index) throws WMIException {
        if(index < 0) {
            throw new IllegalArgumentException("Index starts with zero.");
        }
        return callMethod(SWbemObject.class, "ItemIndex", index);
    }

    /**
     * The security property is used to read, or set the security settings.
     * This property is an {@link SWbemSecurity} object. The security settings in this object do not indicate the authentication,
     * impersonation, or privilege settings made on a connection to Windows Management Instrumentation (WMI),
     * or the security in effect for the proxy when an object is delivered to a sink in an asynchronous call.
     * <p><strong>Note: </strong> Setting the Security_ property of an SWbemObject object to NULL grants unlimited access to everyone all the time.
     * For more information, see {@link SWbemSecurity}.</p>
     * @return The security settings of this WMI object.
     * @throws WMIException
     */
    public SWbemSecurity getSecurity() throws WMIException {
        try {
            JIVariant result = dispatch.get("Security_");
            IJIComObject comObject = result.getObjectAsComObject();
            IJIDispatch securityDispatch = (IJIDispatch) JIObjectFactory.narrowObject(comObject);
            return new SWbemSecurity(securityDispatch);
        } catch (JIException e) {
            throw new WMIException(e);
        }
    }
}
