package com.soprasteria.fleet.services.utilServices.interfaces;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public interface Sanitizer {
    /**
     * Sanitize text not allowing any HTML
     * @param value the value to sanitize
     * @return the sanitized value
     */
    static String stripXSS(String value) {
        if (value == null) {
            return null;
        }
        // Setting the Jsoup Whitelist to none allowing only text nodes. All HTML will be stripped.
        return Jsoup.clean(value, Safelist.none());
    }
}
