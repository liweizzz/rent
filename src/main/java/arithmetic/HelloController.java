package arithmetic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private final Logger logger = LoggerFactory.getLogger(HelloController.class);

//    @Autowired
//    private DiscoveryClient client;

//    @RequestMapping("/hello")
//    public List<String> index(){
//        List<String> services = client.getServices();
//        return services;
//    }

}
