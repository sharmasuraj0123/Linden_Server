package com.linden.controllers;

import com.linden.models.Account;
import com.linden.models.Admin;
import com.linden.models.User;
import com.linden.services.AccountService;
import com.linden.services.AdminService;
import com.linden.services.UserService;
import com.linden.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse login(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody Account account) {
        HttpSession session = request.getSession(true);
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            Account accountInDatabase = accountService.getAccountByEmail(account.getEmail());
            if(accountInDatabase == null) {
                return new StatusResponse("ERROR", "Account not registered!");
            }
            else {
                if(accountService.checkCredentials(account, accountInDatabase)) {
                    if (accountInDatabase instanceof User) {
                        return handleUserLogin(accountInDatabase, session);
                    } else if (accountInDatabase instanceof Admin) {
                        return handleAdminLogin(accountInDatabase, session);
                    } else {
                        return new StatusResponse("ERROR", "Account not yet verified.");
                    }
                }
                else {
                    return new StatusResponse("ERROR", "Invalid credentials!");
                }
            }
        } else {
            return new StatusResponse("ERROR", "Already logged in!");
        }
    }

    private StatusResponse handleUserLogin(Account account, HttpSession session) {
        if(((User)account).isVerifiedAccount()) {
            session.setAttribute("account", account);
            session.setAttribute("user", userService.getUserByEmail(account.getEmail()));
            return new StatusResponse("OK");
        }
        else {
            return new StatusResponse("ERROR", "Account not verified!");
        }
    }

    private StatusResponse handleAdminLogin(Account account, HttpSession session){
        session.setAttribute("account", account);
        session.setAttribute("admin", adminService.getAdminByEmail(account.getEmail()));
        return new StatusResponse("OK");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public StatusResponse logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        return new StatusResponse("OK", "Logged out.");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public StatusResponse register(HttpServletRequest request, HttpServletResponse response,
                                   @RequestBody User user){
        UserService.RegistrationStatus status = userService.registerUser(user);
        switch (status){
            case OK:
                return new StatusResponse("OK");
            case EMAIL_TAKEN:
            case USERNAME_TAKEN:
                return new StatusResponse("ERROR", status.toString());
            default:
                // This should never occur, just placed here for consistency.
                return new StatusResponse("ERROR", "An unknown error occurred!");
        }
    }


}
