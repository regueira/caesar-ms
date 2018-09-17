package com.caesar.auth.controller;

import com.caesar.auth.config.AuthorityPropertyEditor;
import com.caesar.auth.config.SplitCollectionEditor;
import com.caesar.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private JdbcClientDetailsService clientsDetailsService;

    @Autowired
    private ApprovalStore approvalStore;

    @Autowired
    private TokenStore tokenStore;

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.registerCustomEditor(Collection.class, new SplitCollectionEditor(Set.class,","));
        binder.registerCustomEditor(GrantedAuthority.class,new AuthorityPropertyEditor());
    }

    @GetMapping("/")
    public ModelAndView index(Map<String,Object> model, @AuthenticationPrincipal final User user){

        List<Approval> approvals= clientsDetailsService.listClientDetails().stream()
            .map(clientDetails -> approvalStore.getApprovals(user.getUsername(),clientDetails.getClientId()))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        model.put("approvals",approvals);
        model.put("clientDetails",clientsDetailsService.listClientDetails());
        return new ModelAndView ("client/index",model);
    }

    @PostMapping("/revoke")
    public String revokeApproval(@RequestParam("clientId") final String clientId,
                                 @AuthenticationPrincipal final User user){

//        Permite el revoke de una aplicacion de un usuario.
        Collection<Approval> approvals = approvalStore.getApprovals(user.getUsername(), clientId);

        approvalStore.revokeApprovals(approvals);
        tokenStore.findTokensByClientIdAndUserName(clientId,user.getId().toString())
            .forEach(tokenStore::removeAccessToken) ;
        return "redirect:/client/";
    }


    @GetMapping("/form")
    @PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
    public String form(@RequestParam(value="client",required=false) final String clientId, Model model){

        ClientDetails clientDetails;
        if (clientId != null) {
            clientDetails = clientsDetailsService.loadClientByClientId(clientId);
        } else {
            clientDetails = new BaseClientDetails();
        }

        model.addAttribute("clientDetails",clientDetails);
        return "client/form";
    }


    @PostMapping("/edit")
    @PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
    public String edit(@ModelAttribute BaseClientDetails clientDetails,
                       @RequestParam(value = "newClient", required = false) String newClient) {

        if (newClient == null) {
            clientsDetailsService.updateClientDetails(clientDetails);
        } else {
            clientsDetailsService.addClientDetails(clientDetails);
        }

        if (!clientDetails.getClientSecret().isEmpty()) {
            clientsDetailsService.updateClientSecret(clientDetails.getClientId(), clientDetails.getClientSecret());
        }
        return "redirect:/client/";
    }

    @GetMapping("{client.clientId}/delete")
    @PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
    public String delete(@ModelAttribute BaseClientDetails ClientDetails,
                         @PathVariable("client.clientId") String id){
        clientsDetailsService.removeClientDetails(id);
        return "redirect:/client/";
    }

}
