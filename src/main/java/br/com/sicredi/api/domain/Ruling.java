package br.com.sicredi.api.domain;

import br.com.sicredi.api.domain.enu.RulingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ruling")
public class Ruling {

    @Id
    @Field(targetType = FieldType.OBJECT_ID)
    private String id;
    private Session session;
    @NotBlank
    @Size(min = 5, message = "Name is more then 5 chars")
    private String name;
    @Builder.Default
    private RulingStatus status = RulingStatus.NOT_STARTED;
}
