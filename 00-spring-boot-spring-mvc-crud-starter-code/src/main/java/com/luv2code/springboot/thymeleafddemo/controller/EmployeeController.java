package com.luv2code.springboot.thymeleafddemo.controller;

import com.luv2code.springboot.thymeleafddemo.entity.Employee;
import com.luv2code.springboot.thymeleafddemo.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
   private EmployeeService employeeService ;
   public EmployeeController(EmployeeService theEmployeeService){
       employeeService=theEmployeeService;
   }
   //add mapping for the list

    @GetMapping("/list")
       public String ListEmployees(Model theModel){

       //get the employee from db
        List<Employee> theEmployees=employeeService.findAll();

        //add it to the spring model
        theModel.addAttribute("employees",theEmployees);
        return "employees/list-employees";

    }
    @GetMapping("/showFormForAdd")
    public String showFormforAdd(Model theModel){

       //create model attribute to bind the form data
        Employee theEmployee=new Employee();
        theModel.addAttribute("employee" ,theEmployee);
        return "employees/employee-form";

   }

   @GetMapping("/showFormForUpdate")
   public String showFormForUpdate(@RequestParam("employeeId") int theId,Model theModel){

       // get the employee from the service
       Employee theEmployee=employeeService.findById(theId);

       //set the employee as model attribute
       theModel.addAttribute("employee",theEmployee);

      // send over to form
       return "employees/employee-form";

    }
@PostMapping("/save")
    public String saveEmployee(@ModelAttribute("employee") Employee theEmployee){
       //save th employee
    employeeService.save(theEmployee);

    //use a redirect to prevent duplicaTE SUBMISSIONS ( post/redirect/get pattern )
    return "redirect:/employees/list";

   }
   @GetMapping("/delete")
    public String delete (@RequestParam("employeeId")int theId){
       //delete the employee
       employeeService.deleteById(theId);

       //redirect to the /employees/list
       return "redirect:/employees/list";
   }
}
