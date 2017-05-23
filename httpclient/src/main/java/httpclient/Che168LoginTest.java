package httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 
 * untested
 * 
 * @author ethan
 *
 */
public class Che168LoginTest {

	public static void main1(String[] args) {
		// 登陆 Url
		String loginUrl = "http://dealers.che168.com/login.html";

		// 需登陆后访问的 Url
		String dataUrl = "http://dealers.che168.com/Handler/CarFlowLog/GetHotSeriesModelData.ashx?pid=340000&cid=340100&date=201611&pricearea=-1";
		//http://dealers.che168.com/Handler/CarFlowLog/GetHotSeriesModelData.ashx?pid=340000&cid=340100&date=201611&pricearea=-1

		HttpClient httpClient = new HttpClient();

		// 模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式
		PostMethod postMethod = new PostMethod(loginUrl);

		// 设置登陆时要求的信息，用户名和密码
		NameValuePair[] data = { new NameValuePair("name", "admin"), new NameValuePair("password", "123456") };
		postMethod.setRequestBody(data);
		try {
			// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
			httpClient.executeMethod(postMethod);
			// 获得登陆后的 Cookie
			Cookie[] cookies = httpClient.getState().getCookies();
			StringBuffer tmpcookies = new StringBuffer();
			for (Cookie c : cookies) {
				tmpcookies.append(c.toString() + ";");
			}
			// 进行登陆后的操作1581,1602,1603,1610,1609,1608,1607,1606,1605,1620,1619,1617,1616,1622,1626,1642,1648,1647,1657
			GetMethod getMethod = new GetMethod(dataUrl);
			// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
			getMethod.setRequestHeader("cookie", tmpcookies.toString());
			// 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
			// 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
			postMethod.setRequestHeader("Referer", "http://www.cc");
			postMethod.setRequestHeader("User-Agent", "www Spot");
			httpClient.executeMethod(getMethod);
			// 打印出返回数据，检验一下是否成功
			String text = getMethod.getResponseBodyAsString();
			System.out.println(text);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		// 登陆 Url
		String loginUrl = "http://dealers.che168.com/login.html";

		// 需登陆后访问的 Url
		String dataUrl = "http://dealers.che168.com/Handler/CarFlowLog/GetHotSeriesModelData.ashx?pid=340000&cid=340100&date=201611&pricearea=-1";

		try {
			HttpClient httpClient = new HttpClient();
			String loginCookie = "sessionid=eebd911b-bdbc-4e7b-aea1-b69ec52b43d1; sessionip=61.153.7.250; area=330199; sessionuid=eebd911b-bdbc-4e7b-aea1-b69ec52b43d1; userarea=330100; Hm_lvt_d381ec2f88158113b9b76f14c497ed48=1481810073; Hm_lpvt_d381ec2f88158113b9b76f14c497ed48=1481811109; DealerCheckCode=8190188451CB3E86; 2scDealerInfo=DE7A4AC2795BDF359DE8C96B1DE9276799124BD89525AD4C7A583B72E9AF4A7DE049D5305433BE69FEF79229581D5C8F1D8A02096C13FAB3CD66792B53358F43D762FF54378CBD13; 2scDealerName=%e5%90%88%e8%82%a5%e6%98%8e%e9%91%ab%e4%ba%8c%e6%89%8b%e8%bd%a6; 2scDealerId=113985; 2scDealerAId=0; 2scDealerAMobile=; sessionvisit=2fe66bbd-f250-4759-b244-4da6b99d0ec1";
			String result = accessByHttpClientGet(loginCookie , dataUrl);
			System.out.print("result : " + result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 使用http GET 访问后台数据。
	 * @param loginCookie
	 * @param getUrl GET method URL
	 * @return
	 */
	public static String accessByHttpClientGet(String loginCookie, String getUrl) {
		GetMethod getMethod = new GetMethod(getUrl);
		// 每次访问需授权的网址时需带上前面的 cookie 作为通行证
		String tmpcookies = formatLoginCookieAsKV(loginCookie);
		getMethod.setRequestHeader("Cookie", tmpcookies.toString());
		//这几行代码会导致查询结果乱码：具体原因还不清楚。
//		getMethod.setRequestHeader("Accept-Encoding", "gzip, deflate, sdch");
//		getMethod.setRequestHeader("Accept-Language", "zh-CN,zh;q=0.8");
		getMethod.setRequestHeader("Connection", "keep-alive");
		// 打印出返回数据，检验一下是否成功
		String text;
		try {
			HttpClient client = new HttpClient();
			client.executeMethod(getMethod);
			text = getMethod.getResponseBodyAsString();
			return text;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	/**
	 * 转换cookie的格式为K=V;K=V。
	 * untested
	 * @param object
	 * @return
	 */
	private static String formatLoginCookieAsKV(String loginCookies) {
		return loginCookies;
	}

}
