package parser.KufarParser.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.ManyToAny;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity(name = "users")
public class User {

    @Id
    private Long chatId;

    private String firstName;

    private String lastName;

    private String userName;

    private Timestamp registeredAt;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn
    private State state;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_link",
            joinColumns = @JoinColumn(name = "chatId", referencedColumnName = "chatId"),
            inverseJoinColumns = @JoinColumn(name = "link_id", referencedColumnName = "id")
    )
    private List<Link> links;

}