/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.boot.loader.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL utils.
 *
 * @author hengyunabc chen
 */
public abstract class URLUtils {

	private static int countOccurrencesOf(String str, String sub) {
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * Convert jar file URL to file URL.
	 * @param urls the urls to be converted
	 * @return the urls converted
	 */
	public static URL[] tryConvertJarURLtoFileURL(URL[] urls) {
		URL[] result = new URL[urls.length];
		for (int i = 0; i < urls.length; ++i) {
			String urlString = urls[i].toString();
			if (urlString.startsWith("jar:file:") && urlString.endsWith("!/")
					&& countOccurrencesOf(urlString, "!/") == 1) {
				try {
					result[i] = new URL(urlString.substring("jar:".length(),
							urlString.length() - "!/".length()));
				}
				catch (MalformedURLException ex) {
					result[i] = urls[i];
				}
			}
			else {
				result[i] = urls[i];
			}
		}
		return result;
	}

}
