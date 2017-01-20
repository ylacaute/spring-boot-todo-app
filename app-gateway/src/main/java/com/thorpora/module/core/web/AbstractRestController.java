/**
 * Created by Yannick Lacaute on 12/01/17.
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
package com.thorpora.module.core.web;

import com.thorpora.module.core.domain.AbstractRestEntity;
import com.thorpora.module.core.exception.InternalRestException;
import com.thorpora.module.core.service.AbstractRestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AbstractRestController<Resource extends RestResource, Entity extends AbstractRestEntity> {

    private final String baseURI;

    protected AbstractRestService<Entity> service;

    protected AbstractRestResourceConverter<Resource, Entity> converter;

    public AbstractRestController(String baseURI,
                                  AbstractRestService<Entity> service,
                                  AbstractRestResourceConverter<Resource, Entity> converter) {
        this.baseURI = baseURI.charAt(baseURI.length() - 1) == '/' ? baseURI : baseURI + '/';
        this.service = service;
        this.converter = converter;
    }

    /**
     * POST
     */
    public ResponseEntity<Resource> postRequest(Resource resource) {
        Entity entity = converter.toEntity(resource);
        entity.setHref(getServletPath() + "/" + entity.getId());
        Entity savedEntity = service.save(entity);
        return postResponse(savedEntity);
    }

    /**
     * POST RESPONSE
     */
    public ResponseEntity<Resource> postResponse(Entity savedEntity) {
        return ResponseEntity
                .created(generateURI(savedEntity.getHref()))
                .body(converter.toResource(savedEntity));
    }

    /**
     * GET ONE
     */
    public ResponseEntity<Resource> getRequest(UUID id) {
        Entity entity = service.getOne(id);
        Resource resource = converter.toResource(entity);

        return ResponseEntity
                .ok()
                .location(generateURI(getServletPath(), resource.getId()))
                .body(resource);
    }

    /**
     * GET MANY
     */
    public ResponseEntity<List<Resource>> getListRequest() {
        List<Entity> entities = service.findAll();
        List<Resource> resources = converter.toResource(entities);

        return ResponseEntity
                .ok()
                .location(generateURI(getServletPath()))
                .body(resources);
    }

    /**
     * PUT ONE
     */
    public ResponseEntity<Resource> putRequest(UUID pathId, Resource resource) {
        Entity entity = converter.toEntity(resource);
        Entity updatedEntity = service.updateOne(pathId, entity);

        return ResponseEntity
                .noContent()
                .location(generateURI(getServletPath(), resource.getId()))
                .build();
    }

    /**
     * DELETE ONE
     * What ever the resource exists or not, for idempotence delete operation is always successful
     *   NO_CONTENT (204)
     */
    public ResponseEntity<Void> deleteRequest(UUID id) {
        Optional<Entity> entity = service.deleteOne(id);
        return deleteResponse(id);
    }

    public ResponseEntity<Void> deleteResponse(UUID id) {
        return ResponseEntity
                .noContent()
                .location(generateURI(getServletPath(), id))
                .build();
    }

    private URI generateURI(String href, Object... pathVariables) {
        try {
            return UriComponentsBuilder
                    .fromPath(href)
                    .buildAndExpand(pathVariables)
                    .encode()
                    .toUri();
        } catch (Exception ex) {
            throw new InternalRestException(String
                    .format("Can't generate URI from href='%s'", href), ex);
        }
    }

    protected String getServletPath() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getServletPath();
    }

}
