package temps.pagable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ��ҳģ�ͣ� ����ҳĿ�꣬ҳ������������ԺͲ������Ժ͹������Ժ�ʵ�ֿ������ݣ� <b>��ԭ�ͽṹ������չ�������ڿ�ʼ����ʵ����ʵ�飡</b>
 * 
 * �ӿڵ�һ�����ʺ�������Ӧ����ʵ��ǰ�����趨�ã� ������ƺͼܹ��ķ��룬Ӧ��Ҫ���ϵ��Ż����ӽ���һĿ�꣡
 * 
 * @author Administrator
 *
 * @param <E>
 */
public interface Page<E> {
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * ҳ��С
	 * 
	 * @return
	 */
	public int getPageSize();

	/**
	 * ��ҳ�š���1��ʼ
	 * 
	 * @return
	 */
	public int getPageIndex();

	/**
	 * ����
	 * 
	 * @return
	 */
	public int getTotalCount();

	/**
	 * ��ҳ��
	 * 
	 * @return
	 */
	public int getToTalPage();

	/**
	 * �ӿ���߲�Ӧ���ṩ�����ķ����� ����Ĵ�����ʹ����÷��뿪��Ҳ�޷��ṩ��
	 */
	// /**
	// *
	// * @param iterable
	// * @return
	// */
	// public Pagable<E> create(Iterable<E> iterable);
	// /**
	// *
	// * @param iterable
	// * @param pageSize
	// * @return
	// */
	// public Pagable<E> create(Iterable<E> iterable, int pageSize);
	// /**
	// *
	// *
	// * @param map
	// * @return
	// */
	// public <V> Pagable<E> create(Map<E,V> map);
	// public <V> Pagable<E> create(Map<E,V> map, int pageSize);
	/**
	 * ������ҳ�Ķ����Ǹ�map,���ص�ǰ��ҳ�� key list
	 * 
	 * @return
	 */
	public List<E> nextPage();

	/**
	 * �Ƿ���������ҳ
	 * 
	 * @return
	 */
	public boolean hasNextPage();

}

class PagableCollection<E> implements Page<E> {
	/**
	 * >0
	 */
	private int pageSize;
	private int pageIndex;
	/**
	 * >= -1.
	 */
	private int totalCount = -1; // -1 not valid
	private int totalPage = -1; // -1 not valid
	private Iterable<E> collection;
	private Iterator<E> iterator;

	public int getPageSize() {
		return this.pageSize;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * 
	 */
	public int getTotalCount() {
		if (totalCount == -1) {
			calculate();
		}

		return this.totalCount;
	}

	private void calculate() {
		totalCount = 0;
		this.totalPage = 0;
		while (iterator.hasNext()) {
			totalCount += 1;
			iterator.next();
		}
		this.totalPage = totalCount / pageSize + totalCount % pageSize == 0 ? 0
				: 1;
	}

	public PagableCollection(Iterable<E> iterable) {
		this(iterable, DEFAULT_PAGE_SIZE);
	}

	private void afterPropertiesSet() {
	}

	public PagableCollection(Iterable<E> iterable, int pageSize) {
		if (iterable == null) {
			throw new IllegalArgumentException(
					"argument iterable can't be null");
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException(
					"argument pageSize can't be nonpositive");
		}

		this.collection = iterable;
		this.pageSize = pageSize;
		this.totalCount = -1;
		this.pageIndex = 1;
		this.iterator = collection.iterator();
		this.afterPropertiesSet();
	}

	public List<E> nextPage() {
		List<E> newPage = new ArrayList<E>();
		int offset4Page = 0;
		int maxOffSet4Page = pageSize - 1;
		while (iterator.hasNext() && offset4Page < maxOffSet4Page) {
			E element = iterator.next();
			newPage.add(element);
		}
		return newPage;
	}

	public boolean hasNextPage() {
		return iterator.hasNext();
	}

	public int getToTalPage() {
		if (this.totalPage == -1) {
			calculate();
		}
		return this.totalPage;
	}
}

class PagableMap<E> implements Page<E> {
	/**
	 * > 0
	 */
	private int pageSize;
	/**
	 * start from 1
	 */
	private int pageIndex;
	/**
	 * >= -1. -1 not valid
	 */
	private int totalCount;
	/**
	 * >= -1. -1 not valid
	 */
	private int totalPage;
	private Iterator<E> iterator;

	public int getPageSize() {
		return this.pageSize;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	/**
	 * 
	 */
	public int getTotalCount() {
		if (totalCount == -1) {
			calculate();
		}

		return this.totalCount;
	}

	private void calculate() {
		totalCount = 0;
		this.totalPage = 0;
		while (iterator.hasNext()) {
			totalCount += 1;
			iterator.next();
		}
		this.totalPage = totalCount / pageSize + totalCount % pageSize == 0 ? 0
				: 1;
	}

	private void afterPropertiesSet() {
	}

	public <V> PagableMap(Map<E, V> map) {
		this(map, DEFAULT_PAGE_SIZE);
	}

	public <V> PagableMap(Map<E, V> map, int pageSize) {
		if (map == null) {
			throw new IllegalArgumentException("argument map can't be null");
		}
		if (pageSize <= 0) {
			throw new IllegalArgumentException(
					"argument pageSize can't be nonpositive");
		}

		this.pageSize = pageSize;
		this.totalCount = -1;
		this.pageIndex = 1;
		this.iterator = map.keySet().iterator();
		this.afterPropertiesSet();
	}

	public List<E> nextPage() {
		List<E> newPage = new ArrayList<E>();
		int offset4Page = 0;
		int maxOffSet4Page = pageSize - 1;
		while (iterator.hasNext() && offset4Page < maxOffSet4Page) {
			E element = iterator.next();
			newPage.add(element);
		}
		return newPage;
	}

	public boolean hasNextPage() {
		return iterator.hasNext();
	}

	public int getToTalPage() {
		if (this.totalPage == -1) {
			calculate();
		}
		return this.totalPage;
	}
}
