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
import java.util.ArrayList;
import java.util.Collections;
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
public class LocationController {
    @Autowired
    HeroVillainDao herodao;
    @Autowired
    LocationDao locationdao;
    @Autowired
    OrganizationDao orgdao;
    
    @GetMapping("lastSightlist")
    public String displaylastSightings(Model model) {
        List<Sighting> sightinglist = locationdao.get_all_sighting();
        if(sightinglist.size()<=10)
        {
            Collections.sort(sightinglist, Collections.reverseOrder());
           model.addAttribute("lastSightlist", sightinglist);
           return "lastSightlist";
        }
        else
        {
            List<Sighting> sightinglist2 = new ArrayList<>();
            int max = sightinglist.size()-1;
            int min = sightinglist.size()-11;
            for(int i = max ; i>=min ; i-- )
            {
                sightinglist2.add(sightinglist.get(i));
            }
            model.addAttribute("lastSightlist", sightinglist2);
            return "Home";
        }
        
    }
    @GetMapping("Locations")
    public String displayLocations(Model model) {  
        List<Location> all_Locations = locationdao.get_all_locations();
        model.addAttribute("Locations", all_Locations);
        return "Locations";
    }  
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String Name= request.getParameter("Name");
        String description = request.getParameter("Description");
        String adress = request.getParameter("Address");
        double Longitude = Double.parseDouble(request.getParameter("Longitude"));
        double Latitude = Double.parseDouble(request.getParameter("Latitude"));
        Location temp = new Location();
        temp.setName(Name);
        temp.setDescription(description);
        temp.setAddress(adress);
        temp.setLongitude(Longitude);
        temp.setLatitude(Latitude);
        locationdao.addLocation(temp);
         return "redirect:/Locations";
    }
   @GetMapping("deleteLocation")
   public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationdao.deleteLocationbyID(id);
         return "redirect:/Locations";
   }    
   
    @GetMapping("DisplayLocation")
      public String DisplayLocation(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("ID"));
           Location temp = locationdao.getlocationbyID(id);
           model.addAttribute("Location", temp);
           return "DisplayLocation";
          
      }
      
     @GetMapping("editLocation")
      public String editLocation(HttpServletRequest request, Model model) {
          int id = Integer.parseInt(request.getParameter("id"));
          Location temp = locationdao.getlocationbyID(id);
          model.addAttribute("location", temp);
          return "EditLocation";
    }
   @PostMapping("editLocation")
   public String EditLocation(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        Location temp = locationdao.getlocationbyID(id);
        temp.setName(request.getParameter("Name"));
        temp.setDescription(request.getParameter("Description"));
        temp.setAddress(request.getParameter("Address"));
        temp.setLongitude(Double.parseDouble(request.getParameter("Longitude")));
        temp.setLatitude(Double.parseDouble(request.getParameter("Latitude")));
        locationdao.editLocationbyID(id, temp);
        return "redirect:/Locations";
   }
   @GetMapping("LocationHeros")
    public String displayLocationHeros(HttpServletRequest request, Model model) {
         int id = Integer.parseInt(request.getParameter("id"));
          List<HeroVillain> herolist = locationdao.get_all_hero_villain(id);
          model.addAttribute("LocationHero", herolist);
          return  "LocationHero";
    }
}
