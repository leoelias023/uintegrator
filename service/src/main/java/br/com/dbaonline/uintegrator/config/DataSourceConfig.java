package br.com.dbaonline.uintegrator.config;

import br.com.dbaonline.uintegrator.util.DataSourceEnv;
import br.com.dbaonline.uintegrator.util.PropertyLoader;
import br.com.dbaonline.uintegrator.util.SpringProfile;
import lombok.NonNull;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    private static final String PROPERTIES_FILE = "db.properties";

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() throws IOException {
        val credentials = environment.acceptsProfiles(Profiles.of(SpringProfile.dev.name()))
            ? getCredentialsFromPropertiesFile()
            : getCredentialsFromEnv();

        return DataSourceBuilder.create()
                .driverClassName(credentials.getDriverClassName())
                .url(credentials.getUrl())
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build();
    }

    private @NonNull DataSourceCredentials getCredentialsFromEnv() {
        val driverClassName = System.getenv(DataSourceEnv.DATASOURCE_DRIVERCLASSNAME.name());
        val url = System.getenv(DataSourceEnv.DATASOURCE_URL.name());
        val username = System.getenv(DataSourceEnv.DATASOURCE_USERNAME.name());
        val password = System.getenv(DataSourceEnv.DATASOURCE_PASSWORD.name());

        Assert.notNull(driverClassName, DataSourceEnv.DATASOURCE_DRIVERCLASSNAME + " must be defined as environment variable");
        Assert.notNull(url, DataSourceEnv.DATASOURCE_URL + " must be defined as environment variable");
        Assert.notNull(username, DataSourceEnv.DATASOURCE_USERNAME + " must be defined as environment variable");
        Assert.notNull(password, DataSourceEnv.DATASOURCE_PASSWORD + " must be defined as environment variable");

        return DataSourceCredentials.builder()
                .driverClassName(driverClassName)
                .password(password)
                .url(url)
                .username(username)
                .build();
    }

    private @NonNull DataSourceCredentials getCredentialsFromPropertiesFile() throws IOException {
        return PropertyLoader.load(fetchProperties(), "datasource", DataSourceCredentials.class);
    }

    private @NonNull Properties fetchProperties() throws IOException {
        val properties = new Properties();

        properties.load(new ClassPathResource(PROPERTIES_FILE).getInputStream());

        return properties;
    }

}
