package com.linden.controllers;

import com.linden.models.Account;
import com.linden.models.Admin;
import com.linden.models.User;
import com.linden.services.AdminService;
import com.linden.services.UserService;
import com.linden.util.ObjectStatusResponse;
import com.linden.util.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ObjectStatusResponse<?> login(HttpServletRequest request, HttpServletResponse response,
                                        @RequestBody Account account) {
        HttpSession session = request.getSession(true);
        User userAccount = (User) session.getAttribute("user");
        Admin adminAccount = (Admin) session.getAttribute("admin");

        if(userAccount == null && adminAccount == null){
            User userFromDb = userService.getUserByEmail(account.getEmail());
            Admin adminFromDb = adminService.getAdminByEmail(account.getEmail());
            if(userFromDb == null && adminFromDb == null){
                return new ObjectStatusResponse(
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
            return new ObjectStatusResponse(
                null,
                "ERROR",
                "Already logged in!"
            );
        }
    }

    private ObjectStatusResponse handleUserLogin(User user, HttpSession session) {
        if(userService.checkCredentials(user)){
            User userInDb = userService.getUserByEmail(user.getEmail());
            if(userInDb.isVerifiedAccount()) {
                session.setAttribute("user", userInDb);
                return new ObjectStatusResponse<>(userInDb, "OK");
            }
            else {
                return new ObjectStatusResponse(null,"ERROR", "Account not verified!");
            }
        }
        else {
            return new ObjectStatusResponse(null,"ERROR", "Invalid Credentials!");
        }
    }

    private ObjectStatusResponse handleAdminLogin(Admin admin, HttpSession session){
        if(adminService.checkCredentials(admin)){
            Admin adminInDb = adminService.getAdminByEmail(admin.getEmail());
            session.setAttribute("admin", adminInDb);
            return new ObjectStatusResponse<>(adminInDb, "OK");
        }
        else {
            return new ObjectStatusResponse(null,"ERROR", "Invalid Credentials!");
        }

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
