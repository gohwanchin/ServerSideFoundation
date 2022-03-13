package sg.edu.nus.revision2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculate")
public class CalculateController {
    @GetMapping
    public String getCalculate() {
        return "calculate";
    }

    @PostMapping
    public String postCalculate(@RequestBody MultiValueMap<String, String> form, Model model) {
        int x, y, sum = 0;

        try {
            x = Integer.parseInt(form.getFirst("x"));
            y = Integer.parseInt(form.getFirst("y"));
        } catch (NumberFormatException e) {
            model.addAttribute("msg", "Invalid format, only numbers accepted!");
            return "error";
        }
        String op = form.getFirst("op");
        switch (op) {
            case "add":
                sum = x + y;
                break;
            case "minus":
                sum = x - y;
                break;
            case "multiply":
                sum = x * y;
                break;
            case "divide":
                sum = x / y;
                break;
        }
        if (sum < 0 || sum > 30) {
            model.addAttribute("msg", sum + "out of range");
            return "error";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/numbers/number").append(sum).append(".jpg");
        String sumUrl = sb.toString();
        model.addAttribute("num", sumUrl);
        return "number";
    }
}
