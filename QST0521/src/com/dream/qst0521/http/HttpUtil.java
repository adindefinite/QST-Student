package com.dream.qst0521.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil {
	 //实例话对象
    private static AsyncHttpClient client =new AsyncHttpClient();
    static {
        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
    }

    //用一个完整url获取一个string对象
    public static void get(String urlString,AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }
    //url里面带参数
    public static void get(String urlString, RequestParams params, AsyncHttpResponseHandler res)
     {
        client.get(urlString, params,res);
     }
    //不带参数，获取json对象或者数组()
    public static void get(String urlString,JsonHttpResponseHandler res)
    {
    client.get(urlString,res);
    }
    //带参数，获取json对象或者数组()
    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res)
    {
        client.get(urlString, params,res);
    }
    //下载数据使用，会返回byte数据
    public static void get( String uString, BinaryHttpResponseHandler bHandler)
    {
        client.get(uString, bHandler);
    }
    public static AsyncHttpClient getClient()
    {
        return client;
    }

	
    //封装的发送请求函数
	/*
	 * public static void sendHttpRequest(final String address, final
	 * HttpCallbackListener listener) { if (!HttpUtil.isNetworkAvailable()){
	 * //这里写相应的网络设置处理 return; } new Thread(new Runnable() {
	 * 
	 * @Override public void run() { HttpURLConnection connection = null; try{ URL
	 * url = new URL(address); //使用HttpURLConnection connection =
	 * (HttpURLConnection) url.openConnection(); //设置方法和参数
	 * connection.setRequestMethod("GET"); connection.setConnectTimeout(8000);
	 * connection.setReadTimeout(8000); connection.setDoInput(true);
	 * connection.setDoOutput(true); //获取返回结果 InputStream inputStream =
	 * connection.getInputStream(); BufferedReader reader = new BufferedReader(new
	 * InputStreamReader(inputStream)); StringBuilder response = new
	 * StringBuilder(); String line; while ((line = reader.readLine()) != null){
	 * response.append(line); } //成功则回调onFinish if (listener != null){
	 * listener.onFinish(response.toString()); } } catch (Exception e) {
	 * e.printStackTrace(); //出现异常则回调onError if (listener != null){
	 * listener.onError(e); } }finally { if (connection != null){
	 * connection.disconnect(); } } } }).start(); }
	 * 
	 * //组装出带参数的完整URL public static String getURLWithParams(String
	 * address,HashMap<String,String> params) throws UnsupportedEncodingException {
	 * //设置编码 final String encode = "UTF-8"; StringBuilder url = new
	 * StringBuilder(address); url.append("?"); //将map中的key，value构造进入URL中
	 * for(Map.Entry<String, String> entry:params.entrySet()) {
	 * url.append(entry.getKey()).append("=");
	 * url.append(URLEncoder.encode(entry.getValue(), encode)); url.append("&"); }
	 * //删掉最后一个& url.deleteCharAt(url.length() - 1); return url.toString(); }
	 * 
	 * //判断当前网络是否可用 public static boolean isNetworkAvailable(){ //这里检查网络，后续再添加
	 * return true; }
	 */
}
