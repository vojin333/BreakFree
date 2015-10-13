# BreakFree

Java command line text based game

Setting Up The Game For Development or Gameplay:


1.You can download the game with:

    git clone https://github.com/vojin333/BreakFree.git

2.Run with Maven

	Install the game with : 
		mvn clean install 
	
	It will automatically compile, build , and make executable jar.	
	
3.Run the game with:
	java -jar breakfree-0.0.1-SNAPSHOT.jar
	
Additionally you can import into IDE as maven project and check the source code	

  

Playing the Game
--------------

To start a new game:

    start

To save a game:

    save

Get a list of commands with:

    help


To view details about your player:

    stats

To quit the game:

    exit

To move:

    g n - go north
    g s - go south
    g e - go east
    g w - go west


To start a fight with zombie:
    
    fight
    
To attack in battle mode :    
	
	a
	
To heal yourself in battle mode :    
	
	h	

	
	
Code extensibility and future tasks

The story of the game can be easily switched into some other genres by:

1.Changing map overview in /game_data/game_world_data/loactions.xml
2.And type of the creatures by inheriting CreatureEntity

Possible enhancements:

1. Adding Game Items in Player class and location.xml it can be created additional game feature to equip your player with more powerful tools for fight and map exploration.
2. Game fog can be added because there is already a flag in  loactions.xml if specific loaction.xml has been seen.
3. 3D movement can be added by expanding Coordinate class with additional axis and Direction class.
