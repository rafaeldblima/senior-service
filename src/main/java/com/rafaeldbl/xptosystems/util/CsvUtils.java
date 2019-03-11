package com.rafaeldbl.xptosystems.util;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvUtils {

    private CsvUtils() {
    }

    public static <T> List<T> read(Class<T> clazz, InputStream stream) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = mapper.schemaFor(clazz).withHeader().withColumnReordering(true);
        ObjectReader reader = mapper.readerFor(clazz).with(schema);
        return reader.<T>readValues(stream).readAll();
    }

    public static final Map<String, String> RELATION_CSV_ENTITY = new HashMap<>();

    static {
        RELATION_CSV_ENTITY.put("ibge_id", "ibgeId");
        RELATION_CSV_ENTITY.put("uf", "uf");
        RELATION_CSV_ENTITY.put("name", "name");
        RELATION_CSV_ENTITY.put("capital", "isCapital");
        RELATION_CSV_ENTITY.put("lon", "longitude");
        RELATION_CSV_ENTITY.put("lat", "latitude");
        RELATION_CSV_ENTITY.put("no_accents", "unnaccentName");
        RELATION_CSV_ENTITY.put("alternative_names", "alternativeNames");
        RELATION_CSV_ENTITY.put("microregion", "microRegion");
        RELATION_CSV_ENTITY.put("mesoregion", "mesoregion");
    }

}
