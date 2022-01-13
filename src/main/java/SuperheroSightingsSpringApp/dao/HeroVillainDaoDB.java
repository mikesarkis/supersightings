/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;


import SuperheroSightingsSpringApp.dao.LocationDaoDB.LocationMapper;
import SuperheroSightingsSpringApp.dao.OrganizationDaoDB.OrganizationMapper;
import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Location;
import SuperheroSightingsSpringApp.dto.Organization;
import SuperheroSightingsSpringApp.dto.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mike
 */
@Repository
public class HeroVillainDaoDB implements HeroVillainDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void addHero_villian(HeroVillain temp) {
        final String Insert_Hero = "INSERT INTO HeroVillain(Name,Description,Superpowerid,isHero) "+ " VALUES(?,?,?,?)";
        jdbc.update(Insert_Hero, temp.getName(),temp.getDescription(),temp.getSuperpowerid(),temp.isIsHero()?1:0);  
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        temp.setId(newID);     
        assocaitesuperheroAndSuperpower(temp, temp.getSuperpowerid());
        
    }
    @Override
    public void add_hero_to_organization(int id, int organizationid) {
        final String Insert_relation = "INSERT INTO herovillainorganization(HeroVillainID,OrganizationID) "+ " VALUES(?,?)";
        jdbc.update(Insert_relation, id, organizationid);
    }
    @Override
    public void edithero(int id,HeroVillain temp) {
        final String update_hero = "UPDATE herovillain SET name=? , description = ? ,Superpowerid  = ?, isHero = ? "
                + " WHERE ID = ?";
        jdbc.update(update_hero, temp.getName(), temp.getDescription(), temp.getSuperpowerid(), temp.isIsHero(), id);
    }

    @Override
    @Transactional
    public List<Location> get_all_Loaction(int id) {
        final String Select_Locations_for_hero = "Select l.* From location l"
                + " Join sightrecord s ON l.ID = s.LocationID"
                + " Join herovillain h ON s.HeroVillainID = h.ID WHERE h.ID= ? ";
        return jdbc.query(Select_Locations_for_hero,new LocationMapper()  ,id);
    }

    @Override
    @Transactional
    public List<Organization> get_all_Organization(int id) {
        final String Select_Organization_for_hero = "Select o.* From organization o"
                +" Join herovillainorganization r ON o.ID = r.OrganizationID "
                +" Join herovillain h ON r.HeroVillainID = h.ID WHERE h.ID = ?";
        return jdbc.query(Select_Organization_for_hero, new OrganizationMapper() ,id);
    }

    @Override
    public HeroVillain getherobyID(int id) {
        try{
        final String select_hero_by_id = "Select * FROM herovillain WHERE ID=?";
        HeroVillain temp = jdbc.queryForObject(select_hero_by_id, new HeroVillainMapper(), id);
        assocaitesuperheroAndSuperpower(temp, temp.getSuperpowerid());
        return temp;
        }catch(DataAccessException ex){
            return null;
        }
    }



    @Override
    @Transactional
    public void DeleteherobyID(int id) {
        final String delet_relation = "DELETE  FROM herovillainorganization WHERE HeroVillainID = ?";
        jdbc.update(delet_relation, id);
        final String delete_sight= "DELETE  FROM sightrecord WHERE HeroVillainID = ?";
        jdbc.update(delete_sight, id);
        final String delete_hero = "DELETE  FROM herovillain WHERE ID = ?";
        jdbc.update(delete_hero, id);
    }

    @Override
    public void remove_hero_from_organization(int id, int organizationid) {
        final String delete_relation = "DELETE FROM herovillainorganization WHERE HeroVillainID = ? AND OrganizationID = ? ";
        jdbc.update(delete_relation, id, organizationid);
    }

    @Override
    public List<SuperPower> getallsuperpower() {
        final String get_all_superpower = "SELECT * from SuperPower";
        return jdbc.query(get_all_superpower, new superpowerMapper());
    }

    @Override
    public void Editsuperpower(int id, String temp) {
        final String edit_superpower = "UPDATE SuperPower SET description = ? WHERE ID = ?";
        jdbc.update(edit_superpower, temp, id);
    }
    private void assocaitesuperheroAndSuperpower(HeroVillain hero, int Superpowerid)
    {
        SuperPower temp = getsuperpowerbyID(Superpowerid);
        hero.setSuperpower(temp.getDescription());
    }

    @Override
    public void Deletesuperpower(int id) {
        final String delete_superpower =  "DELETE FROM SuperPower WHERE ID= ?";
        jdbc.update(delete_superpower, id);
    }

    @Override
    public SuperPower getsuperpowerbyID(int id) {
        final String select_superpower = "SELECT * FROM SuperPower WHERE ID = ? ";
        return jdbc.queryForObject(select_superpower, new superpowerMapper(), id);
    }

    @Override
    public void add_superpower(int id, String temp) {
        final String insert_superpower= "INSERT INTO SuperPower(ID,description)"
                +" VALUES(?,?)";
        jdbc.update(insert_superpower, id, temp);
    }

    @Override
    public List<HeroVillain> get_all_heros() {
        final String select_all_hero = "SELECT * FROM herovillain";
        List<HeroVillain> herolist = jdbc.query(select_all_hero, new HeroVillainMapper());
        for(HeroVillain hero: herolist)
        {
            assocaitesuperheroAndSuperpower(hero, hero.getSuperpowerid());
        }
        return herolist;
    }
    
    public static final class HeroVillainMapper implements RowMapper<HeroVillain> {
        
        @Override
        public HeroVillain mapRow(ResultSet rs, int index) throws SQLException{
            HeroVillain temp = new HeroVillain();
            temp.setId(rs.getInt("ID"));
            temp.setName(rs.getString("name"));
            temp.setDescription(rs.getString("description"));
            temp.setSuperpowerid(rs.getInt("Superpowerid"));
            temp.setIsHero(rs.getBoolean("isHero"));
            
            return temp;
        }
    }
    public static final class superpowerMapper implements RowMapper<SuperPower> {       
        
        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException{
            SuperPower temp = new SuperPower();
            temp.setId(rs.getInt("ID"));
            temp.setDescription(rs.getString("description"));
            
            return temp;
        }
        
    }

    
}
