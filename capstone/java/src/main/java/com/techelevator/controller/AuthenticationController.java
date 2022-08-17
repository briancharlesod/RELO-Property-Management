package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.exceptions.*;
import com.techelevator.exceptions.UserNotFoundException;
import com.techelevator.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.dao.UserDao;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDao userDao;
    private int count = 0;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        User user = userDao.findByUsername(loginDto.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponse(jwt, user), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDTO newUser) {
        try {
            User user = userDao.findByUsername(newUser.getUsername());
            throw new UserAlreadyExistsException();
        } catch (UsernameNotFoundException e) {
            boolean test = userDao.create(newUser.getUsername(), newUser.getPassword(), newUser.getRole(), newUser.getQuestionOne(), newUser.getQuestionTwo(), newUser.getAnswerOne(), newUser.getAnswerTwo());
            System.out.println(test);
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class LoginResponse {

        private String token;
        private User user;

        LoginResponse(String token, User user) {
            this.token = token;
            this.user = user;
        }

        @JsonProperty("token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }

        @JsonProperty("user")
        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }


    @RequestMapping(path = "user/retrieval/question/{username}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String forgotPasswordQuestionOne(@PathVariable String username, @RequestBody String[] possibleQuestions) throws UserNotFoundException, RetrievalException {
        String question = null;
        count++;
        User user = userDao.findByUsername(username);
        if (user.getUsername().equalsIgnoreCase(username)) {
            question = userDao.getQuestionOne(user.getId());
            if (question == null) {
                throw new RetrievalException();
            }
        } else {
            throw new UserNotFoundException();
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        for (String aQuestion : possibleQuestions) {

            if (bcrypt.matches(aQuestion, question)) {
                return aQuestion;
            }
        }
        return null;
    }

    @RequestMapping(path = "user/retrieval/answer/{username}/{answer}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean checkAnswerOne(@PathVariable String username, @PathVariable String answer) throws UserNotFoundException, RetrievalException {
        User user = userDao.findByUsername(username);
        String resp = userDao.getAnswerOne(user.getId());
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean check = bcrypt.matches(answer, resp);
        if (check) {
            return true;
        } else {
            throw new RetrievalException();
        }
    }

    @RequestMapping(path = "user/retrieval/answerTwo/{username}/{answer}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean checkAnswerTwo(@PathVariable String answer, @PathVariable String username) throws UserNotFoundException, RetrievalException {
        User user = userDao.findByUsername(username);
        String resp = userDao.getAnswerTwo(user.getId());
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        boolean check = bcrypt.matches(answer, resp);
        if (check) {
            return true;
        } else {
            throw new RetrievalException();
        }
    }

    @RequestMapping(path = "user/retrieve/password/{username}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void updatePassword(@RequestBody PasswordRetrieval password, @PathVariable String username) throws RetrievalException {
        int userID = (userDao.findIdByUsername(username));
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        System.out.println(password.getPassword());
        System.out.println(password.getHash());
        boolean hash1 = bcrypt.matches(password.getHash(), userDao.getAnswerOne(userID));
        boolean hash2 = bcrypt.matches(password.getHash(), userDao.getAnswerTwo(userID));
        if (hash2 || hash1) {
            userDao.updatePassword(userDao.findIdByUsername(username), password.getPassword());
        }
        else {
            throw new RetrievalException();
        }
    }

    @RequestMapping(path = "user/retrieval/questionTwo/{username}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String forgotPasswordQuestionTwo(@PathVariable String username, @RequestBody String[] possibleQuestions) throws UserNotFoundException, RetrievalException {
        String question = null;
        User user = userDao.findByUsername(username);
        if (user.getUsername().equalsIgnoreCase(username)) {
            question = userDao.getQuestionTwo(user.getId());
            if (question == null) {
                throw new RetrievalException();
            }
        } else {
            throw new UserNotFoundException();
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        for (String aQuestion : possibleQuestions) {
            if (bcrypt.matches(aQuestion, question)) {
                System.out.println(aQuestion);
                return aQuestion;
            }
        }
        return question;
    }

    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "user/set/rental", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void setUserToProperty(@RequestBody @Valid UserRental ur, Principal principal) throws UserToPropertyException {
        boolean setUSer = userDao.setUserToProperty(ur.getUserID(), ur.getRentalID(), principal.getName());
        if (!setUSer) {
            throw new UserToPropertyException();
        }
    }

    @PreAuthorize("hasRole('ROLE_LANDLORD')")
    @RequestMapping(path = "/rental/renters/{rentalId}", method = RequestMethod.GET)
    public List<Renter> getUsersFromProperty(@PathVariable int rentalId) throws UserToPropertyException {
        List<Renter> userList = userDao.getUsersOfRentersByRentalId(rentalId);
        if (userList == null) {
            throw new UserToPropertyException();
        }
        return userList;

    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE') or hasRole('ROLE_LANDLORD')")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "user/set/maintenance", method = RequestMethod.POST)
    public void setUserToMaintenance(@RequestBody @Valid UserMaintenance uR, Principal principal) throws UserToMaintenanceException {
        System.out.println("456");
        boolean setUser = userDao.setUserToMaintenance(uR.getUserID(), uR.getMaintenanceID(), principal.getName());
        System.out.println("123");
        if (!setUser) {
            throw new UserToMaintenanceException();
        }
    }
}

