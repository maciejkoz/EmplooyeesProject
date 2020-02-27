package spring.hibernate;

import spring.hibernate.model.Cars;
import spring.hibernate.model.Employees;
import spring.hibernate.model.Printer;

import java.util.*;

public class DataSource {

    public static boolean isDatabaseConnection = Boolean.FALSE;

    public static void supplyDatabase() {
        HibernateDao hibernateDao = null;

        try {
            hibernateDao = new HibernateDao();
            isDatabaseConnection = Boolean.TRUE;
        } catch (NullPointerException e) {
            System.out.println("No database connection.");
            e.getStackTrace();
        }

        Employees employee1 = new Employees(1, "Jan", "Kran", "Długa 1", "Warszawa", 40, 3000, new Date(), 1);
        Employees employee2 = new Employees(2, "Zbigniew", "Miednica", "Emaliowa 2", "Kraków", 30, 4000, new Date(), 0);
        Employees employee3 = new Employees(3, "Anna", "Żelazna", "Rdzawa 3", "Warszawa", 35, 4500, new Date(), 1);

        List<Employees> verificationList = hibernateDao.get(Employees.class);

        List<Employees> listToAdd = new ArrayList<>(Arrays.asList(employee1, employee2, employee3));
        listToAdd.removeAll(verificationList);

        for (Employees employee : listToAdd) {
            hibernateDao.saveEntity(employee);
        }

        Cars car1 = new Cars(employee1, "Audi", "B4", new Date());
        Cars car2 = new Cars(employee2, "BMW", "525", new Date());
        Cars car3 = new Cars(employee3, "Chrysler", "Voyager", new Date());


        hibernateDao.saveEntity(car1);
        hibernateDao.saveEntity(car2);
        hibernateDao.saveEntity(car3);


        Set<Employees> setForPrinter1 = new HashSet<>(listToAdd);
        Set<Employees> setForPrinter2 = new HashSet<>(Arrays.asList(employee1, employee2, employee3));
        Set<Employees> setForPrinter3 = new HashSet<>(Arrays.asList(employee2, employee3));

        Printer printer1 = new Printer("Cannon ", "C01");
        Printer printer2 = new Printer("Lexmark", "L02");
        Printer printer3 = new Printer("HP", "HP03");

        List<Printer> printerVerificationList = hibernateDao.get(Printer.class);


        List<Printer> printerListToAdd = new ArrayList<>(Arrays.asList(printer1, printer2, printer3));
        printerListToAdd.removeAll(printerVerificationList);

        for (Printer printer : printerListToAdd) {
            hibernateDao.saveEntity(printer);
        }

    }

}
