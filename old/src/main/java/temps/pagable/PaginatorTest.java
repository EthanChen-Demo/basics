package temps.pagable;

import java.util.List;

import com.google.common.collect.Lists;

public class PaginatorTest {
	public static void main(String[] args) {
		List<String> collects = Lists.newArrayList();
		Page<String> strPage = Pages.create(collects);
		int totalCount = strPage.getTotalCount();
		System.out.println(totalCount);
		System.out.println(strPage.getPageIndex());
		System.out.println(strPage.getToTalPage());
		System.out.println(strPage.getPageSize());
		System.out.println(strPage.nextPage());
	}
}
