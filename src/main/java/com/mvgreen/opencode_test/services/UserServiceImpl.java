package com.mvgreen.opencode_test.services;

import com.mvgreen.opencode_test.entities.UserRole;
import com.mvgreen.opencode_test.entities.UserData;
import com.mvgreen.opencode_test.repositories.RoleRepository;
import com.mvgreen.opencode_test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void save(UserData user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        HashSet<UserRole> roles = new HashSet<>();
        roles.add(roleRepository.findByName("User"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UserData findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void grantRole(String username, String grantedRole) {
        UserData user;
        UserRole role;
        user = userRepository.findByUsername(username);
        if (user == null)
            throw new  DataRetrievalFailureException("No such user: " + username);
        role = roleRepository.findByName(grantedRole);
        if (role == null)
            throw new DataRetrievalFailureException("No such role: " + grantedRole);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public float updateScore(String username, int attempts) {
        UserData user = userRepository.findByUsername(username);
        if (user == null)
            throw new DataRetrievalFailureException("No such user: " + username);
        int amountOfGames = user.getAmountOfGames() + 1;
        int sumOfAttempts = user.getSumOfAttempts() + attempts;
        user.setAmountOfGames(amountOfGames);
        user.setSumOfAttempts(sumOfAttempts);
        userRepository.save(user);
        return amountOfGames == 0 ? 0 : sumOfAttempts / (float) amountOfGames;
    }

    @Override
    public List<UserData> getHighScoreTable() {
        List<UserData> users = userRepository.findAll();
        users.forEach(user -> user.setScore(user.getAmountOfGames() == 0 ?
                0 : user.getSumOfAttempts() / ((float) user.getAmountOfGames())));
        users.sort((user1, user2) -> {
            float average1 = user1.getScore();
            float average2 = user2.getScore();
            float difference = Math.abs(average1 - average2);
            float epsilon = (float) 1e-10;
            if (difference < epsilon)
                return 0;
            if (average1 > average2)
                return -1;
            else
                return 1;
        });
        return users;
    }

}
