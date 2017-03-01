package activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.misc.Contended;

/**
 * Created by song on 2017/1/7.
 */
@Controller
public class RootController {
    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }


}
