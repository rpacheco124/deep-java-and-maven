package com.dachser.api;

public final class JsonUtil {

    private JsonUtil() {
    }

    public static String extractStringField(String json, String field) {
        if (json == null) {
            return null;
        }

        String token = "\"" + field + "\"";
        int fieldPos = json.indexOf(token);
        if (fieldPos < 0) {
            return null;
        }

        int colonPos = json.indexOf(':', fieldPos + token.length());
        if (colonPos < 0) {
            return null;
        }

        int firstQuote = json.indexOf('"', colonPos + 1);
        if (firstQuote < 0) {
            return null;
        }

        int secondQuote = json.indexOf('"', firstQuote + 1);
        if (secondQuote < 0) {
            return null;
        }

        return json.substring(firstQuote + 1, secondQuote);
    }

    public static String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
