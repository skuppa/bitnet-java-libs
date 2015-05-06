/*
 * Copyright 2015 Bitnet.
 */

package io.bitnet.feign.decoders;

import com.google.gson.*;
import feign.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This queries the error responses from Bitnet to pull out required values.
 */
public class BitnetErrorResponseInterrogator {

    public String getCorrelationId(String responseBody) {
        return extractValueFromResponseBody(responseBody, new JsonValueExtractor() {
            @Override
            public String extract(String json) {
                try {
                    JsonElement body = new JsonParser().parse(json);
                    JsonObject obj = body.getAsJsonObject();
                    String correlationId = obj.get("correlationId").toString();
                    return stripWrappingQuotes(correlationId);
                } catch (NullPointerException | JsonParseException ex) {
                    return "";
                }
            }
        });
    }

    public String getBody(String responseBody) {
        return extractValueFromResponseBody(responseBody, new JsonValueExtractor() {
            @Override
            public String extract(String json) {
                try {
                    JsonElement body = new JsonParser().parse(json);
                    return body.getAsJsonObject().toString();
                } catch (JsonParseException jpe) {
                    return json;
                }
            }
        });
    }

    public List<String> getErrorMessages(String responseBody) {
        if ("".equals(responseBody)) {
            return new ArrayList<>();
        }

        return getErrorMessagesFromJson(responseBody);
    }

    private String extractValueFromResponseBody(String responseBody, JsonValueExtractor jsonValueExtractor) {
        return jsonValueExtractor.extract(responseBody);
    }

    public String getResponseBody(Response response) {

        Response.Body body = response.body();
        if (body == null) {
            return "";
        }

        byte[] bodyContent = new byte[body.length()];

        try {
            int bytesRead = body.asInputStream().read(bodyContent);
            if (bytesRead != body.length()) {
                return "";
            }
            return new String(bodyContent, "UTF-8");
        } catch (IOException e) {
            return "";
        }
    }

    private String stripWrappingQuotes(String value) {
        return value.substring(1, value.length() - 1);
    }

    private List<String> getErrorMessagesFromJson(String responseBody) {
        JsonArray errors = getErrorsFromErrorResponseBody(responseBody);

        try {
            return getErrorMessagesFromJsonErrors(errors);
        } catch (NullPointerException ex) {
            return new ArrayList<>();
        }
    }

    private List<String> getErrorMessagesFromJsonErrors(JsonArray errors) {
        List<String> errorMessages = new ArrayList<>();
        for (int i = 0; i < errors.size(); i++) {
            JsonObject jsonObject = errors.get(i).getAsJsonObject();
            errorMessages.add(stripWrappingQuotes(jsonObject.toString()));
        }
        return errorMessages;
    }

    private JsonArray getErrorsFromErrorResponseBody(String responseBody) {
        JsonElement body = new JsonParser().parse(responseBody);
        JsonObject obj = body.getAsJsonObject();
        return obj.getAsJsonArray("errors");
    }

    private interface JsonValueExtractor {
        String extract(String json);
    }
}
