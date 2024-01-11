package parser.KufarParser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String url;

    private String description;

    @ManyToMany(mappedBy = "links")
    private List<User> users;

    public Link(String url, List<User> users) {
        this.url = url;
        this.users = users;
    }
}
