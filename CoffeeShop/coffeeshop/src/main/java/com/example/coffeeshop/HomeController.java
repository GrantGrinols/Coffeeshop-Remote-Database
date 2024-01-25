package com.example.coffeeshop;



import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;



@Controller
public class HomeController {
    
    @GetMapping("/")
    public String Index() {
        return "index.html";
    }
    @GetMapping("/birthday")
    public String getMethodName(Model model) {
        String message = "";
        List<String> firstName = new ArrayList<>();
        List<String> lastName = new ArrayList<>();
        List<String[]> fullNames = new BirthdayChecker().GetBirthdays();
        for(String[] fullName : fullNames){
            firstName.add(fullName[0]);
            lastName.add(fullName[1]);
        }
        if(fullNames.size()==0){
            message = "Sadly, there are no birthdays today :(";
        }
        model.addAttribute("message", message);
        model.addAttribute("fullNames", fullNames);
        return "birthday.html";
    }
    
    
}
