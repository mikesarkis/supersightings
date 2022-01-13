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
import SuperheroSightingsSpringApp.dto.Sighting;
import SuperheroSightingsSpringApp.dto.SuperPower;
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
public class SuperpowerController {
    @Autowired
    HeroVillainDao herodao;
    @Autowired
    LocationDao locationdao;
    @Autowired
    OrganizationDao orgdao;
    
      @GetMapping("Superpowers")
    public String DisplaySuperpowers(Model model){
        List<SuperPower> SuperPowerlist = herodao.getallsuperpower();
        model.addAttribute("Superpowers", SuperPowerlist);
        return "Superpowers";
       }
      @PostMapping("addSuperpower")
          public String addSuperpower(HttpServletRequest request) {
          int id = Integer.parseInt(request.getParameter("SuperpowerID"));
          String description = request.getParameter("Description");
          herodao.add_superpower(id, description);
          return "redirect:/Superpowers";
      }
      @GetMapping("editSuperpower")
      public String editSuperpower(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("id"));
          SuperPower temp = herodao.getsuperpowerbyID(id);
          model.addAttribute("superpower", temp);
          return "Editsuperpower";
      }
       @PostMapping("editSuperpower")
          public String editSuperpower(HttpServletRequest request){
          int id = Integer.parseInt(request.getParameter("id"));
          String description = request.getParameter("Description");
          herodao.Editsuperpower(id, description);
          return "redirect:/Superpowers";
      }
        @GetMapping("deleteSuperpower")
        public String deleteSuperpower(HttpServletRequest request) {
         int id = Integer.parseInt(request.getParameter("id"));
        herodao.Deletesuperpower(id);
         return "redirect:/Superpowers";
       }    
       @GetMapping("DisplaySuperpower")
      public String DisplaySuperpower(HttpServletRequest request, Model model) {
            int id = Integer.parseInt(request.getParameter("SuperpowerID"));
           SuperPower temp = herodao.getsuperpowerbyID(id);
           model.addAttribute("Superpower", temp);
           return "DisplaySuperpower";
          
      }   
}
