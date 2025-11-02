package com.modaexpress.modaexpress_backend.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection = "resenas")
public class Review {
    @Id
    private String id;
    private String userId;
    private String productId;
    private Double rating;
    private String comment;
    // metadata puede ser Map<String,Object> si se desea

}
