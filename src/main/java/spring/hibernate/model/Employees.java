package spring.hibernate.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import spring.hibernate.HibernateEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "Employees")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Employees implements HibernateEntity {

    @OneToMany(mappedBy = "employees", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Cars> cars;


    @ManyToMany(mappedBy = "employeesSet", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Printer> printerSet;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private int id;

    @Column(name = "FirstName")
    @NonNull
    private String firstName;

    @Column(name = "LastName")
    @OrderColumn
    @NonNull
    private String lastName;

    @Column(name = "Address")
    @NonNull
    private String address;

    @Column(name = "City")
    @NonNull
    private String city;

    @Column(name = "Salary")
    @NonNull
    @EqualsAndHashCode.Exclude
    private int salary;

    @Column(name = "Age")
    @NonNull
    private int age;

    @Column(name = "StartJobDate")
    @NonNull
    @DateTimeFormat(pattern = "dd-mm-yyyy")
    @EqualsAndHashCode.Exclude
    private Date startJobDate;

    @Column(name = "Benefit")
    @NonNull
    @EqualsAndHashCode.Exclude
    private int benefit;

    @Column(name = "Email")
    @Getter
    @Setter
    private String email;

    public Employees(String firstName, String lastName, String address, String city, int salary, int age, Date startJobDate, int benefit, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.salary = salary;
        this.age = age;
        this.startJobDate = startJobDate;
        this.benefit = benefit;
        this.email = email;
    }

    public Employees(int id, String firstName, String lastName, String address, String city, int salary, int age, Date startJobDate, int benefit, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.salary = salary;
        this.age = age;
        this.startJobDate = startJobDate;
        this.benefit = benefit;
        this.email = email;
    }

    public Set<Printer> getPrintersSet() {
        if (printerSet == null) {
            printerSet = new HashSet<>();
        }
        return printerSet;
    }

    public Employees() {
    }

    public Employees(int id, String firstName, String lastName, String address, String city, int age, int salary, Date startJobDate, int benefit) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.age = age;
        this.salary = salary;
        this.startJobDate = startJobDate;
        this.benefit = benefit;
    }
}

