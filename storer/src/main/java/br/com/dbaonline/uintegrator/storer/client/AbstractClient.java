package br.com.dbaonline.uintegrator.storer.client;

import br.com.dbaonline.uintegrator.storer.config.ElasticSearchConfig;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.val;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
abstract class AbstractClient {

    protected final ElasticsearchClient client;

    protected AbstractClient(@NonNull ElasticSearchConfig config) {
        this.client = generateClient(config);
    }

    private ElasticsearchClient generateClient(@NonNull ElasticSearchConfig config) {

        val credentialsProvider = generateCredentialsProvider(config.getCredentials());

        return new ElasticsearchClient(
                new RestClientTransport(
                        RestClient.builder(
                                new HttpHost(config.getHost(), config.getPort(), config.getScheme())
                                )
                                .setHttpClientConfigCallback(builder -> builder.setDefaultCredentialsProvider(credentialsProvider))
                                .build(),
                        new JacksonJsonpMapper()
                )
        );
    }

    private CredentialsProvider generateCredentialsProvider(@NonNull ElasticSearchConfig.Credentials credentials) {

        val provider = new BasicCredentialsProvider();

        provider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(credentials.getUsername(), credentials.getPassword())
        );

        return provider;
    }
}
