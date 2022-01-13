/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;


import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Location;
import SuperheroSightingsSpringApp.dto.Sighting;
import java.util.List;

/**
 *
 * @author Mike
 */
public interface LocationDao {
    public void addLocation(Location location);
    public void addsighting(int locationID , int HeroVillainID, String date);
    public List<HeroVillain> get_all_hero_villain(int id);
    public List<Sighting> get_all_info_for_date(String date);
    public Location getlocationbyID(int id);
    public Sighting getSighting(int heroID, int LocationID);
    public void editLocationbyID(int id, Location temp);
    public void deleteLocationbyID(int id);   
    public void editsighting(Sighting temp);
    public void deletesighting(int heroID, int LocationID);
    public List<Location> get_all_locations();
    public List<Sighting> get_all_sighting();
    
}
