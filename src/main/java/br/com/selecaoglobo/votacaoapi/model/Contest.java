package br.com.selecaoglobo.votacaoapi.model;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection = "contest")
public class Contest {

	@Id
	@NotNull(message = "A propriedade 'slug' é obrigatória.")
	@Size(min=3, max=20, message="A propriedade 'slug' deve ter de 3 a 20 caracteres.")
	private String slug;
	
	@NotNull(message = "A propriedade 'name' é obrigatória.")
	@Size(min=3, max=10, message="A propriedade 'name' deve ter de 3 a 10 caracteres.")
	private String name;
	
    @Size(min=3, max=255, message="A propriedade 'description' deve ter de 3 a 255 caracteres.")
	private String description;
	
    @NotNull(message = "A propriedade 'start_date' é obrigatória.")
	@Field("start_date")
	@JsonProperty("start_date")
    @DateTimeFormat(iso = ISO.DATE)
	private Date startDate;
	
    @NotNull(message = "A propriedade 'end_date' é obrigatória.")
	@Field("end_date")
	@JsonProperty("end_date")
    @DateTimeFormat(iso = ISO.DATE)
	private Date endDate;
}
