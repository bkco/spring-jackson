package co.bk.task.jackson.resource;


import co.bk.task.jackson.resource.cmd.DatasetSaveCmd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * https://stackoverflow.com/questions/11376304/right-way-to-write-json-deserializer-in-spring-or-extend-it#11377362
 *
 *
 */
@RestController
@Slf4j
public class DemoResource {

//  private final ServiceFacade serviceFacade;
//
//  private final IamService iamService;

  public DemoResource() {
  }

//  @Autowired
//  public DemoResource(final ServiceFacade serviceFacade, final IamService iamService) {
//    this.serviceFacade = serviceFacade;
//    this.iamService = iamService;
//  }

  @ResponseStatus(HttpStatus.CREATED)
  @RequestMapping(value = "/dataset", method = RequestMethod.POST, consumes = "application/json")
  public void saveDataset(@RequestBody DatasetSaveCmd datasetSaveCmd,
                        HttpServletRequest request,
                        HttpServletResponse response) {

    //datasetSaveCmd.isValid();

    System.out.println(datasetSaveCmd);
    //TODO create object....

//    response.setHeader("Location", new StringBuffer(request.getContextPath())
//            .append("/").append(accessRequest.getId()).toString());
  }

}

