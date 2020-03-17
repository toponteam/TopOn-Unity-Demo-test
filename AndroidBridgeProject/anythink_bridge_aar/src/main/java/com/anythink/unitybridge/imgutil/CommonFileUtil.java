package com.anythink.unitybridge.imgutil;


import android.text.TextUtils;

import java.io.File;


public class CommonFileUtil {



	/**
	 * Indicates if this file represents a file on the underlying file system.
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(filePath.trim())) {
			return false;
		}

		File file = new File(filePath);
		return (file.exists() && file.isFile());
	}


}