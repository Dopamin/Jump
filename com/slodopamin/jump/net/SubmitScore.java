
package com.slodopamin.jump.net;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import android.util.Log;

public class SubmitScore implements Runnable {

	public static int score;
	public static String name;

	@Override
	public void run() {

		HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

		DefaultHttpClient client = new DefaultHttpClient();

		SchemeRegistry registry = new SchemeRegistry();
		SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
		registry.register(new Scheme("https", socketFactory, 443));
		SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
		DefaultHttpClient httpclient = new DefaultHttpClient(mgr, client.getParams());

		// Set verifier     
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		//http post
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://flying-squids.net/admin/jump/submit.php?name=" + name + "&score=" + score);
			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
	}
}
