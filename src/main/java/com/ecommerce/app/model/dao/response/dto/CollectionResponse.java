package com.ecommerce.app.model.dao.response.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CollectionResponse {
    private String id;
    private String collectionName;
    private String collectionDescription;
    private String collectionImage;
    private String collectionSlug;
}
