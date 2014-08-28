package jp.bitcheck.pay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

public class BitcheckPay {
	
	private static final String BASE_URL = "https://settlement.bitcheck.jp/api/v1/";
	
	private String apiKey;
	private HttpClient client;
	private String auth;
	
	private List<NameValuePair> params;
	
	private String redirectURL;
	private String orderID;
	private String userMail;
	
	public BitcheckPay(String apiKey) {
		this.apiKey = apiKey;
		this.auth = new String(Base64.encodeBase64((this.apiKey).getBytes()));
		client = HttpClientBuilder.create().build();

		params = new ArrayList<NameValuePair>();
	}
	
	public String createInvoice(double price,String currency, String itemName) {
		
		String url = BASE_URL + "invoice";
		
		try {
			HttpPost post = new HttpPost(url);
			
			post.addHeader("Authorization", "Basic " + this.auth);
			post.setEntity(new UrlEncodedFormEntity(getParams(price, currency, itemName), "UTF-8"));
			
			HttpResponse response = client.execute(post);
			
			return getContent(response);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	private List<NameValuePair> getParams(double price,String currency, String itemName) {
		params.add(new BasicNameValuePair("price", String.valueOf(price)));
		params.add(new BasicNameValuePair("currency", currency));
		params.add(new BasicNameValuePair("item_name", itemName));
		params.add(new BasicNameValuePair("redirect_url", redirectURL));
		params.add(new BasicNameValuePair("order_id", orderID));
		params.add(new BasicNameValuePair("user_mail", userMail));
		return params;
	}

	private String getContent(HttpResponse response) throws IOException {
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		
		StringBuilder content = new StringBuilder();
		String line;
		
		while (null != (line = rd.readLine())) {
			content.append(line);
		}
		return content.toString();
	}
	
	public void addOption(String name,String value){
		params.add(new BasicNameValuePair(name, value));
	}

	public void setRedirectURL(String redirectURL) {
		this.redirectURL = redirectURL;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	
}
