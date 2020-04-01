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

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

import jdk.nashorn.api.scripting.NashornScriptEngine;

/**
 * @author Christian
 *
 */
@SuppressWarnings("restriction")
public class Require {
    // This overload registers the require function globally in the engine scope
    public static Module enable(NashornScriptEngine engine, Folder folder) throws ScriptException {
        Bindings global = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        return enable(engine, folder, global);
    }

    // This overload registers the require function in a specific Binding. It is useful when re-using the
    // same script engine across multiple threads (each thread should have his own global scope defined
    // through the binding that is passed as an argument).
    public static Module enable(NashornScriptEngine engine, Folder folder, Bindings bindings) throws ScriptException {
        Bindings module = engine.createBindings();
        Bindings exports = engine.createBindings();

        Module created = new Module(engine, folder, new ModuleCache(), "<main>", module, exports, null, null);
        created.setLoaded();

        bindings.put("require", created);
        bindings.put("module", module);
        bindings.put("exports", exports);

        return created;
    }
}
