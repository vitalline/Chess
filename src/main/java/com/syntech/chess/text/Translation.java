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
    public String get(String str, @NotNull Object... args) {
        String result = get(str);
        int index, prevIndex = 0;
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            if (args[i] instanceof String) {
                String value = get((String) args[i]);
                if (!value.equals(args[i])) {
                    index = result.indexOf('#', prevIndex);
                    if (index == -1 || index == result.length() - 1) {
                        args[i] = value.split("\n")[0];
                    } else {
                        while (result.charAt(index + 1) == '#') {
                            prevIndex = index + 2;
                            index = result.indexOf('#', prevIndex);
                            if (index == -1 || index == result.length() - 1) {
                                break;
                            }
                        }
                        if (index == -1 || index == result.length() - 1) {
                            args[i] = value.split("\n")[0];
                        } else {
                            int valueIndex = Character.digit(result.charAt(index + 1), 16);
                            if (valueIndex != -1) {
                                String[] values = value.split("\n");
                                if (values.length > valueIndex) {
                                    args[i] = values[valueIndex];
                                } else {
                                    args[i] = values[0];
                                }
                            }
                            prevIndex = index + 2;
                            if (result.length() > prevIndex) {
                                result = result.substring(0, index) + result.substring(index + 2);
                            } else {
                                result = result.substring(0, index);
                            }
                        }
                    }
                }
            }
        }
        return String.format(result.replaceAll("##", "#"), args);
    }

    public boolean exists(@NotNull String str) {
        return !str.equals(get(str));
    }
}
