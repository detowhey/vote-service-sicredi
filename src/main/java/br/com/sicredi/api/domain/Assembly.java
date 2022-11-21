package br.com.sicredi.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Assembly {

    private String id;
    private Session session;
    private String name;
}
