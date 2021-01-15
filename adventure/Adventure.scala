package o1.adventure
import scala.collection.mutable.Buffer
import scala.util.Random

/** Eli tämä on Osomon Adventure, Pokemon kopio, johon käytin aivan liikaa aikaa. Toivottavasti se täyttää vaatimukset.
 *  Q&A "Miksi monissa luokissa suurin osa metodeista on julkisia" V: Koska usein tarvitsin laajan kattauksen metodeita muista luokista, esim Osomon luokan metodeja tarvitsin hyvinkin laajalti.
 *  Privateksi muuttaminen olisi vain vaikeuttanut elämääni. Lisäksi kukaan muu ei tule tämän koodin kanssa työskentelemään.
 *  Q&A "Miten kusetan pelin niin, että se on helposti läpäistävissä" V: Käytä komentoa cheat overworldissa.
 *   */
class Adventure {

  /** The title of the adventure game. */
  val title = "A Osomon Adventure"

  //kaikki alueet
  private val home        = new Area("Home", "Home feels always warm and welcoming.",0)
  private val route1      = new Area("Route 1", "A nice meadow crossed by an elegant road.\nThere are flowers everywhere",1)
  private val twinleafTown = new Area("Twinleaf Town", "Town filled with happy people. East of here is the Mysterious mansion,\n and to the west is the familiar route 1.",0)
  private val badlands = new Area("Badlands", "A rocky, mountanious area. It really is difficult to move here.",2)
  private val mysteriousMansion    = new Area("Mysterious Mansion", "You are filled with an ominous feeling.\nThe big old mansion is barely standing there.",4)
  private val shimonoTown      = new Area("Shimono Town", "Shimono Town is the capital of technology!\n East of here lies Route 3! ",0)
  private val route3      = new Area("Route 3", "If you go north, this path in the woods leads you to Mount Coronet!\n East of here is Route 2. ",5)
  private val route2      = new Area("Route 2", "Wonderful route with beautiful ocean view. \n And of course, beaches.",3)
  private val oldaleTown      = new Area("Oldale Town", "The town with a deep history and calm atmosphere.\n If you go to North you get to the legendary Spear Pillar. ",0)
  private val spearPillar      = new Area("Spear Pillar", "This is the Spear Pillar, a place where the world was created.\n or so they say.",1)
  private val route4      = new Area("Route 4", "A huge swamp that doesn't really lead anywhere, except to mt. Coronet,\n If you come from route 2. But you'd be better off somewhere else.\n West of here is Mt. Coronet. ",6)
  private val mtCoronet      = new Area("Mt. Coronet", "You find yourself in a huge cavesystem. You can almost smell the Osomon League!\n Can you find your way out?",8)
  private val osomonLeague      = new Area("Osomon League", "You are finally in the Osomon League! Good luck!\n Will you become the champion?",0)
  
  private val destination = home

   
       home.setNeighbors(Vector(          "north"->route1                                                                          ))
        route1.setNeighbors(Vector(                               "east"->twinleafTown,     "south"->home,           "west"->badlands))
        badlands.setNeighbors(Vector(    "north"->shimonoTown,   "east"->route1                                                    ))
     twinleafTown.setNeighbors(Vector(  "north"->route2,         "east"->mysteriousMansion,                            "west"->route1))
  mysteriousMansion.setNeighbors(Vector(                                                                               "west"->twinleafTown))
       route2.setNeighbors(Vector(      "north"->route4,          "east"->oldaleTown,        "south"->twinleafTown,    "west"->route3))
 shimonoTown.setNeighbors(Vector(                                "east"->route3,              "south"->badlands                       ))
     route3.setNeighbors(Vector(          "north"->mtCoronet,    "east"->route2,                                       "west"->shimonoTown))
    oldaleTown.setNeighbors(Vector(      "north"->spearPillar,                                                          "west"->route2))
    osomonLeague.setNeighbors(Vector(                            "east"->mtCoronet                                                 ))
    mtCoronet.setNeighbors(Vector(                               "east"->route4,              "south"->route3,         "west"->osomonLeague))
    route4.setNeighbors(Vector(                                                              "south"->route2,          "west"->mtCoronet))  
    spearPillar.setNeighbors(Vector(                                                         "south"->oldaleTown                   ))
         
