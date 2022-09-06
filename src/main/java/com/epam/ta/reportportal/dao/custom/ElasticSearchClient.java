package com.epam.ta.reportportal.dao.custom;

import com.epam.ta.reportportal.entity.log.LogMessage;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Simple client to work with Elasticsearch.
 * @author <a href="mailto:maksim_antonov@epam.com">Maksim Antonov</a>
 */
@Service
@ConditionalOnProperty(prefix = "rp.elasticsearch", name = "host")
public class ElasticSearchClient {
    public static final String INDEX_PREFIX = "logs-reportportal-";
    public static final String CREATE_COMMAND = "{\"create\":{ }}\n";
    protected final Logger LOGGER = LoggerFactory.getLogger(ElasticSearchClient.class);

    private final String host;
    private final RestTemplate restTemplate;

    public ElasticSearchClient(@Value("${rp.elasticsearch.host}") String host,
                                     @Value("${rp.elasticsearch.username}") String username,
                                     @Value("${rp.elasticsearch.password}") String password) {
        restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));

        this.host = host;
    }

    public void save(List<LogMessage> logMessageList) {
        if (CollectionUtils.isEmpty(logMessageList)) return;
        Map<String, String> logsByIndex = new HashMap<>();

        logMessageList.forEach(logMessage -> {
            String indexName = getIndexName(logMessage.getProjectId());
            String logCreateBody = CREATE_COMMAND + convertToJson(logMessage) + "\n";

            if (logsByIndex.containsKey(indexName)) {
                logsByIndex.put(indexName, logsByIndex.get(indexName) + logCreateBody);
            } else {
                logsByIndex.put(indexName, logCreateBody);
            }
        });

        logsByIndex.forEach((indexName, body) -> {
            restTemplate.put(host + "/" + indexName + "/_bulk?refresh", getStringHttpEntity(body));
        });
    }

    public void deleteLogsByLogIdAndProjectId(Long projectId, Long logId) {
        JSONObject terms = new JSONObject();
        terms.put("id", List.of(logId));

        deleteLogsByTermsAndProjectId(projectId, terms);
    }

    public void deleteLogsByItemSetAndProjectId(Long projectId, Set<Long> itemIds) {
        JSONObject terms = new JSONObject();
        terms.put("itemId", itemIds);

        deleteLogsByTermsAndProjectId(projectId, terms);
    }

    public void deleteLogsByLaunchIdAndProjectId(Long projectId, Long launchId) {
        JSONObject terms = new JSONObject();
        terms.put("launchId", List.of(launchId));

        deleteLogsByTermsAndProjectId(projectId, terms);
    }

    public void deleteLogsByLaunchListAndProjectId(Long projectId, List<Long> launches) {
        JSONObject terms = new JSONObject();
        terms.put("launchId", launches);

        deleteLogsByTermsAndProjectId(projectId, terms);
    }

    public void deleteLogsByProjectId(Long projectId) {
        String indexName = getIndexName(projectId);
        try {
            restTemplate.delete(host + "/_data_stream/" + indexName);
        } catch (Exception exception) {
            // to avoid checking of exists stream or not
            LOGGER.error("DELETE stream from ES " + indexName + " Project: " + projectId
                    + " Message: " + exception.getMessage());
        }
    }

    private void deleteLogsByTermsAndProjectId(Long projectId, JSONObject terms) {
        String indexName = getIndexName(projectId);
        try {
            JSONObject deleteByLaunch = getDeleteJson(terms);
            HttpEntity<String> deleteRequest = getStringHttpEntity(deleteByLaunch.toString());

            restTemplate.postForObject(host + "/" + indexName + "/_delete_by_query", deleteRequest, JSONObject.class);
        } catch (Exception exception) {
            // to avoid checking of exists stream or not
            LOGGER.error("DELETE logs from stream ES error " + indexName + " Terms: " + terms
                    + " Message: " + exception.getMessage());
        }
    }

    private String getIndexName(Long projectId) {
        return INDEX_PREFIX + projectId;
    }

    private JSONObject getDeleteJson(JSONObject terms) {
        JSONObject query = new JSONObject();
        query.put("terms", terms);

        JSONObject deleteByLaunch = new JSONObject();
        deleteByLaunch.put("query", query);

        return deleteByLaunch;
    }

    private JSONObject convertToJson(LogMessage logMessage) {
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("id", logMessage.getId());
        personJsonObject.put("message", logMessage.getLogMessage());
        personJsonObject.put("itemId", logMessage.getItemId());
        personJsonObject.put("@timestamp", logMessage.getLogTime());
        personJsonObject.put("launchId", logMessage.getLaunchId());

        return personJsonObject;
    }

    private HttpEntity<String> getStringHttpEntity(String body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(body, headers);
    }

}
