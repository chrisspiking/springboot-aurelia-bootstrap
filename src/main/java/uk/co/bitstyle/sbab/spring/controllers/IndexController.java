package uk.co.bitstyle.sbab.spring.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.co.bitstyle.sbab.model.AppUser;
import uk.co.bitstyle.sbab.services.dao.user.AppUserDao;
import uk.co.bitstyle.sbab.services.dao.user.AppUserDaoOpResult;
import uk.co.bitstyle.sbab.spring.config.dao.silliness.SillyDao;

import java.util.LinkedList;
import java.util.List;

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
    private AppUserDao appUserDao;

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
        final List<GrantedAuthority> authorityList = new LinkedList<>();
        authorityList.add(new SimpleGrantedAuthority("SIMPLE_USER"));

        final AppUser newUser =
                new AppUser.Builder()
                .setAccountNonExpired(true)
                .setAccountNonLocked(true)
                .setAuthorities(authorityList)
                .setCredentialsNonExpired(true)
                .setEmail(username + "@" + username + "y7c89ac8e9.com")
                .setEnabled(true)
                .setFirstName(username + " First")
                .setLastName(username + " Last")
                .setPassword(username + "pwd")
                .setUsername(username)
                .setReceiveUpdateEmails(true)
                .setRegistrationTime(System.currentTimeMillis())
                .build();

        final AppUserDaoOpResult<AppUser> result = appUserDao.createUser(newUser);
        logger.info("Create User result : {}", result);
    }
}
