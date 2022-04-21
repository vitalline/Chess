package com.syntech.chess.text;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syntech.chess.Main;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;

public enum Translation {
    NONE("en_US"),
    EN_US("en_US"),
    RU_RU("ru_RU");

    private final JsonNode json;

    Translation(String languageCode) {
        String path = String.format("lang/%s.json", languageCode);
        JsonNode tempJson = null;
        InputStream translationInput = Main.class.getClassLoader().getResourceAsStream(path);
        if (translationInput != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                tempJson = objectMapper.readTree(translationInput);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        json = tempJson;
    }

    @NotNull
    public String get(String str) {
        if (json != null) {
            JsonNode field = json.get(str);
            if (field != null) {
                if (field.isTextual()) {
                    return field.asText();
                } else if (field.isArray()) {
                    StringBuilder sb = new StringBuilder();
                    for (JsonNode subfield : field) {
                        sb.append(subfield.asText());
                        sb.append("\n");
                    }
                    if (sb.length() > 0) {
                        return sb.toString();
                    }
                }
            }
        }
        return str;
    }

    @NotNull
    public String get(String str, @NotNull Object @NotNull ... args) {
        String result = get(str);
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            if (args[i] instanceof String) {
                args[i] = get((String) args[i]);
            }
        }
        return String.format(result, args);
    }

    public boolean exists(@NotNull String str) {
        return !str.equals(get(str));
    }
}
