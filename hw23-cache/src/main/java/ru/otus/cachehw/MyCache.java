package ru.otus.cachehw;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();
    private final ReferenceQueue<HwListener<K, V>> refQueue = new ReferenceQueue<>();

    @Override
    public void put(K key, V value) {
        cache.putIfAbsent(key, value);
    }

    @Override
    public void remove(K key) {
        cache.remove(key);
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener, refQueue));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        for (int i = 0; i < listeners.size(); i++) {
            var currentListener = listeners.get(i);
            if (listener.equals(currentListener)) {
                listeners.remove(listeners.get(i));
            }
        }
    }
}
