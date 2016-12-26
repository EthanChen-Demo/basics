package excel.io;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class DealerShopImportTest {

	private static String filePath = "script/top100.xlsx";

	private static final Log log = LogFactory.getLog(DealerShopImportTest.class);

	@Test
	public void testImport() {

		List<String> shopNames = getShopNames(filePath);

		log.info(shopNames);

	}

	private List<String> getShopNames(String filePath) {
		ExcelReader reader = new ExcelReader();
		InputStream excelStream = reader.getClasspathResourceAsStream(filePath);

		String[] rowHeaders = reader.readExcelTitle(excelStream);
		java.util.Map<Integer, String[]> contents = reader.readExcelContent(excelStream);

		log.info("rowHeaders: " + rowHeaders);
		for (int i = 0; i < rowHeaders.length; i++) {
			log.info("rowHeaderx: " + rowHeaders[i]);
		}

		List<String> shopNames = Lists.newArrayList();
		log.info("contents:+ " + contents);
		for (Integer rowId : contents.keySet()) {

			String[] row = contents.get(rowId);
			shopNames.add(row[0]);
			log.info("name : " + row[0]);

		}
		return shopNames;
	}
	
}

