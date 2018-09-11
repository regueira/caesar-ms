package com.caesar.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class LoginController {

//    @Autowired
//    private JdbcClientDetailsService clientsDetailsService;

//    @Autowired
//    private ApprovalStore approvalStore;
//
//    @Autowired
//    private TokenStore tokenStore;

    @RequestMapping("/success")
    public ModelAndView success(Map<String,Object> model, Principal principal){

//        Permite ver el listado de aplicaciones con accesos.
//
//        List<Approval> approvals= clientsDetailsService.listClientDetails().stream()
//            .map(clientDetails -> approvalStore.getApprovals(principal.getName(),clientDetails.getClientId()))
//            .flatMap(Collection::stream)
//            .collect(Collectors.toList());
//
//
//        model.put("approvals",approvals);
//        model.put("clientDetails",clientsDetailsService.listClientDetails());
        return new ModelAndView ("loggedin",model);

    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @PostMapping("/approval/revoke")
    public String revokeApproval(@ModelAttribute Approval approval){

//        Permite el revoke de una aplicacion de un usuario.
//
//        approvalStore.revokeApprovals(Collections.singletonList(approval));
//        tokenStore.findTokensByClientIdAndUserName(approval.getClientId(),approval.getUserId())
//            .forEach(tokenStore::removeAccessToken) ;
        return "redirect:/success";
    }
}
