package ru.otus.cachehw;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V value = cache.remove(key);
        notifyListeners(key, value, "remove");
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notifyListeners(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        for (int i = 0; i < listeners.size(); i++) {
            var currentListener = listeners.get(i).get();
            if (listener.equals(currentListener)) {
                listeners.remove(listeners.get(i));
            }
        }
    }

    private void notifyListeners(K key, V value, String action) {
        for (var weakReference : listeners) {
            var listener = weakReference.get();
            if (listener != null) {
                listener.notify(key, null, action);
            } else {
                listeners.remove(weakReference);
            }
        }
    }
}
