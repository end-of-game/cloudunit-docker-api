/*
 * Copyright (c) 2015
 *
 * LICENCE : CloudUnit is available under the GNU Affero General Public License : https://gnu.org/licenses/agpl.html
 * but CloudUnit is licensed too under a standard commercial license.
 * Please contact our sales team if you would like to discuss the specifics of our Enterprise license.
 * If you are not sure whether the AGPL is right for you,
 * you can always test our software under the AGPL and inspect the source code before you contact us
 * about purchasing a commercial license.
 *
 * LEGAL TERMS : CloudUnit is a registered trademark of Treeptik and cannot be used to endorse
 * or promote products derived from this project without prior written permission from Treeptik.
 * Products or services derived from this software may not be called "CloudUnit"
 * nor may "Treeptik" or similar confusing terms appear in their names without prior written permission.
 * For any questions, contact us : contact@treeptik.fr
 */

package fr.treeptik.cloudunit.utils;

import fr.treeptik.cloudunit.dto.DockerResponse;
import fr.treeptik.cloudunit.exception.JSONClientException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;

public class JSONClient {

    private Logger logger = LoggerFactory.getLogger(JSONClient.class);

    public DockerResponse sendGet(URI uri) throws JSONClientException {

        if (logger.isDebugEnabled()) {
            logger.debug("Send a get request to : " + uri);
        }
        StringBuilder builder = new StringBuilder();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        HttpResponse response = null;
        try {
            response = httpclient.execute(httpGet);
            LineIterator iterator = IOUtils.lineIterator(response.getEntity()
                    .getContent(), "UTF-8");
            while (iterator.hasNext()) {
                builder.append(iterator.nextLine());
            }
        } catch (IOException e) {
            throw new JSONClientException("Error in sendGet method due to : " + e.getMessage(), e);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Status code : " + response.getStatusLine().getStatusCode());
            logger.debug("Server response : " + builder.toString());
        }

        return new DockerResponse(response.getStatusLine().getStatusCode(), builder.toString());

    }

    public DockerResponse sendPost(URI uri, String body, String contentType) throws JSONClientException {

        if (logger.isDebugEnabled()) {
            logger.debug("Send a post request to : " + uri);
            logger.debug("Body content : " + body);
            logger.debug("Content type : " + contentType);
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("content-type", contentType);
        HttpResponse response = null;
        StringWriter writer = new StringWriter();
        try {
            httpPost.setEntity(new StringEntity(body));
            response = httpclient.execute(httpPost);
            IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        } catch (IOException e) {
            throw new JSONClientException("Error in sendPost method due to : " + e.getMessage(), e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Status code : " + response.getStatusLine().getStatusCode());
            logger.debug("Server response : " + writer.toString());
        }

        return new DockerResponse(response.getStatusLine().getStatusCode(), writer.toString());
    }

    public DockerResponse sendPostToRegistryHost(URI uri, String body, String contentType) throws JSONClientException {

        if (logger.isDebugEnabled()) {
            logger.debug("Send a post request to : " + uri);
            logger.debug("Body content : " + body);
            logger.debug("Content type : " + contentType);
        }

        CloseableHttpClient httpclient = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(1000 * 60 * 5)
                .setConnectTimeout(1000 * 60 * 5).build();
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(config);
        httpPost.addHeader("content-type", contentType);
        httpPost.addHeader("X-Registry-Auth", "1234");
        HttpResponse response = null;
        StringWriter writer = new StringWriter();
        try {
            httpPost.setEntity(new StringEntity(body));
            response = httpclient.execute(httpPost);
            IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
        } catch (IOException e) {
            throw new JSONClientException("Error in sendPostToRegistryHost method due to : " + e.getMessage(), e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Status code : " + response.getStatusLine().getStatusCode());
            logger.debug("Server response : " + writer.toString());
        }

        return new DockerResponse(response.getStatusLine().getStatusCode(), writer.toString());
    }

    public DockerResponse sendDelete(URI uri) throws JSONClientException {

        if (logger.isDebugEnabled()) {
            logger.debug("Send a delete request to : " + uri);
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(uri);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpDelete);
        } catch (IOException e) {
            throw new JSONClientException("Error in sendDelete method due to : " + e.getMessage(), e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Status code : " + response.getStatusLine().getStatusCode());
        }

        return new DockerResponse(response.getStatusLine().getStatusCode(), "");
    }
}
