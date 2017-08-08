package com.unbelievable.library.android.volleyplus;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.NoConnectionError;
import com.android.volley.error.ParseError;
import com.android.volley.error.TimeoutError;
import com.android.volley.error.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class BaseTranscation {
	
	protected Map<String, String> params;
	protected Map<String, String> headers;
	protected HttpCallback callback;	
	protected String url;
	protected RequestQueue mQueue;
	protected String TAG = "BaseTranscation";
	// 默认开启缓存
	protected boolean shouldCache =true;
	// 设置是否缓存
	public void setShouldCache(boolean shouldCache) {
		this.shouldCache = shouldCache;
	}
	
	protected boolean forceCache;
	
	/**
	 * 如果设置需要缓存，forceCache说明强制先返回缓存再校验
	 * 可以忽视服务器must-revalidate或proxy-revalidate
	 * 默认按标准http缓存策略，max-age，只要没有过期都走缓存
	 * must-revalidate作用是，下次请求必须先校验资源是否有效
	 * 如果返回302说明，继续使用缓存资源
	 */
	public void setForceCache(boolean forceCache) {
		this.forceCache = forceCache;
	}	
	public boolean isForceCache() {
		return forceCache;
	}

	public abstract String getApiUrl();
	
	protected List<Map.Entry<String, String>> getParamsList() {
		List<Map.Entry<String, String>> infoIds =
				new ArrayList<Map.Entry<String, String>>(params.entrySet());
		Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
				//return (o2.getValue() - o1.getValue());
				return (o1.getKey()).toString().compareTo(o2.getKey());
			}
		});
		return infoIds;
	}	
	
	protected JSONObject getJSONObject() {
		
		JSONObject jb = new JSONObject();
		if(params != null){
			for (ConcurrentHashMap.Entry<String, String> entry : params.entrySet()) {
				try {
					jb.put(entry.getKey(), URLEncoder.encode(entry.getValue(), "UTF-8"));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}		
		return jb;
	}
	
	protected String getParamString() {
		StringBuffer sb = new StringBuffer();
		for(Map.Entry<String, String> entry : getParamsList()){
			sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		String result = sb.toString();
		return result.substring(0,result.length()-1);
	}
	
	protected String getUrlWithQueryString(String url) {
		if (params != null && params.size()>0) {
			String paramString = getParamString();
			url += "?" + paramString;
		}
		return url;
	}	
	
	/**
	 * 错误处理
	 */
	protected Response.ErrorListener errorListener = new Response.ErrorListener(){

		@Override
		public void onErrorResponse(VolleyError error) {
			error.printStackTrace();
			if(callback == null){
				Log.e(TAG,"callback is null!");
				return;
			}			
			if(error != null){
				Log.e(TAG, error.getMessage() != null ? error.getMessage() : "");
				
				if (null != error.networkResponse && error.networkResponse.statusCode == HttpErrorUtil.ERROR_404){
					callback.onFailure(HttpErrorUtil.ERROR_404, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_404), null);
					return;
				}
				
				if(error.getCause() instanceof UnknownHostException){
					callback.onFailure(HttpErrorUtil.ERROR_NETWORK_ERROR, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_NETWORK_ERROR), null);
					return;
				}
				
				if(error.getCause() instanceof JSONException){
					callback.onFailure(HttpErrorUtil.ERROR_PARSE_DATA_EXCEPTION, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_PARSE_DATA_EXCEPTION), null);
					return;
				}
				
				if(error instanceof ParseError){
					callback.onFailure(HttpErrorUtil.ERROR_PARSE_DATA_EXCEPTION, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_PARSE_DATA_EXCEPTION), null);
					return;
				}
				if(error instanceof TimeoutError){
					callback.onFailure(HttpErrorUtil.ERROR_NETWORK_ERROR, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_NETWORK_ERROR), null);
					return;
				}
				if(error instanceof VolleyError){
					callback.onFailure(HttpErrorUtil.ERROR_SERVER_ERROR, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_SERVER_ERROR), null);
					return;
				}
				if(error instanceof NoConnectionError){
                    callback.onFailure(HttpErrorUtil.ERROR_CONN_ERROR, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_CONN_ERROR), null);
                    return;
                }
			}			
			callback.onFailure(HttpErrorUtil.ERROR_UNKNOW, HttpErrorUtil.getErrorMsg(HttpErrorUtil.ERROR_UNKNOW), null);			
		}
	};

	/**
	 * 解析共同的基本数据，包括code、msg、data
	 */
	public ResponseEntity parseBaseData(JSONObject content) throws JSONException {
		ResponseEntity entity = new ResponseEntity();
		int code = HttpErrorUtil.SUCC;
		String msg = "";
		JSONObject jsonObj = content;

		if (!jsonObj.isNull(VolleyPlus.CODE)) {
			code = jsonObj.getInt(VolleyPlus.CODE);

		}

		if (!jsonObj.isNull(VolleyPlus.MSG)) {
			msg = jsonObj.getString(VolleyPlus.MSG);
		}

		if (!jsonObj.isNull(VolleyPlus.DATA)) {
			entity.setInfo(jsonObj.getString(VolleyPlus.DATA));
		}
		entity.setCode(code);
		entity.setMsg(msg);

		return entity;
	}
}
