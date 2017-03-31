/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.unbelievable.library.nothero.content;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.io.File;
import java.io.IOException;

/**
 * URI工具箱
 */
public class UriUtils {
	public static final String URI_TEL = "tel:";
	public static final String URI_SMS = "smsto:";
	/**
	 * 获取呼叫给定的电话号码时用的Uri
	 * @param phoneNumber 给定的电话号码
	 * @return 呼叫给定的电话号码时用的Uri
	 */
	public static Uri getCallUri(String phoneNumber){
		return Uri.parse(URI_TEL+(phoneNumber!=null?phoneNumber:""));
	}
	
	/**
	 * 获取短信Uri
	 * @param mobileNumber 目标手机号
	 * @return
	 */
	public static Uri getSmsUri(String mobileNumber){
		return Uri.parse(URI_SMS+(mobileNumber!=null?mobileNumber:""));
	}

	/**
	 * create a uri for output
	 * @param activity
	 * @param filename filename
	 * @return
	 */
	public static Uri createCoverUri(Activity activity,String dir, String filename) {
		File outputImage = new File(Environment.getExternalStorageDirectory()+dir, filename);
		if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 5);
			return null;
		}
		try {
			if (outputImage.exists()) {
				outputImage.delete();
			}
			outputImage.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Uri.fromFile(outputImage);
	}
}
