package mack.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
     public void execute();
     public void init(HttpServletRequest request);
     public String getReturnPage();
}
