package uk.co.bitstyle.sbab.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import uk.co.bitstyle.sbab.spring.config.dao.silliness.SillyDao;

/**
 * Main controller rgistering handlers for index and then default any other html file name.
 *
 * @author cspiking
 */
@Controller
public class IndexController {

    @Autowired
    private SillyDao simpleDao;

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

}
