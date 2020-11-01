package ru.sokolova.cache.services;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class LruCache<K, V> implements Cache<K, V> {

	private LinkedHashMap<K, Node<K, V>> cacheMap = new LinkedHashMap<K, Node<K, V>>() {

		protected boolean removeEldestEntry(Map.Entry<K, Node<K, V>> eldest) {
			if (size() > maxSize)
				System.out.println("Lru cache removes node " + eldest.getValue().toString());
			return size() > maxSize;
	}};

	private Integer maxSize;

	public LruCache(Integer maxSize) {
		super();
		this.maxSize = maxSize;
	  }

	
	@Override
	public void put(K k, V v) {
		Node<K, V> node = new Node<K, V>(k, v);
		cacheMap.remove(k);
		cacheMap.put(k, node);
		System.out.println("Lru cache add node " + node.toString());

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
