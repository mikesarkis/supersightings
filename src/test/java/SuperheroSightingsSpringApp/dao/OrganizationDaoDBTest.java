/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;

import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Organization;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mike
 */
public class OrganizationDaoDBTest {
    HeroVillainDao daohero;
    LocationDao daolocation;
    OrganizationDao daoOrganization;
    
    public OrganizationDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testaddorganization() {
        Organization org1 = new Organization();
        org1.setName("Marvel");
        org1.setAddress("135 West 50th Street Seventh Floor New York, NY 10020 USA");
        org1.setContactInfo("(212) 576-4000");
        org1.setDescription("large organization");
        daoOrganization.add_Organization(org1);
        Organization orgfromdao = daoOrganization.getOrganizationbyID(1);
        org1.setId(orgfromdao.getId());
        assertEquals(org1,orgfromdao);
    }
    @Test
    public void testgetmembers() {
        Organization org1 = new Organization();
        org1.setName("Marvel");
        org1.setAddress("135 West 50th Street Seventh Floor New York, NY 10020 USA");
        org1.setContactInfo("(212) 576-4000");
        org1.setDescription("large organization");
        daoOrganization.add_Organization(org1);
        Organization orgfromdao = daoOrganization.getOrganizationbyID(1);
        org1.setId(orgfromdao.getId());
        HeroVillain hero1 = new HeroVillain();
        hero1.setName("Batman");
        hero1.setSuperpower("Fight like a bat");
        hero1.setIsHero(true);
        hero1.setDescription("Work with DC");
        daohero.addHero_villian(hero1);
        HeroVillain herofromDao = daohero.getherobyID(1);
        hero1.setId(herofromDao.getId());
        daohero.add_hero_to_organization(hero1.getId(), org1.getId());
        List<HeroVillain> list1 =  new ArrayList<>();
        list1.add(hero1);
        List<HeroVillain> list2 = daoOrganization.get_all_members(org1.getId());
        assertEquals(1, list2.size());
        assertTrue(list2.contains(hero1));
    }
    
}
