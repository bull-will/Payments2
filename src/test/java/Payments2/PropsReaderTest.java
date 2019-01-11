package Payments2;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

public class PropsReaderTest {

    @Test
    public void readPropsAsStream() throws IOException {

        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("test.properties");

        assertThat(resourceAsStream).isNotNull();

        Properties properties = new Properties();
        properties.load(resourceAsStream);

        assertThat(properties.get("key").toString()).isEqualToIgnoringCase("value");

    }
}
