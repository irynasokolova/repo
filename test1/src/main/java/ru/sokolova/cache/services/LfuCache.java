package ru.sokolova.cache.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class LfuCache<K, V> implements Cache<K, V> {

	private Map<K, Node<K, V>> cacheMap = new HashMap<K, Node<K, V>>();
	private Map<K, Integer> frequencyMap = new ConcurrentHashMap<K, Integer>();


	private Integer maxSize;

	public LfuCache(Integer maxSize) {
		super();
		this.maxSize = maxSize;
	  }

	
	@Override
	public void put(K k, V v) {
		Node<K, V> node = new Node<K, V>(k, v);
		cacheMap.remove(k);
		if (cacheMap.size() >= maxSize) {
			K keyToRemove = frequencyMap.entrySet().stream().min((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
			cacheMap.remove(keyToRemove);
			System.out.println("Lfu cache removes node " + keyToRemove);

		}
		if (frequencyMap.containsKey(k)) {
			Integer q = frequencyMap.get(k);
			frequencyMap.remove(k);
			frequencyMap.put(k, ++q);
		} else {
			frequencyMap.put(k, 1);
		}

		cacheMap.put(k, node);
		System.out.println("Lfu cache add node " + node);
	}


	@Override
	public Integer getMaxSize() {
		return maxSize;
	}

	@Override
	public Map<K, Node<K, V>> getStorage() {
		return cacheMap;
	}


}
