package edu.gmu.swe699.composite;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderErrorController implements ErrorController {

  @RequestMapping("/error")
  public String handleError() {
    return "error";
  }

  @Override
  public String getErrorPath() {
    return "/error";
  }
}
