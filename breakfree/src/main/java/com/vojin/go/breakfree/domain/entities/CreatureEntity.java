package com.vojin.go.breakfree.domain.entities;



/**
 * 
 * @author Vojin Nikolic
 * 
 * Main Entity the is a super class of all game entities
 *
 */
public abstract class CreatureEntity {

	protected String name;
	protected String description;
    protected int health;
    protected int minDamage;
    protected int maxDamage;

    /**
     * non parameterized Constructor 
     */
    public CreatureEntity() {
        this.name = null;
        this.description = null;
        this.minDamage = 6;
        this.maxDamage = 20;
        this.health = 40;
    }
    
    /**
     * Parameterized Constructor
     * @param name
     * @param description
     * @param health
     * @param minDamage
     * @param maxDamage
     */
    public CreatureEntity(String name, String description, int health, int minDamage, int maxDamage) {
        this.name = name;
        this.description = description;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.health = health;
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * @return the minDamage
	 */
	public int getMinDamage() {
		return minDamage;
	}

	/**
	 * @param minDamage the minDamage to set
	 */
	public void setMinDamage(int minDamage) {
		this.minDamage = minDamage;
	}

	/**
	 * @return the maxDamage
	 */
	public int getMaxDamage() {
		return maxDamage;
	}

	/**
	 * @param maxDamage the maxDamage to set
	 */
	public void setMaxDamage(int maxDamage) {
		this.maxDamage = maxDamage;
	}
    
	/**
	 * 
	 * @return health
	 */
	public boolean isAlive() {
        return health > 0;
    }

	/**
	 * 
	 * @return player health
	 */
    public String getStatus() {
        return "Player HP: " + health;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreatureEntity [name=" + name + ", description=" + description + ", hitPoints=" + health + ", minDamage=" + minDamage + ", maxDamage=" + maxDamage + "]";
	}
    
}
