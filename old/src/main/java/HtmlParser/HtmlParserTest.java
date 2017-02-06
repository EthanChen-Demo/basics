package HtmlParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.CssSelectorNodeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.springframework.core.io.ClassPathResource;

import com.google.common.collect.Lists;

/**
 * @author www.baizeju.com
 */
public class HtmlParserTest {

	private static String ENCODE = "GBK";
	private static String path = "script/test.html";

	private static void message(String szMsg) {
		try {
			System.out.println(new String(szMsg.getBytes(ENCODE), System.getProperty("file.encoding")));
		} catch (Exception e) {
		}
	}

	public static String openFile(String szFileName) {
		try {
			BufferedReader bis = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(szFileName)), ENCODE));
			String szContent = "";
			String szTemp;

			while ((szTemp = bis.readLine()) != null) {
				szContent += szTemp + "\n";
			}
			bis.close();
			return szContent;
		} catch (Exception e) {
			return "";
		}
	}

	public static void main1(String[] args) {

		String szContent = openFile("E:/My Sites/HTMLParserTester.html");
		System.out.println(szContent);
		try {

			// Parser parser = Parser.createParser(szContent, ENCODE);

			Parser parser = new Parser(
					(HttpURLConnection) (new URL("http://127.0.0.1:8080/HTMLParserTester.html")).openConnection());

			TextExtractingVisitor visitor = new TextExtractingVisitor();
			parser.visitAllNodesWith(visitor);
			String textInPage = visitor.getExtractedText();

			message(textInPage);

		} catch (Exception e) {
		}
	}

	public static void main(String[] args) throws IOException, ParserException {

		// 1.网页HTML
		InputStreamReader isr = new InputStreamReader(new ClassPathResource(path).getInputStream());
		BufferedReader br = new BufferedReader(isr);

		String s;

		StringBuilder allContent = new StringBuilder();
		while ((s = br.readLine()) != null) {
			allContent = allContent.append(s);
		}

		int pageCount = parsePageCount(allContent.toString());
		System.out.println("pageCount : " + pageCount);
		
		List<PrecisePromotionParseResultRow> currentPageRows = parseHtmlDocument(allContent.toString());
		
		List<PrecisePromotionParseResultRow> results = parseHtmlDocument(allContent.toString());
		System.out.println(results.size());

		PrecisePromotionParseResult<Integer, List<PrecisePromotionParseResultRow>> result = new PrecisePromotionParseResult<Integer, List<HtmlParser.PrecisePromotionParseResultRow>>();
		result.pageCount = pageCount;
		result.curerntPage = currentPageRows;

	}

	private static List<PrecisePromotionParseResultRow> parseHtmlDocument(String allContent) throws ParserException {

		// 使用后HTML Parser 控件
		Parser myParser;
		NodeList nodeList = null;
		myParser = Parser.createParser(allContent, "utf-8");

		NodeFilter tableFilter = new NodeClassFilter(TableTag.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { tableFilter });
		try {

			// 获取标签为table的节点列表
			nodeList = myParser.parse(lastFilter);
			TableTag tag = (TableTag) nodeList.elementAt(0);

			TableRow[] rows = tag.getRows();

			// 循环读取每一行
			List<PrecisePromotionParseResultRow> tableContents = Lists.newArrayListWithCapacity(rows.length);
			for (int j = 1; j < rows.length; j++) {
				TableRow tr = (TableRow) rows[j];
				PrecisePromotionParseResultRow result = PrecisePromotionParseResultRow.buildByTableColumn(tr);
				tableContents.add(result);
			}

			return tableContents;
		} catch (ParserException e) {
			e.printStackTrace();
			throw new RuntimeException("html解析失败！", e);
		}

	}

	/**
	 * 计算分页数。
	 * 
	 * @param myParser
	 * @return
	 * @throws ParserException
	 */
	private static int parsePageCount(String allContent) throws ParserException {

		// 使用后HTML Parser 控件
		Parser myParser;
		myParser = Parser.createParser(allContent, "utf-8");

		int pageCount = 0; // default to be 0
		CssSelectorNodeFilter divFilter = new CssSelectorNodeFilter("div[class='page']");
		NodeList divNodeList = myParser.extractAllNodesThatMatch(divFilter);

		Node nodeDiv = divNodeList.elementAt(0);
		NodeList divChilds = nodeDiv.getChildren();

		if (divChilds.size() > 1) {
			NodeList pageLinkNodes = divChilds.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
			pageCount = pageLinkNodes.size() - 1;
		}

		return pageCount;
	}

}
