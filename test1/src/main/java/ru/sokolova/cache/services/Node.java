package ru.sokolova.cache.services;

public class Node<K, V> {
	private final K k;
	private final V v;

	public Node() {
		this.k = null;
		this.v = null;
	};

	public K getK() {
		return k;
	}

	public Node(K k, V v) {
		this.k = k;
		this.v = v;
	}

	public V getV() {
		return v;
	}

	@Override
	public String toString() {
		return "[k=" + k + ", v=" + v + "]";
	}

}
