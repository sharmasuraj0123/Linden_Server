package com.linden.controllers;

import com.linden.models.accounts.Account;
import com.linden.models.accounts.Admin;
import com.linden.models.accounts.User;
import com.linden.models.accounts.Verification;
import com.linden.services.AccountTokenService;
import com.linden.services.AdminService;
import com.linden.services.UserService;
import com.linden.services.VerificationService;
import com.linden.util.ObjectStatusResponse;
import com.linden.util.StatusResponse;
import com.linden.util.Token;
import com.linden.util.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AccountTokenService accountTokenService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    public JavaMailSender emailSender;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Account account) {
        HttpSession session = request.getSession(true);
        User userAccount = (User) session.getAttribute("user");
        Admin adminAccount = (Admin) session.getAttribute("admin");

        if(userAccount == null && adminAccount == null){
            User userFromDb = userService.getUserByEmail(account.getEmail());
            Admin adminFromDb = adminService.getAdminByEmail(account.getEmail());
            if(userFromDb == null && adminFromDb == null){
                return new ObjectStatusResponse<>(
                    null,
                    "ERROR",
                    "Account not registered!"
                );
            }
            else {
                if(userFromDb != null){
                    return handleUserLogin(
                            new User(account.getEmail(), account.getPassword()),
                            session
                    );
                }
                else {
                    return handleAdminLogin(
                            new Admin(account.getEmail(), account.getPassword()),
                            session
                    );
                }
            }
        }
        else{
            return new ObjectStatusResponse<>(
                null,
                "ERROR",
                "Already logged in!"
            );
        }
    }

    private ObjectStatusResponse<?> handleUserLogin(User user, HttpSession session) {
        if(userService.checkCredentials(user)){
            User userInDb = userService.getUserByEmail(user.getEmail());
            if(userInDb.isVerifiedAccount()) {
                session.setAttribute("user", userInDb);
                userInDb.setToken(accountTokenService.saveAccount(userInDb.getId()));
                return new ObjectStatusResponse<>(userInDb, "OK");
            }
            else {
                return new ObjectStatusResponse<>(null,"ERROR", "Account not verified!");
            }
        }
        else {
            return new ObjectStatusResponse<>(null,"ERROR", "Invalid Credentials!");
        }
    }

    private ObjectStatusResponse<?> handleAdminLogin(Admin admin, HttpSession session){
        if(adminService.checkCredentials(admin)){
            Admin adminInDb = adminService.getAdminByEmail(admin.getEmail());
            session.setAttribute("admin", adminInDb);
            adminInDb.setToken(accountTokenService.saveAccount(admin.getId(), true));
            return new ObjectStatusResponse<>(adminInDb, "OK");
        }
        else {
            return new ObjectStatusResponse<>(null,"ERROR", "Invalid Credentials!");
        }

    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse logout(HttpServletRequest request, @RequestBody Token token) {
        HttpSession session = request.getSession();
        session.invalidate();
        accountTokenService.invalidateToken(token.getToken());
        return new StatusResponse("OK", "Logged out.");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse register(@RequestBody User user){
        UserService.RegistrationStatus status = userService.registerUser(user);
        switch (status) {
            case OK:
                sendVerificationEmail(user);
                return new StatusResponse("OK");
            case EMAIL_TAKEN:
                return new StatusResponse("ERROR", "Email already taken!");
        }
        // Should never be reached
        return new StatusResponse("ERROR", "An unknown error has occurred...");
    }

    private void sendVerificationEmail(User user) {
        if(user != null) {
            Verification verification = verificationService.generateVerification(user);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Linden Verification Email");
            message.setText("Welcome to Linden!\n\n" +
                            "\tWe hope you enjoy your experience here at Linden. To verify your account please follow this link:\n" +
                            "http://localhost:3000/verify/"+user.getId()+"/"+verification.getToken()+"\n\n" +
                            "Regards,\n" +
                            "Linden Team");
            emailSender.send(message);
        }
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse verify(@RequestBody VerificationToken token) {
        if(verificationService.verfiyAccount(token.getUserId(), token.getToken())){
            return new StatusResponse("OK");
        }
        else {
            return new StatusResponse("Error", "Unable to verify account!");
        }
    }
}
