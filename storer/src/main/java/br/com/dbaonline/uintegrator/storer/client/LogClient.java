package br.com.dbaonline.uintegrator.storer.client;

import br.com.dbaonline.uintegrator.entity.dto.Log;
import br.com.dbaonline.uintegrator.entity.transients.LogLevel;
import br.com.dbaonline.uintegrator.storer.config.ElasticSearchConfig;
import br.com.dbaonline.uintegrator.storer.entity.ApplicationLog;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.indices.CreateDataStreamRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.json.JsonData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.NONE)
public class LogClient extends AbstractClient {

    private static final String INDEX_PREFIX = "logs-";
    private static final String LEVEL_FIELD = "level";
    private static final String TIMESTAMP_FIELD = "@timestamp";

    public LogClient(@NonNull ElasticSearchConfig config) {
        super(config);
    }

    public void createDataStream(@NonNull UUID applicationCode) throws IOException {
        val createDataStreamRequest = new CreateDataStreamRequest.Builder()
                .name(mountIndex(applicationCode))
                .build();

        Objects.requireNonNull(client)
                .indices()
                .createDataStream(createDataStreamRequest);
    }

    /**
     * Insert a log entry to a application data stream.
     */
    public void insert(@NonNull UUID applicationCode, @NonNull Log log) {
        // TODO: Implement here
    }

    /**
     * Get last received logs from application.
     *
     * @param applicationCode Registration code of application to be searched.
     * @param numberOfLines Number of returned lines (limit)
     */
    public List<ApplicationLog> tail(@NonNull UUID applicationCode, @NonNull Integer numberOfLines) throws IOException {
        val response = Objects.requireNonNull(client).search(builder ->
                builder.index(mountIndex(applicationCode))
                        .size(numberOfLines)
                        .sort(b -> b.field(f -> f.field(TIMESTAMP_FIELD).order(SortOrder.Desc)))
                        , ApplicationLog.class);

        return response.hits().hits().stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }

    public ApplicationLog last(@NonNull UUID applicationCode) throws IOException {
        val lastApplicationLog = tail(applicationCode, 1).iterator();

        if (lastApplicationLog.hasNext()) {
            return lastApplicationLog.next();
        }

        return null;
    }

    /**
     * @return Application has error log level received today.
     */
    public boolean hasErrorToday(@NonNull UUID applicationCode) throws IOException {

        val mustBeError = MatchQuery.of(m -> m
                .field(LEVEL_FIELD)
                .query(LogLevel.ERROR.name())
        )._toQuery();

        val mustBeToday = RangeQuery.of(m -> m
                .field(TIMESTAMP_FIELD)
                .gt(JsonData.of(new Date()))
        )._toQuery();

        val response = Objects.requireNonNull(client).count(builder ->
                builder.index(mountIndex(applicationCode))
                        .query(q -> q
                                .bool(b -> b
                                        .must(mustBeError)
                                        .must(mustBeToday))));

        return response.count() > 0;
    }

    private @NonNull String mountIndex(@NonNull UUID applicationCode) {
        return INDEX_PREFIX + applicationCode;
    }

}
