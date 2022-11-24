package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ruling")
public class Ruling {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private Session session;
    @NotBlank
    @Size(min = 3, message = "{validation.name.size.too_short}")
    private String name;

    public Ruling(Session session, String name) {
        this.session = session;
        this.name = name;
    }
}
