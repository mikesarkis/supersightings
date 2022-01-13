/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;


import SuperheroSightingsSpringApp.dao.HeroVillainDaoDB.HeroVillainMapper;
import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Organization;
import SuperheroSightingsSpringApp.dto.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mike
 */
@Repository
public class OrganizationDaoDB implements OrganizationDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void add_Organization(Organization temp) {
        final String Insert_Organization = "INSERT INTO organization(name,description,Address,ContactInfo) "
                +"VALUES(?,?,?,?)";
        jdbc.update(Insert_Organization, temp.getName(), temp.getDescription() ,temp.getAddress(), temp.getContactInfo());
        int newID = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        temp.setId(newID);
    }

    @Override
    @Transactional
    public List<HeroVillain> get_all_members(int id) {
        final String Select_all_members = "SELECT t.* FROM herovillain t "
                +" Join HeroVillainOrganization c ON t.ID = c.HeroVillainID "
                +" JOIN organization d ON d.ID = c.OrganizationID WHERE d.ID =? ";
        List<HeroVillain> list11 = jdbc.query(Select_all_members, new HeroVillainMapper(),id);
        associateHeroAndsuperpower(list11);
        return list11;
    }

    @Override
    public Organization getOrganizationbyID(int id) {
        final String select_organization= "SELECT * FROM organization WHERE ID= ?";
        
        return jdbc.queryForObject(select_organization, new OrganizationMapper(),id);
    }

    @Override
    public void editOrganization(int id , Organization temp) {
        final String update_Organization = "UPDATE organization SET name=? , description = ? ,Address  = ?, ContactInfo = ? "
                + " WHERE ID = ?";
        jdbc.update(update_Organization, temp.getName(), temp.getDescription(), temp.getAddress(), temp.getContactInfo(), temp.getId());
    }

    @Override
    public void deleteOrganization(int id) {
        final String delete_hero_organization = "DELETE FROM herovillainorganization WHERE OrganizationID = ?";
        jdbc.update(delete_hero_organization, id);
        final String delete_organization = "DELETE FROM organization WHERE ID=?";
        jdbc.update(delete_organization, id);
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

    @Override
    public List<Organization> get_all_organization() {
        final String  select_all_orgs = "SELECT * FROM organization";
        return jdbc.query(select_all_orgs, new OrganizationMapper());
    }
    
    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization temp = new Organization();
            temp.setId(rs.getInt("ID"));
            temp.setAddress(rs.getString("Address"));
            temp.setName(rs.getString("name"));
            temp.setDescription(rs.getString("description"));
            temp.setContactInfo(rs.getString("ContactInfo"));
            
            return temp;
        }
    }
    
}