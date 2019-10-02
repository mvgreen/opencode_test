package com.mvgreen.opencode_test.game;

import com.mvgreen.opencode_test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GameController {

    public static final int NUMBER_LENGTH = 4;

    @Autowired
    private UserService userService;

    private int[] secretNumber = null;
    private int attempts;
    private boolean gameOver = true;

    public void startGame() {
        if (secretNumber == null)
            secretNumber = new int[NUMBER_LENGTH];
        List<Integer> digits = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        Collections.shuffle(digits);
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            secretNumber[i] = digits.get(i);
        }
        attempts = 0;
        gameOver = false;
    }

    public void checkAttempt(Guess guess) {
        if (gameOver)
            startGame();
        validateGuess(guess);
        generateAnswer(guess);
    }

    private void validateGuess(Guess guess) {
        if (guess.getGuessedNumber().length != NUMBER_LENGTH)
            throw new IllegalArgumentException("guessedNumber has invalid length. Expected: " + NUMBER_LENGTH +
                    ", found: " + guess.getGuessedNumber().length);
        int[] numbers = guess.getGuessedNumber();
        for (int i = 0; i < numbers.length - 1; i++) {
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[i] == numbers[j])
                    throw new IllegalArgumentException("guessedNumber is invalid: every digit must be unique");
            }
        }
    }

    private void generateAnswer(Guess guess) {
        attempts++;
        int cows = 0;
        int bulls = 0;
        int[] guessedNumber = guess.getGuessedNumber();
        for (int i = 0; i < NUMBER_LENGTH; i++) {
            if (guessedNumber[i] == secretNumber[i]) {
                bulls++;
            } else {
                int j = 0;
                while (j < NUMBER_LENGTH && guessedNumber[i] != secretNumber[j])
                    j++;
                if (j < NUMBER_LENGTH)
                    cows++;
            }
        }
        guess.setCows(cows);
        guess.setBulls(bulls);
        if (bulls == NUMBER_LENGTH) {
            guess.setScore(userService.updateScore(
                    ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername(),
                    attempts));
            gameOver = true;
        }
    }

}
