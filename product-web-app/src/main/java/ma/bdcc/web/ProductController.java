package ma.bdcc.web;

import jakarta.validation.Valid;
import ma.bdcc.entities.Product;
import ma.bdcc.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String listProducts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword,
            Model model) {
        Page<Product> pageProducts = productRepository.findByNameContains(
            keyword, PageRequest.of(page, size));
        model.addAttribute("products", pageProducts.getContent());
        model.addAttribute("pages", new int[pageProducts.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        return "products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        productRepository.deleteById(id);
        return "redirect:/products?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/products/add")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "formProduct";
    }

    @PostMapping("/products/save")
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "formProduct";
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return "redirect:/products";
        }
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id,
                                 @Valid @ModelAttribute("product") Product product,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "editProduct";
        }
        product.setId(id);
        productRepository.save(product);
        return "redirect:/products";
    }
}
