/**
 * Created by Yannick Lacaute on 12/01/17.
 * Copyright 2015-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thorpora.module.core.converter;

import javax.persistence.AttributeConverter;
import java.io.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StringMapConverter implements AttributeConverter<Map<String, Serializable>, String> {

    private static final String SEPARATOR = ";";

    @Override
    public String convertToDatabaseColumn(Map<String, Serializable> attribute) {
        String mapAsString = attribute.entrySet().stream()
                .map(this::serializeMapEntry)
                .collect(Collectors.joining(SEPARATOR));
        return mapAsString;
    }

    @Override
    public Map<String, Serializable> convertToEntityAttribute(String dbData) {
        Map<String, Serializable> resultMap = new HashMap<>();
        if (!dbData.isEmpty()) {
            String[] mapEntries = dbData.split(SEPARATOR);
            Arrays.stream(mapEntries).forEach(entry -> {
                String key = entry.split("=")[0];
                Serializable value = fromString64(entry.split("=")[1]);
                resultMap.put(key, value);
            });
        }
        return resultMap;
    }

    private String serializeMapEntry(Map.Entry<String, Serializable> entry) {
        String value;
        try {
            value = toString64(entry.getValue());
        } catch (IOException e) {
            value = null;
        }
        return entry.getKey() + "=" + value;
    }

    private static Serializable fromString64(String value) {
        try {
            byte [] data = Base64.getDecoder().decode(value);
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            try (ObjectInputStream ois = new ObjectInputStream(bais)) {
                Object object = ois.readObject();
                ois.close();
                return (Serializable)object;
            }
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    private static String toString64(Serializable object) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

}