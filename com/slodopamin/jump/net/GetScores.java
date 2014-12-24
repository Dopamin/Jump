
package com.slodopamin.jump.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

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


public class GetScores implements Runnable {

	private void getData(String result) {
		String[] scores = result.split(",");
		for (int i = 1; i < scores.length; i += 6) {

			//System.out.println(scores[i + 1]);
			String name = scores[i + 1];
			name = name.substring(4);
			name = name.substring(1, name.length() - 1);
			//System.out.println(name);

			//System.out.println(scores[i + 3]);
			String score = scores[i + 3];
			score = score.substring(4);
			//System.out.println(score);

			Scores.addScore(i / 6, new Score(name, score));
		}
	}

	@Override
	public void run() {
		String result = "";

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

		InputStream is = null;

		//http post
		try {
			httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://flying-squids.net/admin/jump/get.php");
			//httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}
		//convert response to string
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();

			result = sb.toString();
			//Log.e("test", result);
		} catch (Exception e) {
			Log.e("log_tag", "Error converting result " + e.toString());
		}

		getData(result);

	}

}
