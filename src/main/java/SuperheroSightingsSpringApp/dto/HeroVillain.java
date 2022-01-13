/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SuperheroSightingsSpringApp.dto;

/**
 *
 * @author Mike
 */
import java.util.Objects;

/**
 *
 * @author Mike
 */
public class HeroVillain {
    private int id;
    private String name;
    private int Superpowerid;
    private String description;
    private String Superpower;
  //  private SuperPower mypower;
    public HeroVillain()
    {
        Superpowerid= 0;
    }
    public String getSuperpower() {
        return Superpower;
    }

    public void setSuperpower(String Superpower) {
        this.Superpower = Superpower;
    }
    private boolean isHero;
 
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.id;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.Superpowerid);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + (this.isHero ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HeroVillain other = (HeroVillain) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.isHero != other.isHero) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.Superpowerid, other.Superpowerid)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSuperpowerid() {
        return Superpowerid;
    }

    public void setSuperpowerid(int Superpower) {
        this.Superpowerid = Superpower;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isIsHero() {
        return isHero;
    }

    public void setIsHero(boolean isHero) {
        this.isHero = isHero;
    }
    
}
