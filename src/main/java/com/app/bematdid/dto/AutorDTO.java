package com.app.bematdid.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class AutorDTO {
    private Long idAutor;
    private String nombre;
    private String biografia;
    private String image;
}
