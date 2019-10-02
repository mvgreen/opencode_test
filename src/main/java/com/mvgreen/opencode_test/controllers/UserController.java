package com.mvgreen.opencode_test.controllers;

import com.mvgreen.opencode_test.game.GameController;
import com.mvgreen.opencode_test.game.Guess;
import com.mvgreen.opencode_test.entities.UserData;
import com.mvgreen.opencode_test.services.SecurityService;
import com.mvgreen.opencode_test.services.UserService;
import com.mvgreen.opencode_test.validators.RegistrationFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private RegistrationFormValidator validator;

    @Autowired
    private GameController gameController;

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Неверные логин или пароль");

        if (logout != null)
            model.addAttribute("logout", "Вы вышли");

        return "loginPage";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new UserData());
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String confirmRegistration(@ModelAttribute("userForm") UserData userForm, BindingResult bindingResult) {
        validator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors())
            return "registrationPage";

        String password = userForm.getPassword();
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), password);

        return "redirect:/game";
    }

    @PostMapping(value = "/game", consumes = "application/json", produces = "application/json")
    public @ResponseBody Guess guess(@RequestBody Guess guess) {
        gameController.checkAttempt(guess);
        return guess;
    }

    @GetMapping({"/", "/game"})
    public String game(Model model) {
        gameController.startGame();
        return "gamePage";
    }

    @GetMapping("/highscores")
    public String highscoreTable(Model model) {
        model.addAttribute("userList", userService.getHighScoreTable());
        return "highscorePage";
    }
}
