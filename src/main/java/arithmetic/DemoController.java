package arithmetic;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/test")
public class DemoController {

    @RequestMapping(value = "/liwei",method = RequestMethod.GET)
    public String test(){
        return "haha";
    }

}
