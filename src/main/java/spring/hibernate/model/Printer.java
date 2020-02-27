package spring.hibernate.model;

import lombok.*;
import spring.hibernate.HibernateEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Printers")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Printer implements HibernateEntity {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "Employees_Printers",
            joinColumns = {@JoinColumn(name = "printerId", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "employeeId", referencedColumnName = "ID")})

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Employees> employeesSet;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    @Column(name = "ID")
    private int id;



    @Column(name = "Brand")
    @NonNull
    private String brand;

    @Column(name = "Model")
    @NonNull
    private String model;

    public Set<Employees> getEmployeesSet() {
        if (employeesSet == null) {
            employeesSet = new HashSet<>();
        }
        return employeesSet;
    }

    public Printer() {
    }

    public Printer(Set<Employees> employeesSet, @NonNull String brand, @NonNull String model) {
        this.employeesSet = employeesSet;
        this.brand = brand;
        this.model = model;
         }

    public Printer(int id, @NonNull String brand, @NonNull String model) {
        this.id = id;
        this.brand = brand;
        this.model = model;
    }
}

