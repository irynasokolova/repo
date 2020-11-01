package ru.sokolova.cache.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LruCacheTest {
	private static final Integer MAX_SIZE = 4;

	private LruCache<Integer, Integer> lruCache = new LruCache<Integer, Integer>(MAX_SIZE);

	private LfuCache<Integer, Integer> lfuCache = new LfuCache<Integer, Integer>(MAX_SIZE);

	@Test
	public void testCache() {
		System.out.println("fill Lru cache");

		fillCache(lruCache);
		System.out.println("Lru cache, size " + lruCache.getStorageSize() + ": " + lruCache.print());

		System.out.println("fill Lfu cache");
		fillCache(lfuCache);
		System.out.println("Lfu cache, size " + lfuCache.getStorageSize() + ": " + lfuCache.print());

		//test max size
		assertTrue(lruCache.getStorageSize() <= MAX_SIZE, "LRU Storage size must be equal " + MAX_SIZE);
		assertTrue(lfuCache.getStorageSize() <= MAX_SIZE, "LRU Storage size must be equal " + MAX_SIZE);

		//contains key 1 last value 600
		assertTrue(lruCache.get(1).isPresent() && lruCache.get(1).get().equals(600), "LRU storage doesn't comtains [1-600]");
		assertTrue(lfuCache.get(1).isPresent() && lfuCache.get(1).get().equals(600), "LFU storage doesn't comtains [1-600]");

		//contains last oversize key 5
		assertTrue(lruCache.get(5).isPresent() && lruCache.get(5).get().equals(100), "LRU storage doesn't comtains [5-100]");
		assertTrue(lfuCache.get(5).isPresent() && lfuCache.get(5).get().equals(100), "LFU storage doesn't comtains [5-100]");

		//LRU lost key number 2, it the earliest, but LFU contains, it counts 2 times
		assertFalse(lruCache.get(2).isPresent(), "LRU storage comtains [2-200]");
		assertTrue(lfuCache.get(2).isPresent(), "LFU storage doesn't comtain 2-200]");

		//LRU must contains key number 3, it isn't the earliest, but LFU lost it - it counts 1 times	
		assertTrue(lruCache.get(3).isPresent(), "LRU storage doesn't comtain [3-100]");
		assertFalse(lfuCache.get(3).isPresent(), "LFU storage comtains [3-100]");
	}

	private void fillCache(Cache<Integer, Integer> cache) {
		cache.put(1, 100);
		cache.put(2, 100);
		cache.put(2, 200);
		cache.put(3, 100);
		cache.put(4, 100);
		cache.put(4, 100);
		cache.put(4, 500);
		cache.put(1, 100);
		cache.put(1, 600);
		cache.put(5, 100);
	}
}
