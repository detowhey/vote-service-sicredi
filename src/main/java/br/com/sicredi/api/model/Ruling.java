package br.com.sicredi.api.model;

import br.com.sicredi.api.model.enu.RulingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ruling")
public class Ruling {

    @Id
    @Field(targetType  = FieldType.STRING)
    private String id;
    private Session session;
    @NotBlank(message = "Property is blank")
    @Size(min = 5, message = "Name is more then 5 chars")
    private String name;
    @Builder.Default
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RulingStatus status = RulingStatus.NOT_STARTED;
}
