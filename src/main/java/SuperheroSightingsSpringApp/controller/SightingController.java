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
public class SightingController {
    @Autowired
    HeroVillainDao herodao;
    @Autowired
    LocationDao locationdao;
    @Autowired
    OrganizationDao orgdao;
    
    
      @GetMapping("Sightings")
         public String DisplayHeros(Model model){
         List<Sighting> sightingslist = locationdao.get_all_sighting();
         model.addAttribute("Sightings", sightingslist);
         return "Sightings";
        }
         
        @PostMapping("addSighting")
          public String addSighting(HttpServletRequest request) {
          int HeroId= Integer.parseInt(request.getParameter("HeroID"));
          int LocationID = Integer.parseInt(request.getParameter("LocationID"));
          String date = request.getParameter("date");
          locationdao.addsighting(LocationID, HeroId, date);
          return "redirect:/Sightings";
      }
       @GetMapping("editSighting")
      public String editHero(HttpServletRequest request, Model model) {
          int heroid = Integer.parseInt(request.getParameter("HeroID"));
          int LocationID = Integer.parseInt(request.getParameter("LocationID"));
          Sighting temp = locationdao.getSighting(heroid, LocationID);
          model.addAttribute("sight", temp);
          return "Editsight";
      }
       @PostMapping("editSighting")
          public String editSightingr(HttpServletRequest request){
          int heroid= Integer.parseInt(request.getParameter("HeroID"));
          int LocationID = Integer.parseInt(request.getParameter("LocationID"));
          String date = request.getParameter("date");
          Sighting temp = new Sighting();
          temp.setHeroID(heroid);
          temp.setLocationID(LocationID);
          temp.setDate(date);
          locationdao.editsighting(temp);
           return "redirect:/Sightings";
      }
          
       @GetMapping("deleteSighting")
        public String deleteSighting(HttpServletRequest request) {
        int heroid = Integer.parseInt(request.getParameter("HeroID"));
        int LocationID = Integer.parseInt(request.getParameter("LocationID"));
        locationdao.deletesighting(heroid, LocationID);
         return "redirect:/Sightings";
       }    
       @GetMapping("DisplaySighting")
      public String DisplayLocation(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("ID"));
           Location temp = locationdao.getlocationbyID(id);
           model.addAttribute("Sighting", temp);
           return "DisplaySighting";
          
      }
     @GetMapping("SightingInfo")
    public String displayLocationHeros(HttpServletRequest request, Model model) {
         String  date = request.getParameter("date");
          List<Sighting> SightList = locationdao.get_all_info_for_date(date);
          model.addAttribute("SightingInfo", SightList);
          return  "SightingInfo";
    }
    
    
}
