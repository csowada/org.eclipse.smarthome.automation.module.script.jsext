/**
 * Copyright (c) 2010-2019 Contributors to the openHAB project
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package com.coveo.nashorn_modules;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Christian
 *
 */
public class ModuleCache {
    private Map<String, Module> modules = new HashMap<>();

    public Module get(String fullPath) {
        return modules.get(fullPath);
    }

    public void put(String fullPath, Module module) {
        modules.put(fullPath, module);
    }
}
