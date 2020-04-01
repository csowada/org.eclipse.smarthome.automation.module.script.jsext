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
package org.eclipse.smarthome.automation.module.script.jsext;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.config.core.ConfigConstants;
import org.openhab.core.automation.module.script.ScriptEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coveo.nashorn_modules.FilesystemFolder;
import com.coveo.nashorn_modules.Require;

import jdk.nashorn.api.scripting.NashornScriptEngine;

/**
 * @author Christian
 *
 */
@SuppressWarnings("restriction")
// @Component(service = ScriptEngineFactory.class)
public class JsExtScriptEngineFactory2 implements ScriptEngineFactory {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ScriptEngineManager engineManager = new ScriptEngineManager();

    private static String LANGUAGE = "es5";

    public List<String> getLanguages() {
        return Arrays.asList(LANGUAGE);
    }

    @Override
    public void scopeValues(ScriptEngine engine, Map<String, Object> scopeValues) {
        Set<String> expressions = new HashSet<String>();

        for (Entry<String, Object> entry : scopeValues.entrySet()) {
            engine.put(entry.getKey(), entry.getValue());

            if (entry.getValue() instanceof Class) {
                expressions.add(String.format("%s = %s.static;", entry.getKey(), entry.getKey()));
            }
        }
        String scriptToEval = String.join("\n", expressions);
        try {
            engine.eval(scriptToEval);
        } catch (ScriptException e) {
            logger.error("ScriptException while importing scope: {}", e.getMessage());
        }

    }

    @Override
    public ScriptEngine createScriptEngine(String fileExtensionX) {

        String fileExtension = fileExtensionX.replace("es5", "js");

        ScriptEngine engine = engineManager.getEngineByExtension(fileExtension);

        if (engine == null) {
            engine = engineManager.getEngineByName(fileExtension);
        }

        if (engine == null) {
            engine = engineManager.getEngineByMimeType(fileExtension);
        }

        if (engine instanceof NashornScriptEngine) {

            File file = new File(
                    ConfigConstants.getConfigFolder() + File.separator + JsExtScriptFileWatcher.FILE_DIRECTORY);

            FilesystemFolder rootFolder = FilesystemFolder.create(file, "UTF-8");

            try {
                Require.enable((NashornScriptEngine) engine, rootFolder);
            } catch (ScriptException e) {
                logger.error("error!", e);
            }
        }

        return engine;
    }

    // public boolean isSupported(String fileExtension) {
    // return getLanguages().contains(fileExtension);
    // }

    @Override
    public List<@NonNull String> getScriptTypes() {
        List<String> scriptTypes = new ArrayList<>();

        for (javax.script.ScriptEngineFactory f : ENGINE_MANAGER.getEngineFactories()) {
            List<String> extensions = f.getExtensions();

            if (extensions.contains(LANGUAGE)) {
                scriptTypes.addAll(extensions);
                scriptTypes.addAll(f.getMimeTypes());
            }
        }
        return Collections.unmodifiableList(scriptTypes);
    }

}
