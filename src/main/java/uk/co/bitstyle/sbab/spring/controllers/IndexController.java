package uk.co.bitstyle.sbab.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.co.bitstyle.sbab.model.AppUser;
import uk.co.bitstyle.sbab.services.user.AppUserRegistrationRequest;
import uk.co.bitstyle.sbab.services.user.AppUserService;
import uk.co.bitstyle.sbab.spring.config.dao.silliness.SillyDao;

/**
 * Main controller rgistering handlers for index and then default any other html file name.
 *
 * @author cspiking
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private SillyDao simpleDao;

    @Autowired
    private AppUserService appUserService;

    @RequestMapping(value={"/", "index"})
    public String index() {
        return "index";
    }

    @RequestMapping("springDbPropsTest")
    @ResponseStatus(HttpStatus.OK)
    public void springTest() {
        simpleDao.doSomething();
    }

    @RequestMapping("/src/{htmlFileName}.html")
    public String staticFileSupport(@PathVariable String htmlFileName) {
        return htmlFileName;
    }

    @RequestMapping("createUser")
    @ResponseStatus(HttpStatus.OK)
    public void createUser(@RequestParam("username") String username) {
        final AppUserRegistrationRequest regRequest =
                new AppUserRegistrationRequest()
                    .withUsername(username)
                    .withPassword(username + "pwd")
                    .withFirstName(username)
                    .withLastName(username)
                    .withReceiveEmails(false);

        final AppUser appUser = appUserService.registerNewUser(regRequest);
        logger.info("Create User result : {}", appUser);
    }
}
