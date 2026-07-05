package ma.bdcc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String name;

    @Positive(message = "Le prix doit être positif")
    private double price;

    @Min(value = 0, message = "La quantité doit être >= 0")
    private int quantity;
}
