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
import SuperheroSightingsSpringApp.dto.Location;
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
public class SuperHeroVillainController {
    
    @Autowired
    HeroVillainDao herodao;
    @Autowired
    LocationDao locationdao;
    @Autowired
    OrganizationDao orgdao;
    
    
    @GetMapping("Heros")
    public String DisplayHeros(Model model){
        List<HeroVillain> herolist = herodao.get_all_heros();
        model.addAttribute("Heros", herolist);
        return "Heros";
    }
     @PostMapping("addHero")
      public String addHero(HttpServletRequest request) {
          String Name= request.getParameter("Name");
          int superpowerid = Integer.parseInt(request.getParameter("Superpowerid"));
          String description = request.getParameter("Description");
          boolean isHero = Boolean.parseBoolean(request.getParameter("isHero").toLowerCase());
          System.out.println(isHero);
          System.out.println(request.getParameter("isHero"));
          HeroVillain temp = new HeroVillain();
          temp.setName(Name);
          temp.setSuperpowerid(superpowerid);
          temp.setDescription(description);
          temp.setIsHero(isHero);
          herodao.addHero_villian(temp);          
          return "redirect:/Heros";
      }
      @GetMapping("deleteHero")
      public String deleteHero(HttpServletRequest request) {
           int id = Integer.parseInt(request.getParameter("id"));
           System.out.println(id);
           herodao.DeleteherobyID(id);
           return "redirect:/Heros";
      }
      @GetMapping("editHero")
      public String editHero(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("id"));
          HeroVillain temp = herodao.getherobyID(id);
          model.addAttribute("hero", temp);
          return "Edithero";
      }
      @PostMapping("editHero")
      public String EditHero(HttpServletRequest request){
          int id = Integer.parseInt(request.getParameter("id"));
          HeroVillain temp = herodao.getherobyID(id);
          temp.setName(request.getParameter("Name"));
          temp.setDescription(request.getParameter("Description"));
          temp.setSuperpowerid(Integer.parseInt(request.getParameter("Superpowerid")));
          temp.setIsHero(Boolean.parseBoolean(request.getParameter("isHero")));
          herodao.edithero(id, temp);
           return "redirect:/Heros";
      }
      @GetMapping("DisplayHero")
      public String DisplayHero(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("id"));
           HeroVillain temp = herodao.getherobyID(id);
           model.addAttribute("Hero", temp);
           return "DisplayHero";
          
      }

      @GetMapping("HeroOrganizations")
      public String HeroOrganizations (HttpServletRequest request, Model model){
         int id = Integer.parseInt(request.getParameter("id"));
         List<Organization> list1 = herodao.get_all_Organization(id);
         model.addAttribute("HeroOrganizations", list1);
         return "DisplayHeroOrganizations";
     }
      @PostMapping("addHeroOrganization")
      public String addHeroOrganization (HttpServletRequest request) {
           int id = Integer.parseInt(request.getParameter("id"));
           int orgId =  Integer.parseInt(request.getParameter("OrganizationID"));
           herodao.add_hero_to_organization(id, orgId);
          return "redirect:/Heros";
      }
      @PostMapping("deleteHeroOrganization")
       public String deleteHeroOrganization(HttpServletRequest request) {
           int id = Integer.parseInt(request.getParameter("id"));
           int orgId =  Integer.parseInt(request.getParameter("OrganizationID"));
           herodao.remove_hero_from_organization(id, orgId);
            return "redirect:/Heros";
       }
       @GetMapping("HeroLocations")
       public String HeroLocations (HttpServletRequest request, Model model){
           int id = Integer.parseInt(request.getParameter("id"));
           List<Location> list1 = herodao.get_all_Loaction(id);
           model.addAttribute("HeroLocations", list1);
           return "DisplayHeroLocations";
       }
      
 
}
