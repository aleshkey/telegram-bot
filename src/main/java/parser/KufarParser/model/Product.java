package parser.KufarParser.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private String price;

    @Column(name = "link")
    private String link;

    @Override
    public String toString() {
        return "title= " + title + '\n' +
                "price= " + price + '\n' +
                "link= " + link;
    }
}
