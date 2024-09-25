package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepo;
import com.example.demo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/users/save")
    public String page(Model model){
        model.addAttribute("user", new User());

        return "page";
    }

    @PostMapping("/users/save")
    public String save (@ModelAttribute("user") User user){

        return "redirect:/users/save";
    }

    @GetMapping("/users/show")
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

    @GetMapping("/users/delete")
    public String showDeletePage(){
        return "delete";
    }

    @PostMapping("/users/delete")
    public String deleteUser(@RequestParam Long userId, Model model) {
        try {
            userService.deleteById(userId);
            model.addAttribute("message", "User deleted successfully!");
        } catch (EmptyResultDataAccessException e) {
            model.addAttribute("message", "User not found.");
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred while deleting the user.");
        }

        return "delete"; // Redirect back to the same form with message
    }

    @GetMapping("/users/show-all")
    public String showAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());

        return "show-all";
    }

    @GetMapping("/main")
    public String showMain(){

        return "main";
    }
}