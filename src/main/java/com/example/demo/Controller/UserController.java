package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/user")
    public String user(){
        Date d = new Date();
        ArrayList<User> users = new ArrayList<>();

        User user1 = new User();
        user1.setAge(20);
        user1.setDate(d);
        user1.setName("test1");
        users.add(user1);

        User user2 = new User();
        user2.setAge(80);
        user2.setDate(d);
        user2.setName("test2");
        users.add(user2);

        User user3 = new User();
        user3.setAge(10);
        user3.setDate(d);
        user3.setName("test3");
        users.add(user3);

        for (User _user : users) {
            userRepo.save(_user);
        }

        return "index";
    }

//    @GetMapping("/save")
//    public void saveUser(User user){
//
//
//        userRepo.save(user);
//    }

//    @GetMapping("/employee")
//    public String showForm(Model model){
//        model.addAttribute("employee", new Employee());
//        return "index";
//    }
//
//    @PostMapping("/employee")
//    public String submitEmpForm(@ModelAttribute("employee") Employee employee) {
//        employeesService.save(employee);
//        return "redirect:/employee";
//
//    }


    @GetMapping("/save")
    public String page(Model model){
        model.addAttribute("user", new User());

        return "page";
    }

    @PostMapping("/save")
    public String save (@ModelAttribute("user") User user){

        return "redirect:/save";
    }

    @GetMapping("/show")
    public String show(@RequestParam(value = "searchDate", required = false) String searchDate, Model model){
        List<User> users;

        if(searchDate != null){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = formatter.parse(searchDate);

                users = userRepo.findByDateOnly(date);
            }catch (ParseException e){
                users = userRepo.findAll();
            }
        }else {
            users = userRepo.findAll();
        }

        model.addAttribute("users", users);

        return "show";
    }

    @PostMapping("/search")
    public String search(@ModelAttribute("user") User user, Model model) {
        Date searchDate = user.getDate();

        Calendar cl = Calendar.getInstance();
        cl.setTime(searchDate);

        List<User> _users = userRepo.findByDate(searchDate);

        model.addAttribute("users", _users);

        return "show";
    }

    @GetMapping("/users/{id}")
    public String delete(@PathVariable Long id){
        userService.deleteById(id);

        return "redirect:/show";
    }
}