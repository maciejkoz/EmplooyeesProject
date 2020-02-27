package spring.hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import spring.hibernate.DataSource;
import spring.hibernate.model.Employees;
import spring.hibernate.HibernateDao;
import spring.hibernate.model.Printer;

import java.util.*;

@Controller
@RequestMapping("printer")
public class PrinterController {
    private List<Printer> printerList;
    private List<Employees> employeesList;
    private HibernateDao hibernateDao;


    public PrinterController() {
        try {
            hibernateDao = new HibernateDao();
            DataSource.supplyDatabase();
            employeesList = hibernateDao.get(Employees.class);
            printerList = hibernateDao.get(Printer.class);
        } catch (NullPointerException exception) {
            System.out.println("No connection with database");
            exception.getMessage();

            employeesList = new ArrayList<>();
            Employees employee1 = new Employees(1, "Jan", "Kran", "Długa 1", "Warszawa", 40, 3000, new Date(), 1);
            Employees employee2 = new Employees(2, "Zbigniew", "Miednica", "Emaliowa 2", "Kraków", 30, 4000, new Date(), 0);
            Employees employee3 = new Employees(3, "Anna", "Żelazna", "Rdzawa 3", "Warszawa", 35, 4500, new Date(), 1);
            employeesList.addAll(Arrays.asList(employee1, employee2, employee3));

            printerList = new ArrayList<>();
            Set<Employees> setForPrinter1 = new HashSet<>(Collections.singletonList(employee1));
            Set<Employees> setForPrinter2 = new HashSet<>(Arrays.asList(employee1, employee2, employee3));

            Printer printer1 = new Printer(setForPrinter1, 1, "Cannon", "C01");
            Printer printer2 = new Printer(2, "Lexmark", "L02");
            Printer printer3 = new Printer(setForPrinter2, 3, "HP", "HP03");
            printerList.addAll(Arrays.asList(printer1, printer2, printer3));
        }
    }

    @RequestMapping("/seeAll")
    public ModelAndView showPrinterList(Model model) {
        return new ModelAndView("printers-list", "printersList", printerList);
    }

    @RequestMapping(value = "/getForm", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("printer", new Printer());
        model.addAttribute("employeesList", employeesList);
        model.addAttribute("chosenEmployeesIdsList", new ArrayList<>());
        return "printer-form";
    }


    @RequestMapping(value = "/save")
    public ModelAndView save(@ModelAttribute(value = "printer") Printer printer,
                             @ModelAttribute(value = "chosenEmployeesIdsList") ArrayList<String> chosenEmployeesIdsList) {
        List<Integer> idsToIntList = new ArrayList<>();
        for (String idToConvert : chosenEmployeesIdsList) {
            Integer id = Integer.parseInt(idToConvert);
            idsToIntList.add(id);
        }

        Set<Employees> chosenEmployees = new HashSet<>();
        for (Employees employees : employeesList) {
            if (idsToIntList.contains(employees.getId())) {
                chosenEmployees.add(employees);
            }
        }
        printer.setEmployeesSet(chosenEmployees);

        if (printer.getId() == 0) {
            addPrinterToDatabase(printer);
            printer.setId(printerList.size());
            printerList.add(printer);
        } else {
            updatePrinterInDatabase(printer);
            printerList.set(printer.getId() - 1, printer);
        }
        return new ModelAndView("redirect:/printer/seeAll");
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@ModelAttribute(value = "printer_id") String printerId) {
        Printer printer = getPrinterById(Integer.parseInt(printerId));
        deletePrinterFromDatabase(printer);
        printerList.remove(printer);
        return new ModelAndView("redirect:/printer/seeAll");
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView edit(@RequestParam(value = "printer_id") String printerId) {
        Printer printer = getPrinterById(Integer.parseInt(printerId));
        return new ModelAndView("printer-form", "printer", printer);
    }

    private Printer getPrinterById(@RequestParam int id) {
        return printerList.stream().filter(f -> f.getId() == id).findFirst().get();
    }

    private void addPrinterToDatabase(Printer printer) {
        try {
            hibernateDao.saveEntity(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void updatePrinterInDatabase(Printer printer) {
        try {
            hibernateDao.updateEntity(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void deletePrinterFromDatabase(Printer printer) {
        try {
            hibernateDao.deleteEntity(printer);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}
