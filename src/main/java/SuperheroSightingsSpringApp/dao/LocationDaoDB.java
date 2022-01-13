/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;

import SuperheroSightingsSpringApp.dao.HeroVillainDaoDB.HeroVillainMapper;
import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Location;
import SuperheroSightingsSpringApp.dto.Sighting;
import SuperheroSightingsSpringApp.dto.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mike
 */
@Repository
public class LocationDaoDB implements LocationDao{
    
    @Autowired
    JdbcTemplate jdbc;
      
    @Override
    public void addLocation(Location location) {
        final String Insert_Hero = "INSERT INTO location(name,description,Address,Longitude,Latitude) "+ " VALUES(?,?,?,?,?)";
        jdbc.update(Insert_Hero, location.getName(),location.getDescription(),location.getAddress() ,location.getLongitude(), location.getLatitude());
        int newID= jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newID);
    }

    @Override
    public void addsighting(int locationID, int heroID, String date) {
        final String Insert_sighting = "INSERT INTO SightRecord(HeroVillainID,LocationID, Date) "+
                " VALUES(?,?,?)";
        jdbc.update(Insert_sighting, heroID, locationID, date);
    }

    @Override
    @Transactional
    public List<HeroVillain> get_all_hero_villain(int id) {
        final String select_all_hero_for_location = "SELECT h.* FROM herovillain h "
                +" JOIN SightRecord s ON h.ID = s.HeroVillainID"
                +" JOIN location l ON l.ID = s.LocationID WHERE l.ID = ?";
        List<HeroVillain> list11=  jdbc.query(select_all_hero_for_location,new HeroVillainMapper() ,id);
        associateHeroAndsuperpower(list11);
        return list11;
    }

    @Override
    public List<Sighting> get_all_info_for_date(String date) {
        final String select_all_hero_for_date = "SELECT * FROM SightRecord  WHERE Date = ?";             
        List<Sighting> sighting_list = jdbc.query(select_all_hero_for_date,new SightMapper() ,date);
        associateHeroAndLocation(sighting_list);
        for(Sighting sight : sighting_list)
        {
            associateSingalHeroAndsuperpower(sight.getHero());
        }
        return sighting_list;
    }
    private void associateHeroAndLocation(List<Sighting> sighting_list)
    {
        for (Sighting sight : sighting_list)
        {
            sight.setHero(get_hero(sight.getHeroID()));
            sight.setLocation(getlocationbyID(sight.getLocationID()));
        }
    }
    private void associateHeroAndsuperpower(List<HeroVillain> heros)
    {
        final String get_superpower = "SELECT * FROM SuperPower WHERE ID = ? ";
        for(HeroVillain hero : heros)
        {
            SuperPower temp = jdbc.queryForObject(get_superpower, new HeroVillainDaoDB.superpowerMapper(), hero.getSuperpowerid());
            hero.setSuperpower(temp.getDescription());
        }
    }
    private void associateSingalHeroAndsuperpower(HeroVillain hero)
    {
        final String get_superpower = "SELECT * FROM SuperPower WHERE ID = ? ";
        SuperPower temp = jdbc.queryForObject(get_superpower, new HeroVillainDaoDB.superpowerMapper(), hero.getSuperpowerid());
        hero.setSuperpower(temp.getDescription());
        
    }
    private HeroVillain get_hero(int id)
    {
       try{ final String select_hero = "SELECT * from herovillain WHERE ID= ?";
        return jdbc.queryForObject(select_hero ,new HeroVillainMapper() ,id);
       }catch(DataAccessException ex){
            return null;
        }
    }

    @Override
    public Location getlocationbyID(int id) {
        try{
            final String get_location = "SELECT * FROM location WHERE ID = ?";
            return jdbc.queryForObject(get_location, new LocationMapper(), id);
        }catch(DataAccessException ex){
            return null;
        }

    }

    @Override
    public void editLocationbyID(int id, Location temp) {
        final String update_location = "UPDATE location SET name=? , description = ? ,Address  = ?, Longitude = ?, Latitude =? "
                + " WHERE ID = ?";
        jdbc.update(update_location, temp.getName(), temp.getDescription(), temp.getAddress(), temp.getLongitude(), temp.getLatitude(), temp.getId());
    }


    @Override
    public void deleteLocationbyID(int id) {
        final String delete_Sight= "DELETE FROM SightRecord  WHERE LocationID =?";
        jdbc.update(delete_Sight, id);
        final String delete_location = "DELETE FROM location WHERE ID=?";
        jdbc.update(delete_location, id);
    }

    @Override
    public void editsighting(Sighting temp) {
        final String update_sight = "UPDATE SightRecord SET Date = ? "
                +" WHERE HeroVillainID= ? AND LocationID =? ";
        jdbc.update(update_sight, temp.getDate() ,temp.getHeroID(), temp.getLocationID());
    }

    @Override
    public void deletesighting(int heroID, int LocationID) {
        final String delete_sighting = "DELETE FROM SightRecord WHERE HeroVillainID = ? AND LocationID =?  ";
        jdbc.update(delete_sighting, heroID, LocationID);
    }

    @Override
    public Sighting getSighting(int heroID, int LocationID) {
        final String select_sighting = "SELECT * FROM SightRecord WHERE HeroVillainID = ? AND LocationID =?";
        Sighting temp = jdbc.queryForObject(select_sighting,new SightMapper() ,heroID, LocationID);
        HeroVillain herotemp = get_hero( heroID);
        associateSingalHeroAndsuperpower(herotemp);
        Location locationtemp= getlocationbyID(LocationID);
        temp.setHero(herotemp);
        temp.setLocation(locationtemp);
        return temp;
    }

    @Override
    public List<Location> get_all_locations() {
        final String select_locations = "SELECT * FROM location";
        return jdbc.query(select_locations, new LocationMapper());
    }

    @Override
    public List<Sighting> get_all_sighting() {
        final String select_all_sighting = "SELECT * FROM SightRecord";
        List<Sighting> sighting_list = jdbc.query(select_all_sighting, new SightMapper());
        associateHeroAndLocation(sighting_list);
        for(Sighting sight : sighting_list)
        {
            associateSingalHeroAndsuperpower(sight.getHero());
        }
        return sighting_list;
    }



    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location temp = new Location();
            temp.setId(rs.getInt("ID"));
            temp.setName(rs.getString("name"));
            temp.setDescription(rs.getString("description"));
            temp.setAddress(rs.getString("Address"));
            temp.setLongitude(rs.getDouble("Longitude"));
            temp.setLatitude(rs.getDouble("Latitude"));
                      
            return temp;
        }
    }
    public static final class SightMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sighting temp=  new Sighting();
            temp.setHeroID(rs.getInt("HeroVillainID"));
            temp.setLocationID(rs.getInt("LocationID"));
            temp.setDate(rs.getString("Date"));
            
            return temp;
        }
    }
    
}
