/**
 * Created by Yannick Lacaute on 18/01/17.
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
package com.thorpora.test;

import com.thorpora.module.core.domain.AbstractEntity;
import com.thorpora.module.core.error.ErrorDTO;
import com.thorpora.module.core.web.RestResource;
import org.assertj.core.api.Assertions;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class TestRestHelper<Resource extends RestResource, Entity extends AbstractEntity> {

    private final Class<Resource> resourceClass;
    private TestRestTemplate restTemplate;

    protected String resourceMapping;
    protected String resourceIdMapping;
    protected String resourcesMapping;

    public TestRestHelper(
            TestRestTemplate restTemplate,
            String resourceMapping,
            String resourceIdMapping,
            String resourcesMapping) {
        this.resourceMapping = resourceMapping;
        this.resourceIdMapping = resourceIdMapping;
        this.resourcesMapping = resourcesMapping;
        this.resourceClass = resolveResourceClass();
        this.restTemplate = restTemplate;
    }

    protected abstract ParameterizedTypeReference<List<Resource>> getResourceListType();

    @SuppressWarnings("unchecked")
    private Class<Resource> resolveResourceClass() {
        return (Class<Resource>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * TEST POST
     */
    public void postOne(ResourceCreator<Resource> entityCreator,
                        ResourceVerifier<Resource> verifier,
                        Object... urlVariables) {
        Resource postedRsc = entityCreator.create();

        ResponseEntity<Resource> postResponse = restTemplate
                .postForEntity(resourceMapping, postedRsc, resourceClass, urlVariables);

        // Verify POST status
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Verify POST location
        String path = postResponse.getHeaders().getLocation().getPath();
        assertThat(postResponse.getBody()).isNotNull();
        assertValidLocation(path, resourceMapping, postResponse.getBody().getId(), urlVariables);

        // Verify POST body
        Resource rscGot = postResponse.getBody();
        Assertions.assertThat(rscGot.getId()).isNotNull();
        verifier.verify(postedRsc, rscGot);
    }

    /**
     * TEST GET
     */
    public void postOneAndGetIt(ResourceCreator<Resource> resourceCreator,
                                ResourceVerifier verifier,
                                Object... urlVariables) {
        Resource postedRsc = resourceCreator.create();

        ResponseEntity<Resource> postResponse = restTemplate
                .postForEntity(resourceMapping, postedRsc, resourceClass, urlVariables);

        ResponseEntity<Resource> getResponse = restTemplate
                .getForEntity(postResponse.getHeaders().getLocation().getPath(), resourceClass);

        // Verify GET status
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify GET location
        String path = postResponse.getHeaders().getLocation().getPath();
        assertThat(getResponse.getBody()).isNotNull();
        assertValidLocation(path, resourceMapping, postResponse.getBody().getId(), urlVariables);

        // Verify GET body
        Resource rscGot = getResponse.getBody();
        Assertions.assertThat(rscGot.getId()).isNotNull();
        verifier.verify(postedRsc, rscGot);
    }

    /**
     * TEST GET MANY EMPTY (without result)
     */
    public void getManyWithoutResult(Object... urlVariables) {
        ResponseEntity<List<Resource>> getResponse = restTemplate
                .exchange(resourcesMapping, HttpMethod.GET, null, getResourceListType(), urlVariables);

        // Verify GET status
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify GET location
        String path = getResponse.getHeaders().getLocation().getPath();
        assertThat(getResponse.getBody()).isNotNull();
        assertValidLocation(path, resourcesMapping, urlVariables);

        // Verify GET body
        List<Resource> resources = getResponse.getBody();
        assertThat(resources).hasSize(0);
    }

    /**
     * TEST GET MANY (with results)
     */
    public void postManyAndGetThem(ResourceListCreator<Resource> resourcesCreator,
                                   ResourceListVerifier<Resource> verifier,
                                   Object... urlVariables) {
        Collection<Resource> resources = resourcesCreator.create();

        resources.forEach(rsc ->
                restTemplate.postForEntity(resourceMapping, rsc, Object.class, urlVariables));

        ResponseEntity<List<Resource>> getResponse =
                restTemplate.exchange(resourcesMapping, HttpMethod.GET, null, getResourceListType(), urlVariables);

        // Verify GET body
        assertThat(getResponse.getBody()).isNotNull();
        List<Resource> result = getResponse.getBody();
        assertThat(result).hasSize(resources.size());
        verifier.verify(result);
    }

    /**
     * TEST PUT
     * <p>
     * Default status : 204 (NO CONTENT)
     * Location : none (no result)
     */
    public void postOnePutItAndGetIt(
            ResourceCreator<Resource> resourceCreator,
            ResourceModifier<Resource> resourceModifier,
            ResourceVerifier<Resource> resourceVerifier,
            Object... urlVariables) {
        Resource rsc = resourceCreator.create();

        // POST
        Resource postedRsc = restTemplate
                .postForEntity(resourceMapping, rsc, resourceClass, urlVariables).getBody();

        // PUT
        Resource putRsc = resourceModifier.modify(postedRsc);
        restTemplate.put(postedRsc.getHref(), putRsc);

        // GET
        ResponseEntity<Resource> getResponse = restTemplate
                .getForEntity(postedRsc.getHref(), resourceClass);

        // Verify GET status
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verify GET location
        String path = getResponse.getHeaders().getLocation().getPath();
        assertThat(getResponse.getBody()).isNotNull();
        assertValidLocation(path, resourceMapping, getResponse.getBody().getId(), urlVariables);

        // Verify GET body
        Resource resourceGot = getResponse.getBody();
        resourceVerifier.verify(rsc, resourceGot);
    }


    /**
     * TEST DELETE EXISTING AND GET NOT FOUND
     * Default status : 204 (NO CONTENT)
     * Location : none (no result)
     */
    public void postOneDeleteItAndGetItNotFound(
            ResourceCreator<Resource> resourceCreator,
            Object... urlVariables) {
        Resource rsc = resourceCreator.create();

        // POST
        Resource postedRsc = restTemplate
                .postForEntity(resourceMapping, rsc, resourceClass, urlVariables).getBody();

        // DELETE
        restTemplate.delete(postedRsc.getHref());

        // GET
        ResponseEntity<ErrorDTO> getResponse = restTemplate
                .getForEntity(postedRsc.getHref(), ErrorDTO.class);

        // Verify GET body
        Assertions.assertThat(getResponse.getBody()).isNotNull();
        ErrorDTO errorDTO = getResponse.getBody();
        Assertions.assertThat(errorDTO.getTimeStamp()).isNotNull();

        // Verify GET location
        assertValidLocation(errorDTO.getPath(), resourceMapping, postedRsc.getId(), urlVariables);

        //assertThat(errorDTO.getPath()).isEqualTo(buildGetResourcePath(savedRsc.getId()));
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    /**
     * TEST DELETE (not exist, must not fail)
     */
    public void deleteNotExist(Object... urlVariables) {
        restTemplate.delete(resourceIdMapping, UUID.randomUUID(), urlVariables);
    }


    protected String buildGetResourcePath(Object id, Object... urlVariables) {
        return resourceIdMapping.replace("{id}", id.toString());
    }

    public void assertValidLocation(String resourceLocation,
                                    String resourceMapping,
                                    Object... pathVariables) {
        assertValidLocation(resourceLocation, resourceMapping, null, pathVariables);
    }


    public void assertValidLocation(String resourceLocation,
                                    String resourceMapping,
                                    UUID id,
                                    Object... pathVariables) {
        String fakeHost = "http://localhost:8080";
        String expectedLocation = UriComponentsBuilder
                .fromPath(fakeHost + resourceMapping)
                .buildAndExpand(pathVariables)
                .toUri()
                .toString();
        if (id != null) {
            expectedLocation += "/" + id;
        }
        assertThat(expectedLocation).contains(resourceLocation);
    }

}
