package temps.pagable;

import java.util.Map;

public class Pages {
	/**
	 * 
	 * @param iterable
	 * @return
	 */
	public static <E> Page<E>  create(Iterable<E> iterable) {
		return new PagableCollection<E>(iterable);
	}
	/**
	 * 
	 * @param iterable
	 * @param pageSize
	 * @return
	 */
	public static <E>  Page<E> create(Iterable<E> iterable, int pageSize) {
		return new PagableCollection<E>(iterable, pageSize);
	}
	/**
	 * @param map
	 * @return
	 */
	public static <E, V> Page<E> create(Map<E,V> map) {
		return new PagableMap<E>(map);
	}
	public static <E, V> Page<E> create(Map<E,V> map, int pageSize) {
		return new PagableMap<E>(map, pageSize);
	}
}
