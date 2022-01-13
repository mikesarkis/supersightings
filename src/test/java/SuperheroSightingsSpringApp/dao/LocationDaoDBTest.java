/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dao;

import SuperheroSightingsSpringApp.dto.HeroVillain;
import SuperheroSightingsSpringApp.dto.Location;
import SuperheroSightingsSpringApp.dto.Sighting;
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
public class LocationDaoDBTest {
     HeroVillainDao daohero;
     LocationDao daolocation;
     OrganizationDao daoOrganization;
    
    public LocationDaoDBTest() {
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
    public void testaddLocation() {
        Location location1 = new Location();
        location1.setName("location1");
        location1.setAddress("4637 Chem. du Souvenir, Laval, QC H7W 1C5");
        location1.setDescription("a very beautiful place");
        location1.setLatitude(45.54074401532407);
        location1.setLongitude(-73.76713915228807);
        daolocation.addLocation(location1);
        Location locationfromdao = daolocation.getlocationbyID(1);
        location1.setId(locationfromdao.getId());
        assertEquals(location1,locationfromdao);
    }
    @Test
    public void testgethero() {
        HeroVillain hero1 = new HeroVillain();
        hero1.setName("Batman");
        hero1.setSuperpower("Fight like a bat");
        hero1.setIsHero(true);
        hero1.setDescription("Work with DC");
        daohero.addHero_villian(hero1);
        HeroVillain herofromDao =daohero.getherobyID(1);
        hero1.setId(herofromDao.getId());
        Location location1 = new Location();
        location1.setName("location1");
        location1.setAddress("4637 Chem. du Souvenir, Laval, QC H7W 1C5");
        location1.setDescription("a very beautiful place");
        location1.setLatitude(45.54074401532407);
        location1.setLongitude(-73.76713915228807);
        daolocation.addLocation(location1);
        Location locationfromdao = daolocation.getlocationbyID(1);
        location1.setId(locationfromdao.getId());
        String date = "10/12/2021-09:30AM";
        daolocation.addsighting(location1.getId(), hero1.getId(), date);
        List<HeroVillain> list1 = new ArrayList<>();
        list1.add(hero1);
        List<HeroVillain> list2 = daolocation.get_all_hero_villain(location1.getId());
        assertEquals(1, list2.size());
        assertTrue(list2.contains(location1));
    }
    @Test
    public void testgetallfordate() {
        HeroVillain hero1 = new HeroVillain();
        hero1.setName("Batman");
        hero1.setSuperpower("Fight like a bat");
        hero1.setIsHero(true);
        hero1.setDescription("Work with DC");
        daohero.addHero_villian(hero1);
        HeroVillain herofromDao = daohero.getherobyID(1);
        hero1.setId(herofromDao.getId());
        Location location1 = new Location();
        location1.setName("location1");
        location1.setAddress("4637 Chem. du Souvenir, Laval, QC H7W 1C5");
        location1.setDescription("a very beautiful place");
        location1.setLatitude(45.54074401532407);
        location1.setLongitude(-73.76713915228807);
        daolocation.addLocation(location1);
        Location locationfromdao = daolocation.getlocationbyID(1);
        location1.setId(locationfromdao.getId());
        String date = "10/12/2021-09:30AM";
        Sighting sight1= new Sighting();
        sight1.setDate(date);
        sight1.setHeroID(hero1.getId());
        sight1.setLocationID(location1.getId());
        sight1.setHero(hero1);
        sight1.setLocation(location1);       
        List<Sighting> list1 = new ArrayList<>();
        list1.add(sight1);
        daolocation.addsighting(location1.getId(), hero1.getId(), date);
        List<Sighting> list2 = daolocation.get_all_info_for_date(date);
        assertEquals(1, list2.size());
        assertTrue(list2.contains(sight1));
    }
    
}
