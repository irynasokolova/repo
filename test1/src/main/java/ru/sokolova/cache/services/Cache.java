package ru.sokolova.cache.services;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface Cache<K, V> {

	public void put(K k, V v);

	public default Optional<V> get(K k) {
		return Optional.ofNullable(getStorage().get(k)).map(x -> x.getV());
	};

	public Map<K, Node<K, V>> getStorage();

	public default Integer getStorageSize() {
		return getStorage().size();
	};

	Integer getMaxSize();

	public default String print() {
		return getStorage().entrySet().stream().map(e -> e.getValue().toString()).collect(Collectors.joining(","));
	};
}
