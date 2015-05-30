package name.etki.jayground.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */

@Controller
public class DescriptionController {
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
    public String getDescription()
    {
        return "/index.html";
    }
}
