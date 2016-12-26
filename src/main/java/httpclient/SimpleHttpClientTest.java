package httpclient;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

public class SimpleHttpClientTest {

	public static void main(String[] args) throws IOException {

		HttpClient client = new HttpClient();

		HttpMethod method = getPostMethod(); // 使用 POST 方式提交数据

		method.addRequestHeader("Cookie",
				"sessionid=eebd911b-bdbc-4e7b-aea1-b69ec52b43d1; sessionip=61.153.7.250; area=330199; 2scDealerInfo=DE7A4AC2795BDF359DE8C96B1DE9276799124BD89525AD4C7A583B72E9AF4A7DE049D5305433BE69FEF79229581D5C8F1D8A02096C13FAB3CD66792B53358F43D762FF54378CBD13; 2scDealerName=%e5%90%88%e8%82%a5%e6%98%8e%e9%91%ab%e4%ba%8c%e6%89%8b%e8%bd%a6; 2scDealerId=113985; 2scDealerAId=0; 2scDealerAMobile=; userarea=340100; Hm_lvt_d381ec2f88158113b9b76f14c497ed48=1481810073; Hm_lpvt_d381ec2f88158113b9b76f14c497ed48=1482071710; _ga=GA1.2.648450830.1482071710; sessionuid=eebd911b-bdbc-4e7b-aea1-b69ec52b43d1");

		client.executeMethod(method); // 打印服务器返回的状态
		System.out.println(method.getStatusLine()); // 打印结果页面
		String response = new String(method.getResponseBodyAsString().getBytes("utf-8"));

		// 打印返回的信息
		System.out.println(response);
		method.releaseConnection();

		String data = "2016-12-18 21：30";

		data = beautify(data);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date recommandTime = format.parse(data);
			System.out.println(recommandTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param data
	 * @return
	 */
	private static String beautify(String data) {
		data = data.replaceAll("：", ":");
		return data;
	}

	/**
	 * 
	 * 使用 GET 方式提交数据
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private static HttpMethod getGetMethod() {
		return new GetMethod("/simcard.php?simcard=1330227");
	}

	/**
	 * 使用 POST 方式提交数据
	 * 
	 * @return
	 */
	private static HttpMethod getPostMethod() {
		PostMethod post = new PostMethod(
				"http://dealers.che168.com/Handler/CarManager/DealerRecommendUsedDetailProcess.ashx");
		NameValuePair namepair1 = new NameValuePair("page", "1");
		NameValuePair namepair2 = new NameValuePair("size", "10");
		NameValuePair namepair3 = new NameValuePair("action", "getDeaerRecommendDetail");
		NameValuePair namepair4 = new NameValuePair("stime", "2016-11-18");
		NameValuePair namepair5 = new NameValuePair("etime", "2016-12-18");
		NameValuePair namepair6 = new NameValuePair("dealerid", "113985");
		NameValuePair namepair7 = new NameValuePair("memberId", "0");
		NameValuePair namepair8 = new NameValuePair("brcid", "0");

		NameValuePair[] params = new NameValuePair[8];
		params[0] = namepair1;
		params[1] = namepair2;
		params[2] = namepair3;
		params[3] = namepair4;
		params[4] = namepair5;
		params[5] = namepair6;
		params[6] = namepair7;
		params[7] = namepair8;

		post.setRequestBody(params);
		return post;

	}

	/**
	 * 
	 * 
	 * @param url
	 * @param loginCookie
	 * @param pageId
	 * @param pageSize
	 * @param fromDate
	 * @param toDate
	 * @param dealerid
	 * @return
	 */
	public static String accessByHttpClientPost4latformMarketServiceDetail(String url, String loginCookie,
			String pageId, String pageSize, String fromDate, String toDate, String dealerid) {
		HttpClient client = new HttpClient();
		HttpMethod method = buildPostMethod4PlatformMarketServiceDetail(url, loginCookie, pageId, pageSize, fromDate,
				toDate, dealerid);
		try {
			client.executeMethod(method);
			String response = new String(method.getResponseBodyAsString().getBytes("utf-8"));
			return response;
		} catch (HttpException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * 
	 * @param url
	 * @param loginCookie
	 * @param pageId
	 *            start from 1
	 * @param pageSize
	 * @param fromDate
	 *            yyyy-MM-dd
	 * @param toDate
	 *            yyyy-MM-dd
	 * @param dealerid
	 * @return
	 */
	public static HttpMethod buildPostMethod4PlatformMarketServiceDetail(String url, String loginCookie, String pageId,
			String pageSize, String fromDate, String toDate, String dealerid) {

		NameValuePair namepair1 = new NameValuePair("page", pageId);
		NameValuePair namepair2 = new NameValuePair("size", pageSize);
		NameValuePair namepair3 = new NameValuePair("action", "getDeaerRecommendDetail");
		NameValuePair namepair4 = new NameValuePair("stime", fromDate);
		NameValuePair namepair5 = new NameValuePair("etime", toDate);
		NameValuePair namepair6 = new NameValuePair("dealerid", dealerid);
		NameValuePair namepair7 = new NameValuePair("memberId", "0");
		NameValuePair namepair8 = new NameValuePair("brcid", "0");

		NameValuePair[] params = new NameValuePair[8];
		params[0] = namepair1;
		params[1] = namepair2;
		params[2] = namepair3;
		params[3] = namepair4;
		params[4] = namepair5;
		params[5] = namepair6;
		params[6] = namepair7;
		params[7] = namepair8;

		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Cookie", loginCookie);

		return buildPostMethod(headers, params, url);
	}

	public static HttpMethod buildPostMethod(Map<String, String> headers, NameValuePair[] params, String url) {
		PostMethod post = new PostMethod(url);
		post.addParameters(params);
		for (String headerName : headers.keySet()) {
			post.addRequestHeader(headerName, headers.get(headerName));
		}
		return post;
	}
}
