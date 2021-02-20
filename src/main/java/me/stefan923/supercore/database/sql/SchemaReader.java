package me.stefan923.supercore.database.sql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public final class SchemaReader {

    private SchemaReader() { }

    public static List<String> getStatements(InputStream inputStream) throws IOException {
        List<String> queries = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            StringBuilder query = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("--") || line.startsWith("#")) {
                    continue;
                }

                query.append(line);

                if (line.endsWith(";")) {
                    query.deleteCharAt(query.length() - 1);

                    String result = query.toString().trim();
                    if (!result.isEmpty()) {
                        queries.add(result);
                    }

                    query = new StringBuilder();
                }
            }
        }

        return queries;
    }

}
