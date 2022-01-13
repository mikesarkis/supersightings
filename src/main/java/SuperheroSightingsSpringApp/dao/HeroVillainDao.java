/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;

import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Location;
import SuperheroSightingsSpringApp.dto.Organization;
import SuperheroSightingsSpringApp.dto.SuperPower;
import java.util.List;

/**
 *
 * @author Mike
 */
public interface HeroVillainDao {
    public void addHero_villian(HeroVillain temp);
    public void add_hero_to_organization(int id, int organizationid);
    public List<Location> get_all_Loaction(int id);
    public List<Organization> get_all_Organization(int id);
    public HeroVillain getherobyID(int id);
    public void edithero(int id, HeroVillain temp);
    public void DeleteherobyID(int id);
    public void remove_hero_from_organization(int id, int organizationid);
    public List<SuperPower> getallsuperpower();
    public void add_superpower(int id, String temp);
    public void Editsuperpower(int id, String temp);
    public void Deletesuperpower (int id);
    public SuperPower getsuperpowerbyID(int id);
    public List<HeroVillain> get_all_heros();
}
