package ki.forecast.rest.service.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
public class RestApiController {

    private RestApiApplication restApiApplication;

    @GetMapping("/result")
    public RFModelResults result() {
        return restApiApplication.getResults();
    }

    @PostMapping("/analyze")
    public RFModelResults analyze(@RequestBody DataToAnalyze data) {
        if (data != null) {
            restApiApplication = new RestApiApplication();
            restApiApplication.setDataToAnalyze(data);
            restApiApplication.analyze();
        }
        return restApiApplication.getResults();
    }

}
