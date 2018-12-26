package org.openhab.binding.nashorn;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.eclipse.smarthome.automation.module.script.ScriptEngineFactory;
import org.eclipse.smarthome.config.core.ConfigConstants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coveo.nashorn_modules.FilesystemFolder;
import com.coveo.nashorn_modules.Require;

import jdk.nashorn.api.scripting.NashornScriptEngine;

@SuppressWarnings("restriction")
@Component(service = ScriptEngineFactory.class)
public class ExtScriptEngineFactory implements ScriptEngineFactory {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ScriptEngineManager engineManager = new ScriptEngineManager();

    @Override
    public List<String> getLanguages() {
        return Arrays.asList("js", "javascript", "application/javascript");
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
    public ScriptEngine createScriptEngine(String fileExtension) {

        // NashornScriptEngine engine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
        ScriptEngine engine = engineManager.getEngineByExtension(fileExtension);

        if (engine == null) {
            engine = engineManager.getEngineByName(fileExtension);
        }

        if (engine == null) {
            engine = engineManager.getEngineByMimeType(fileExtension);
        }

        if (engine instanceof NashornScriptEngine) {

            File file = new File(
                    ConfigConstants.getConfigFolder() + File.separator + ExtScriptFileWatcher.FILE_DIRECTORY);

            FilesystemFolder rootFolder = FilesystemFolder.create(file, "UTF-8");

            try {
                Require.enable((NashornScriptEngine) engine, rootFolder);

                // engine.eval("var global = this;\n" + "var window = this;\n" + "var process = {env: {}};\n" + "\n"
                // + "var console = {};\n" + "console.debug = print;\n" + "console.warn = print;\n"
                // + "console.loggy = print;");

            } catch (ScriptException e) {
                logger.error("error!", e);
            }
        }

        return engine;
    }

    @Override
    public boolean isSupported(String fileExtension) {
        return getLanguages().contains(fileExtension);
    }

}
