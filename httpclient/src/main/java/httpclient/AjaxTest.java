package httpclient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * untested
 * 
 * @author ethan
 *
 */
public class AjaxTest {

	public void requestGet(String urlWithParams) throws Exception {
		CloseableHttpClient httpclient = HttpClientBuilder.create().build();

		// HttpGet httpget = new HttpGet("http://www.baidu.com/");
		HttpGet httpget = new HttpGet(urlWithParams);
		// 配置请求的超时设置
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(50).setConnectTimeout(50)
				.setSocketTimeout(50).build();
		httpget.setConfig(requestConfig);

		CloseableHttpResponse response = httpclient.execute(httpget);
		System.out.println("StatusCode -> " + response.getStatusLine().getStatusCode());

		HttpEntity entity = response.getEntity();
		String jsonStr = EntityUtils.toString(entity);// , "utf-8");
		System.out.println(jsonStr);

		httpget.releaseConnection();

	}

	public static void requestPost(String url,List<NameValuePair> params) throws ClientProtocolException, IOException {
	    CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	         
	    HttpPost httppost = new HttpPost(url);
	        httppost.setEntity(new UrlEncodedFormEntity(params));
	         
	        CloseableHttpResponse response = httpclient.execute(httppost);
	        System.out.println(response.toString());
	         
	        HttpEntity entity = response.getEntity();
	        String jsonStr = EntityUtils.toString(entity, "utf-8");
	        System.out.println(jsonStr);
	         
	        httppost.releaseConnection();
	}
	/**
	 * 
	 * untested
	 * @param loginCookie
	 * @param url
	 * @param params
	 * @return
	 */
	public String requestPost(String loginCookie, String url, List<NameValuePair> params) {
		throw new UnsupportedOperationException();
	}

	public static void main(String[] args) {
		try {
			String loginUrl = "http://dealers.che168.com/login.html";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", "合肥明鑫二手车"));
			params.add(new BasicNameValuePair("pwd", "hefeimingxin168"));

			requestPost(loginUrl, params);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

