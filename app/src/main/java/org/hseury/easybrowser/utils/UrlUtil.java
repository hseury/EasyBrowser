package org.hseury.easybrowser.utils;

/**
 * @description: Created by hseury on 2017/10/23.
 */

public class UrlUtil {
	public static String sanitizeUrl(String url) {
		if (url == null) return null;
		if (url.startsWith("www.") || url.indexOf(":") == -1) url = "http://" + url;
		return url;
	}
}
