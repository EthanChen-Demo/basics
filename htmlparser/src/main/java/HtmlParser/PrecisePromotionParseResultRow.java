package HtmlParser;

import org.apache.commons.lang3.StringEscapeUtils;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;

public class PrecisePromotionParseResultRow {

	public static int index_promotionTime = 0;
	public static int index_adInfoId = 1;
	public static int index_promotionBudget = 2;
	public static int index_clickPrice = 3;
	public static int index_showClickInfo = 4;
	public static int index_leftBudget = 5;
	public static int index_payWay = 6;
	public static int index_operation = 7;

	/**
	 * 推广时间
	 */
	public String promotionTime;
	/**
	 * 推广车源
	 */
	public String adInfoId;
	/**
	 * 消费明细的头ID
	 */
	public String costInfoId;
	/**
	 * 出价预算
	 */
	public String promotionBudget;
	/**
	 * 平均点击
	 */
	public String clickPrice;
	/**
	 * 点击次数与有效点击率
	 */
	public String showClickInfo;
	/**
	 * 剩余预算
	 */
	public String leftBudget;
	/**
	 * 支付方式
	 */
	public String payWay;
	/**
	 * 操作URL
	 */
	public String operation;

	public static PrecisePromotionParseResultRow buildByTableColumn(TableRow row) {

		TableColumn[] columns = row.getColumns();
		TableColumn column_promotionTime = columns[PrecisePromotionParseResultRow.index_promotionTime];
		TableColumn column_adInfoId = columns[PrecisePromotionParseResultRow.index_adInfoId];
		TableColumn column_promotionBudget = columns[PrecisePromotionParseResultRow.index_promotionBudget];
		TableColumn column_clickPrice = columns[PrecisePromotionParseResultRow.index_clickPrice];
		TableColumn column_showClickInfo = columns[PrecisePromotionParseResultRow.index_showClickInfo];
		TableColumn column_leftBudget = columns[PrecisePromotionParseResultRow.index_leftBudget];
		TableColumn column_payWay = columns[PrecisePromotionParseResultRow.index_payWay];
		@SuppressWarnings("unused")
		TableColumn column_operation = columns[PrecisePromotionParseResultRow.index_operation];
		String promotionTime = column_promotionTime.toPlainTextString();

		InputTag adInfoInput = (InputTag) column_adInfoId.childAt(1);
		String costInfoId = column_adInfoId.getAttribute("id").substring("aCarName_".length());
		String adInfoId = adInfoInput.getAttribute("value");

		String promotionBudget = column_promotionBudget.toPlainTextString();
		String clickPrice = column_clickPrice.toPlainTextString();
		String showClickInfo = column_showClickInfo.toPlainTextString();
		String leftBudget = column_leftBudget.toPlainTextString();
		String payWay = column_payWay.toPlainTextString();

		String operation = "NULL";
		PrecisePromotionParseResultRow resultToRet = new PrecisePromotionParseResultRow();
		resultToRet.promotionTime = StringEscapeUtils.unescapeHtml3(StringUtil.trim(promotionTime));

		resultToRet.adInfoId = StringEscapeUtils.unescapeHtml3(StringUtil.trim(adInfoId));
		resultToRet.costInfoId = costInfoId;
		resultToRet.promotionBudget = StringEscapeUtils.unescapeHtml3(StringUtil.trim(promotionBudget));
		resultToRet.clickPrice = StringEscapeUtils.unescapeHtml3(StringUtil.trim(clickPrice));
		resultToRet.showClickInfo = StringEscapeUtils.unescapeHtml3(StringUtil.trim(showClickInfo));
		resultToRet.leftBudget = StringEscapeUtils.unescapeHtml3(StringUtil.trim(leftBudget));
		resultToRet.payWay = StringEscapeUtils.unescapeHtml3(StringUtil.trim(payWay));
		resultToRet.operation = StringEscapeUtils.unescapeHtml3(StringUtil.trim(operation));

		return resultToRet;
	}

//	@Override
//	public String toString() {
//		com.souche.crawler.service.webDriver.StringMaker maker = com.souche.crawler.service.webDriver.StringMaker
//				.instance().appendEntry("promotionTime", promotionTime).appendEntry("adInfoId", adInfoId)
//				.appendEntry("promotionBudget", promotionBudget).appendEntry("clickPrice", clickPrice)
//				.appendEntry("showClickInfo", showClickInfo).appendEntry("leftBudget", leftBudget)
//				.appendEntry("payWay", payWay).appendEntry("operation", operation);
//		return maker.make();
//	}
	
}

