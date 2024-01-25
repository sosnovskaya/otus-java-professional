package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.otus.utils.ReflectionUtils.getAnnotatedMethods;
import static ru.otus.utils.ReflectionUtils.invokeMethod;
import static ru.otus.utils.ReflectionUtils.newInstance;

@SuppressWarnings("squid:S1068")
public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final static List<Object> appComponents = new ArrayList<>();
    private final static Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> assignableComponents = appComponents.stream().filter(component -> componentClass.isAssignableFrom(component.getClass())).toList();
        if (assignableComponents.isEmpty()) {
            throw new RuntimeException(String.format("%s component is not found in the context", componentClass));
        } else if (assignableComponents.size() > 1) {
            throw new RuntimeException(String.format("Found more then 1 option for the component %s", componentClass));
        } else {
            return (C) assignableComponents.get(0);
        }
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new RuntimeException(String.format("%s component is not found in the context", componentName));
        }
        return (C) component;
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        getAnnotatedMethods(configClass, AppComponent.class).stream()
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> {
                    var componentName = method.getAnnotation(AppComponent.class).name();
                    var component = invokeMethod(newInstance(configClass), method, getComponents(method.getParameterTypes()));
                    addComponent(componentName, component);
                });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object[] getComponents(Class<?>[] types) {
        return Arrays.stream(types)
                .map(this::getAppComponent)
                .toArray();
    }

    private void addComponent(String componentName, Object component) {
        if(appComponentsByName.containsKey(componentName)) {
            throw new RuntimeException(String.format("Found more then 1 component with name %s", componentName));
        }
        appComponentsByName.put(componentName, component);
        appComponents.add(component);
    }
}
