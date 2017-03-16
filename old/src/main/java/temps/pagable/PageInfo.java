package temps.pagable;

import java.util.List;

/**
 * @throws UnsupportedOperationException
 * @author Administrator
 *
 */
public class PageInfo implements Page<Object> {

	public PageInfo(int pageSize, int pageIndex, int totalCount) {
		throw new UnsupportedOperationException();
	}

	public int getPageSize() {
		throw new UnsupportedOperationException();
	}

	public int getPageIndex() {
		throw new UnsupportedOperationException();
	}

	public int getTotalCount() {
		throw new UnsupportedOperationException();
	}

	public int getToTalPage() {
		throw new UnsupportedOperationException();
	}

	public List<Object> nextPage() {
		throw new UnsupportedOperationException();
	}

	public boolean hasNextPage() {
		throw new UnsupportedOperationException();
	}

}
