/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;


import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Organization;
import java.util.List;

/**
 *
 * @author Mike
 */
public interface OrganizationDao {
    public void add_Organization(Organization temp);
    public List<HeroVillain> get_all_members(int id);
    public Organization getOrganizationbyID (int id);
    public void editOrganization (int id, Organization temp);
    public void deleteOrganization (int id);
    public List<Organization> get_all_organization();
    
}