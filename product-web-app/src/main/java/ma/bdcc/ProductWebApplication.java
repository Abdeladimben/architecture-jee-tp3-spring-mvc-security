package ma.bdcc;

import ma.bdcc.entities.Product;
import ma.bdcc.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductWebApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            productRepository.save(Product.builder().name("Ordinateur HP").price(4500.0).quantity(10).build());
            productRepository.save(Product.builder().name("Imprimante Canon").price(1200.0).quantity(5).build());
            productRepository.save(Product.builder().name("Smartphone Samsung").price(3000.0).quantity(20).build());
            productRepository.save(Product.builder().name("Tablette iPad").price(5500.0).quantity(8).build());
            productRepository.save(Product.builder().name("Casque Audio").price(250.0).quantity(30).build());
            productRepository.save(Product.builder().name("Clavier Sans Fil").price(150.0).quantity(15).build());
            productRepository.save(Product.builder().name("Souris Logitech").price(80.0).quantity(25).build());
            productRepository.save(Product.builder().name("Écran 24 pouces").price(2000.0).quantity(7).build());
        };
    }
}