   val player = new Player(home)
  //Trainerit
  val kid = new Opponent("Kid",Buffer(new Paskamon(3),new Helppomon(3)))
  val grill = new Opponent("Beautiful Grill",Buffer(new Speedymon(5),new Helppomon(5),new Sikamon(4)))
  val boi = new Opponent("Handsome Boi",Buffer(new Paskamon(20),new Helppomon(5),new Speedymon(6)))
  val projared = new Opponent("Pro-Jared",Buffer(new Tehomon(7),new Sharpmon(9),new Toughmon(5)))
  val champion =new Opponent("Champion",Buffer(new Paskamon(30),new Lightningmon(12),new Sharpmon(12),new Rippermon(8),new Tehomon(12)))
  //tavarat
 val apple = new Item("apple", "It's a red apple. Why did you pick it up?",player)
  val container=new Item("osoball container", "Container full of Osoballs! This is your lucky day!",player)
  val healer=new Item("ultrahealer 2000","This thing heals all of your Osomons instantly. What a machine! Sad that it only functions once",player)
 val box= new Item("big box of osoballs","A huge box containing atleast 10 balls. probably more!",player)
 val jar = new Item("jar of candies","A jar full of delicious-looking candies.",player)
  val bag= new Item("bag of superpotions","A bag full of the best potions known to man. Lucky you!",player)
  
//niiden lisäys
  route1.addTrainer(kid)
  mysteriousMansion.addTrainer(grill)
  route2.addTrainer(boi)
  mtCoronet.addTrainer(projared)
  osomonLeague.addTrainer(champion)
  //Osomonit
  route1.addOsomon(new Paskamon(route1.tasokkuus+Random.nextInt(3)))
  route1.addOsomon(new Helppomon(route1.tasokkuus+Random.nextInt(3)))
  badlands.addOsomon(new Paskamon(badlands.tasokkuus+Random.nextInt(3)))
  badlands.addOsomon(new Sikamon(badlands.tasokkuus+Random.nextInt(3)))
  badlands.addOsomon(new Helppomon(badlands.tasokkuus+Random.nextInt(3)))
  mysteriousMansion.addOsomon(new Helppomon(mysteriousMansion.tasokkuus+Random.nextInt(3)))
  mysteriousMansion.addOsomon(new Speedymon(mysteriousMansion.tasokkuus+Random.nextInt(3)))
  mysteriousMansion.addOsomon(new Massivemon(mysteriousMansion.tasokkuus+Random.nextInt(3)))
  route2.addOsomon(new Paskamon(route2.tasokkuus+Random.nextInt(3)))
  route2.addOsomon(new Lightningmon(route2.tasokkuus+Random.nextInt(3)))
  route2.addOsomon(new Speedymon(route2.tasokkuus+Random.nextInt(3)))
  route3.addOsomon(new Tehomon(route3.tasokkuus+Random.nextInt(3)))
  route3.addOsomon(new Sikamon(route3.tasokkuus+Random.nextInt(3)))
  route3.addOsomon(new Sharpmon(route3.tasokkuus+Random.nextInt(3)))
  route4.addOsomon(new Tehomon(route4.tasokkuus+Random.nextInt(3)))
  route4.addOsomon(new Speedymon(route4.tasokkuus+Random.nextInt(3)))
  route4.addOsomon(new Paskamon(route4.tasokkuus+Random.nextInt(3)))
  mtCoronet.addOsomon(new Sharpmon(mtCoronet.tasokkuus+Random.nextInt(3)))
  mtCoronet.addOsomon(new Toughmon(mtCoronet.tasokkuus+Random.nextInt(3)))
  mtCoronet.addOsomon(new Rippermon(mtCoronet.tasokkuus+Random.nextInt(3)))
  mtCoronet.addOsomon(new Massivemon(mtCoronet.tasokkuus+Random.nextInt(3)))
  spearPillar.addOsomon(new Godsomon(1))
  //adding the items
  spearPillar.addItem(healer)
  mtCoronet.addItem(bag)
  badlands.addItem(container)
  home.addItem(apple)
  shimonoTown.addItem(box)
  oldaleTown.addItem(jar)
  
  /** The number of turns that have passed since the start of the game. */
  var turnCount = 0
  /** The maximum number of turns that this adventure game allows before time runs out. */
  //ei näitä jaksa liikaa vatvoa
  val timeLimit = 30000


  /** Determines if the adventure is complete, that is, if the player has become the champion. */
  def isComplete = this.player.isChampion

  /** Determines whether the player has won, lost, or quit, thereby ending the game. */
  def isOver = player.hasLost||this.isComplete || this.player.hasQuit || this.turnCount == this.timeLimit

  /** Returns a message that is to be displayed to the player at the beginning of the game. */
  def welcomeMessage = "Welcome to a Osomon adventure! Will you become the Osomon champion?\n Your goal is to battle the current Osomon Champion and win him in a battle!"


  /** Returns a message that is to be displayed to the player at the end of the game. The message
    * will be different depending on whether or not the player has completed their quest. */
  def goodbyeMessage = {
    if (this.isComplete)
      "You are the Osomon champion!"
    else if (player.hasLost)
      "Oh no! You blacked out! Game Over."
    else  // game over due to player quitting
      "Shame :( "
  }


  /** Plays a turn by executing the given in-game command, such as "go west". Returns a textual
    * report of what happened, or an error message if the command was unknown. In the latter
    * case, no turns elapse. */
  def playTurn(command: String) = {
     val action = new Action(command)
    
    val outcomeReport = action.execute(this.player)
    if (outcomeReport.isDefined) {
      this.turnCount += 1
    }
    outcomeReport.getOrElse("Unknown command: \"" + command + "\".")
  }
  
  //muutokset tehty if lauseilla gui ja textui tiedostoihin. niitä voi vielä muutella jos ei toimi näin
def playBattleTurn(command: String) = {
     val action = new Action(command)
    
    val outcomeReport = action.battle(this.player)
    
    outcomeReport.getOrElse("You can't do that in a battle!!")
  }

}

