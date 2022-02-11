package com.shopping.cart;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ApplicationScoped
@ManagedBean
public class TestJsfBean {
  @Autowired
  private MsgService msgService;

  public String getMsg() {
      return msgService.getMsg();
  }
  
  public String terms() {
      return msgService.terms();
  }
}