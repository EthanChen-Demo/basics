package temps.pagable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 分页模型： 被分页目标，页两个概念及其属性和操作属性和关联属性和实现控制数据！ <b>从原型结构进行扩展：从现在开始进行实践和实验！</b>
 * 
 * 接口的一切性质和特征都应该在实现前进行设定好： 这是设计和架构的范畴，应该要不断的优化，接近这一目标！
 * 
 * @author Administrator
 *
 * @param <E>
 */
public interface Page<E> {
	public static final int DEFAULT_PAGE_SIZE = 10;

	/**
	 * 页大小
	 * 
	 * @return
	 */
	public int getPageSize();

	/**
	 * 分页号。从1开始
	 * 
	 * @return
	 */
	public int getPageIndex();

	/**
	 * 总数
	 * 
	 * @return
	 */
	public int getTotalCount();

	/**
	 * 总页数
	 * 
	 * @return
	 */
	public int getToTalPage();

	/**
	 * 接口里边不应该提供创建的方法！ 组件的创建和使用最好分离开！也无法提供！
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
	 * 若被分页的对象是个map,返回当前分页的 key list
	 * 
	 * @return
	 */
	public List<E> nextPage();

	/**
	 * 是否有其他的页
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
