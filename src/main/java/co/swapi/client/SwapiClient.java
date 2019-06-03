package co.swapi.client;

import co.swapi.api.PlanetSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

public class SwapiClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SwapiClient.class);

    private static final String EXCEPTION_IN_SEARCH = "Exception on consult SWPAI to planet [{}] | error[{}]";
    private static final String EXCEPTION_IN_HTTP_RESPONSE_CLOSE = "Erro ao fechar HTTP Response";
    private static final String EXCEPTION_ENCODING_URL = "Erro ao realizar encoding da URI";
    private static final String EXCEPTION_IN_PARSE_SEARCH_RESPONSE = "Erro ao realizar o parse de response";

    private final ObjectMapper mapper;

    private final CloseableHttpClient httpClient;

    public SwapiClient(CloseableHttpClient httpClient, ObjectMapper mapper) {
        this.httpClient = httpClient;
        this.mapper = mapper;
    }

    public final PlanetSearch executeSearch(final String planetName) {
        CloseableHttpResponse httpResponse = null;
        try {
            String url = formattedEncodedUrl(planetName);
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
            httpResponse = httpClient.execute(httpGet);

            StatusLine statusLine = httpResponse.getStatusLine();
            if (HttpStatus.OK.value() == statusLine.getStatusCode()) {
                String content = EntityUtils.toString(httpResponse.getEntity(), Charset.defaultCharset());
                return parseSearchResponse(content);
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.warn(EXCEPTION_IN_SEARCH, planetName, e.getMessage());
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    LOGGER.error(EXCEPTION_IN_HTTP_RESPONSE_CLOSE, e);
                }
            }
        }
        return null;
    }

    private PlanetSearch parseSearchResponse(final String content) {
        try {
            return mapper.readValue(content, PlanetSearch.class);
        }
        catch (IOException e) {
            LOGGER.error(EXCEPTION_IN_PARSE_SEARCH_RESPONSE, e);
        }
        return null;
    }

    private String formattedEncodedUrl(final String planetName) {
        try {
            String urlFormatted = "https://swapi.co/api/planets/?search="+planetName;

            // So, only the query string will be encoded
            final URL url = new URL(urlFormatted);
            final URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
            return uri.toString();
        } catch (URISyntaxException | MalformedURLException e) {
            LOGGER.error(EXCEPTION_ENCODING_URL, e);
        }
        throw new IllegalArgumentException(EXCEPTION_ENCODING_URL);
    }


}
