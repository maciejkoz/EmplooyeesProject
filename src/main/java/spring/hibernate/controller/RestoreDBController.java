package spring.hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.hibernate.HibernateDao;
import spring.hibernate.model.Cars;
import spring.hibernate.model.Employees;
import spring.hibernate.model.Printer;

import javax.persistence.EntityManager;
import java.util.*;

@Controller
public class RestoreDBController {

    private List<Cars> carsList;
    private List<Employees> employeesList;
    private List<Printer> printerList;
    private HibernateDao hibernateDao;
    private EntityManager entityManager;


    @RequestMapping("/restoreDataBase")
    public String RestoreDB() {

        List<Printer> allPrinter = printerList;
        for (Printer printer : allPrinter) {
            entityManager.persist(printer);
            entityManager.remove(printer);
        }

        List<Cars> allCars = carsList;
        for (Cars cars : allCars) {
            entityManager.persist(cars);
            entityManager.remove(cars);
        }

        List<Employees> allEmployees = employeesList;
        for (Employees employees : allEmployees) {
            entityManager.persist(employees);
            entityManager.remove(employees);
        }



        Employees employee1 = new Employees(1, "Jan", "Kran", "Długa 1", "Warszawa", 40, 3000, new Date(), 1);
        Employees employee2 = new Employees(2, "Zbigniew", "Miednica", "Emaliowa 2", "Kraków", 30, 4000, new Date(), 0);
        Employees employee3 = new Employees(3, "Anna", "Żelazna", "Rdzawa 3", "Warszawa", 35, 4500, new Date(), 1);

        List<Employees> employeesList = Arrays.asList(employee1, employee2, employee3);
        for (Employees employee : employeesList) {
            entityManager.persist(employee);
        }

        Cars car1 = new Cars(employee1, 1, "Audi", "A4", new Date());
        Cars car2 = new Cars(employee2, 2, "BMW", "525", new Date());
        Cars car3 = new Cars(employee3, "Chrysler", "Voyager", new Date());


        List<Cars> carsList = Arrays.asList(car1, car2, car3);
        for (Cars car : carsList) {
            entityManager.persist(car);
        }

        Set<Employees> setForPrinter1 = new HashSet<>(Arrays.asList(employee1, employee2, employee3));
        Set<Employees> setForPrinter2 = new HashSet<>(Arrays.asList(employee1, employee2, employee3));
        Set<Employees> setForPrinter3 = new HashSet<>(Arrays.asList(employee2, employee3));

        Printer printer1 = new Printer(1, "Cannon", "C01");
        Printer printer2 = new Printer(2, "Lexmark", "L02");
        Printer printer3 = new Printer(3, "HP", "HP03");

        List<Printer> printersList = Arrays.asList(printer1, printer2, printer3);
        for (Printer printer : printersList) {
            entityManager.persist(printer);
        }

        return "/index";
    }
}
