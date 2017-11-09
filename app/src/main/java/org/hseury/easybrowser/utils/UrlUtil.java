package org.hseury.easybrowser.utils;

import android.util.Patterns;
import android.webkit.URLUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: Created by hseury on 2017/10/23.
 */

public class UrlUtil {

	static final Pattern ACCEPTED_URI_SCHEMA = Pattern.compile(
			"(?i)" + // switch on case insensitive matching
					"(" +    // begin group for schema
					"(?:http|https|file):\\/\\/" +
					"|(?:inline|data|about|javascript):" +
					"|(?:.*:.*@)" +
					")" +
					"(.*)" );

	// Google search
	private final static String QUICKSEARCH_G = "http://www.google.com/m?q=%s";
	private final static String QUERY_PLACE_HOLDER = "%s";

	public static String sanitizeUrl(String url) {
		if (url == null) return null;
		if (url.startsWith("www.") || url.indexOf(":") == -1) url = "http://" + url;
		return url;
	}

	/**
	 * Attempts to determine whether user input is a URL or search
	 * terms.  Anything with a space is passed to search if canBeSearch is true.
	 *
	 * Converts to lowercase any mistakenly uppercased schema (i.e.,
	 * "Http://" converts to "http://"
	 *
	 * @param canBeSearch If true, will return a search url if it isn't a valid
	 *                    URL. If false, invalid URLs will return null
	 * @return Original or modified URL
	 *
	 */
	public static String smartUrlFilter(String url, boolean canBeSearch) {
		String inUrl = url.trim();
		boolean hasSpace = inUrl.indexOf(' ') != -1;

		Matcher matcher = ACCEPTED_URI_SCHEMA.matcher(inUrl);
		if (matcher.matches()) {
			// force scheme to lowercase
			String scheme = matcher.group(1);
			String lcScheme = scheme.toLowerCase();
			if (!lcScheme.equals(scheme)) {
				inUrl = lcScheme + matcher.group(2);
			}
			if (hasSpace && Patterns.WEB_URL.matcher(inUrl).matches()) {
				inUrl = inUrl.replace(" ", "%20");
			}
			return inUrl;
		}
		if (!hasSpace) {
			if (Patterns.WEB_URL.matcher(inUrl).matches()) {
				return URLUtil.guessUrl(inUrl);
			}
		}
		if (canBeSearch) {
			return URLUtil.composeSearchUrl(inUrl,
					QUICKSEARCH_G, QUERY_PLACE_HOLDER);
		}
		return null;
	}
}
