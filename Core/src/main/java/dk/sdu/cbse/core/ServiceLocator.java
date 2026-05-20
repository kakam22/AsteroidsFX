package dk.sdu.cbse.core;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

public enum ServiceLocator {
    INSTANCE;

    private final ModuleLayer pluginLayer;

    ServiceLocator() {
        pluginLayer = createPluginLayer();
    }

    public <T> Collection<? extends T> locateAll(Class<T> service) {
        Map<String, T> services = new LinkedHashMap<>();

        ServiceLoader.load(service).stream()
                .map(ServiceLoader.Provider::get)
                .forEach(provider -> services.put(provider.getClass().getName(), provider));

        if (pluginLayer != null) {
            ServiceLoader.load(pluginLayer, service).stream()
                    .map(ServiceLoader.Provider::get)
                    .forEach(provider -> services.put(provider.getClass().getName(), provider));
        }

        return new ArrayList<>(services.values());
    }

    private ModuleLayer createPluginLayer() {
        Path modulePath = Path.of("mods-mvn");
        if (!Files.isDirectory(modulePath)) {
            return null;
        }

        ModuleFinder finder = ModuleFinder.of(modulePath);
        Set<String> bootModules = ModuleLayer.boot().modules().stream()
                .map(Module::getName)
                .collect(Collectors.toSet());

        Set<String> pluginModules = finder.findAll().stream()
                .map(ModuleReference::descriptor)
                .map(descriptor -> descriptor.name())
                .filter(moduleName -> !bootModules.contains(moduleName))
                .filter(moduleName -> !moduleName.startsWith("javafx."))
                .collect(Collectors.toSet());

        if (pluginModules.isEmpty()) {
            return null;
        }

        Configuration configuration = ModuleLayer.boot()
                .configuration()
                .resolve(finder, ModuleFinder.of(), pluginModules);

        return ModuleLayer.boot()
                .defineModulesWithOneLoader(configuration, ClassLoader.getSystemClassLoader());
    }
}
