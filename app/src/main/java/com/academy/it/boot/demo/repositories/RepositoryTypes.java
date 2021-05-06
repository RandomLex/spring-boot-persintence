package com.academy.it.boot.demo.repositories;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public enum RepositoryTypes {
    MEMORY("memory"),
    POSTGRES("postgres"),
    JPA("jpa");

    private final String type;
    private static final Map<String, RepositoryTypes> value2Enum = initializeValue2EnumMap();
    private static final Map<RepositoryTypes, String> enum2Value = initializeEnumMap2Value();

    RepositoryTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static RepositoryTypes getTypeByStr(String type) {
        return value2Enum.getOrDefault(type, MEMORY);
    }

    public static String getStrByType(RepositoryTypes type) {
        return enum2Value.get(type);
    }

    private static Map<String, RepositoryTypes> initializeValue2EnumMap() {
        Map<String, RepositoryTypes> map = new HashMap<>();
        for (RepositoryTypes enumElement : RepositoryTypes.values()) {
            map.put(enumElement.type, enumElement);
        }
        return Collections.unmodifiableMap(map);
    }

    private static Map<RepositoryTypes, String> initializeEnumMap2Value() {
        Map<RepositoryTypes, String> map = new EnumMap<>(RepositoryTypes.class);
        for (RepositoryTypes enumElement : RepositoryTypes.values()) {
            map.put(enumElement, enumElement.type);
        }
        return Collections.unmodifiableMap(map);
    }


}
