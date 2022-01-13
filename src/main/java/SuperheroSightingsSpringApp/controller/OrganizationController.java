/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.controller;

import SuperheroSightingsSpringApp.dao.HeroVillainDao;
import SuperheroSightingsSpringApp.dao.LocationDao;
import SuperheroSightingsSpringApp.dao.OrganizationDao;
import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Organization;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Mike
 */
@Controller
public class OrganizationController {
    @Autowired
    HeroVillainDao herodao;
    @Autowired
    LocationDao locationdao;
    @Autowired
    OrganizationDao orgdao;
    
    
    @GetMapping("Organizations")
         public String DisplayHeros(Model model){
         List<Organization> orglist = orgdao.get_all_organization();
         model.addAttribute("Organizations", orglist);
         return "Organizations";
    }
    
     @PostMapping("addOrganization")
          public String addOrganization(HttpServletRequest request) {
          String Name= request.getParameter("Name");
          String adress = request.getParameter("Address");
          String description = request.getParameter("Description");
          String contactinfo = request.getParameter("Contact");
          Organization temp = new Organization();
          temp.setName(Name);
          temp.setDescription(description);
          temp.setAddress(adress);
          temp.setContactInfo(contactinfo);
          orgdao.add_Organization(temp);
          return "redirect:/Organizations";
      }
     @GetMapping("editOrganization")
      public String EditOrganization(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("id"));
          Organization temp = orgdao.getOrganizationbyID(id);
          model.addAttribute("org", temp);
          return "Editorganization";
      }
      @PostMapping("editOrganization")
          public String EditOrganization(HttpServletRequest request){
          int id = Integer.parseInt(request.getParameter("id"));
          Organization temp = orgdao.getOrganizationbyID(id);
          temp.setName(request.getParameter("Name"));
          temp.setDescription(request.getParameter("Description"));
          temp.setAddress(request.getParameter("Address"));
          temp.setContactInfo(request.getParameter("Contact"));
          orgdao.editOrganization(id, temp);
           return "redirect:/Organizations";
      }
      
       
     @GetMapping("deleteOrganization")
        public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        orgdao.deleteOrganization(id);
         return "redirect:/Organizations";
        }    
        
       @GetMapping("OrganizationMembers")
         public String OrganizationMembers(HttpServletRequest request, Model model){
         int id = Integer.parseInt(request.getParameter("id"));
         List<HeroVillain> list1 = orgdao.get_all_members(id);
         model.addAttribute("OrganizationMembers", list1);
         return "DisplayOrganizationMembers";
     }
   
      @GetMapping("DisplayOrganization")
           public String DisplayOrganization(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("ID"));
          Organization  temp = orgdao.getOrganizationbyID(id);
          model.addAttribute("Organization", temp);
          return "DisplayOrganization";
          
      }
}
