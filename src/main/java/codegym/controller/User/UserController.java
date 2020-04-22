package codegym.controller.User;


import com.codegym.model.User.Role;
import com.codegym.model.User.User;
import com.codegym.service.User.IUserService;
import com.codegym.validator.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.codegym.controller.Product.ProductAjax.productListAddToCart;

@Controller
public class UserController {

    @Autowired
    IUserService userService;

    @Autowired
    Role roleUser;

    @GetMapping("/signup")
    public String showFormSignup(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("amountCart", productListAddToCart.size());
        return "register";
    }

    @PostMapping("/signup")
    public String doSignup(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, Model model, @RequestParam String repassword) {
        new UserValidation().validate(user, bindingResult);
        model.addAttribute("amountCart", productListAddToCart.size());
        if (bindingResult.hasFieldErrors()) {
//            model.addAttribute("message","error");

            return "register";
        }
        if (!user.getPassword().equals(repassword)) {
            model.addAttribute("messageRePass", "pass not match");
            return "register";
        }

        if (userService.isUserExist(user)) {
            model.addAttribute("message", "ten dang nhap da ton tai");
            return "register";
        } else {
            user.setRole(roleUser);
            userService.addUser(user);
            model.addAttribute("message", "dang ki thanh cong");
            return "login";
        }
    }
}
