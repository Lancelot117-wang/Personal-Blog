package com.wjh.model.datastore;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Entity;
import org.springframework.cloud.gcp.data.datastore.core.mapping.Field;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@Entity
public class Tag {

    @Id
    @Field(name = "id")
    private Long id;
    @NotBlank(message = "Tag can't be blank")
    private String name;

    private List<Long> blogForeignIds = new ArrayList<>();
}
