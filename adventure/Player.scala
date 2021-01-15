package o1.adventure

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer
import scala.util.Random

/** A `Player` object represents a player character controlled by the real-life user of the program.
  *
  * A player object's state is mutable: the player's location and possessions can change, for instance.
  *
  * @param startingArea  the initial location of the player */
class Player(startingArea: Area) {

  private var currentLocation = startingArea        // gatherer: changes in relation to the previous location
  private var quitCommandGiven = false              // one-way flag
  private var invi = Map[String, Item]()
  /** Determines if the player has indicated a desire to quit the game. */
  def hasQuit = this.quitCommandGiven
  var hasLost = false
  var isChampion = false
  val osomons = Buffer[Osomon](new Startmon(4))
  //Jotta aloitusosomonit tunnistettaisiin napatuiksi.
  osomons.foreach(_.isCaptured = true)
  
  var osoNumber = 0
  var balls = 5
  var potions = 2
  var superPotions = 1
   def isInBattle = currentBattle != None
  
  var currentBattle: Option[Battle] = None
  var currentOsomon = osomons(osoNumber)
  
  //Pitäisikö näiden allaolevien palauttaa tekstiä?
/*  def startEncounter = {
    this.currentBattle = Some(new Encounter(this,new Paskamon(3)))
    "Battle has begun! I mean test"
  }*/
 /* def startTrainerBattle = {
    val vastustaja = new Opponent("Champion", Buffer(new Paskamon(3),new Startmon(2),new Sikamon(3) ))
    this.currentBattle = Some(new TrainerBattle(this,vastustaja))
    "A battle with "+vastustaja.name + " has begun!"
  }*/
  
  //Apukomento
  def help = {
    "Overworld commands are the following: \n go direction -> takes you to that direction, if possible.  \nget itemname -> tries to get the item described. use itemname-> tries to use the item \ninventory-> tells you your current inventory. quit-> quits the game. \nheal-> if you happen to be in a town, heals all your osomon\nchallenge-> if there are any trainers in the area, challenges them to a battle. \n team-> tells you your teams status.\nballs-> Tells you the amount of osoballs you have.\ndrop itemname-> tries to drop the item.  examine itemname-> examines the item "
    
  }
  
  //Haastekomento
  def challenge = {
    if(location.trainer!=None){
      val vastustaja = location.trainer.get
      if(vastustaja.hasLost)"You have already beaten this guy."
      else {this.currentBattle = Some(new TrainerBattle(this,vastustaja))
      "You challenged the " +vastustaja.name +"\n"+currentBattle.get.statusCheck
      }
    }else "There is no Osomon trainer to challenge here!"
    
  }
  
  def addOsomon(moni:Osomon) = {osomons += moni
    moni.name + " was added to your team!"
  }
  //Kertoo perustiedot tiimistä
  def tellInfo = {
    val hpt = this.osomons.map(_.hp)
    val pakkaus = osomons.zip(hpt)
    "Your Osomons are: "+ pakkaus.mkString(",")
  }
  
  def osomonExp = {
    "Your Osomons have exp's of: " + osomons.map(_.exp).mkString(",")
  }
  //Catch mechanic
  def nappaa(target:Osomon) = {
    if(this.balls>0){
      this.balls -=1
    if( (target.hp*100.0/target.fullHp) < Random.nextInt(101) ){
      this.currentBattle = None
      target.isCaptured = true
      osomons += target
      "Wild "+ target.name +" captured!"+ "\n" + "The encounter has ended."
    }else "Aww it appeared to be caught!"
    
    }else "You don't have any Osoballs left!"
  }
  //Pakottaa vaihdon
  def forceChangeMon:String = {
    val vanha = currentOsomon
    do{
      if (osoNumber<osomons.size-1){
      osoNumber+=1
      }else osoNumber = 0
      currentOsomon=osomons(osoNumber)
    }while(currentOsomon.isDead)
    vanha.name +" fainted because it had only "+ vanha.hp+ " hp. So you had to switch to " + currentOsomon.name
  }
  //Vaihtaa Osomonia
  def nextMon = {
    //Pitää olla vähintään kaksi elossaolevaa osomonia
    if(osomons.filter(!_.isDead).size>1){
    do{
      if (osoNumber<osomons.size-1){
      osoNumber+=1
      }else osoNumber = 0
      currentOsomon=osomons(osoNumber)
    }while(currentOsomon.isDead)
     "Go, "+ currentOsomon.name +"!"
    }else "You have no other Osomon left!"
  
  }
  
  def useMove(target: Osomon) = {
    currentOsomon.attack(target)
  }
  
  
  /** Returns the current location of the player. */
  def location = this.currentLocation
  
  def inventory:String = {
    if(!invi.isEmpty) {
      "You are carrying: \n"+ this.invi.keys.mkString("\n")
    }
    else "You are empty-handed."
  }
  def examine(itemName: String): String={
    this.invi.get(itemName) match{
      case Some(tavara) => "You look closely at the "+itemName + ".\n"+ tavara.description
      case None => "If you want to examine something, you need to pick it up first."
      }
    }
  def has(itemName: String) = this.invi.contains(itemName)
  
  
  /** Attempts to move the player in the given direction. This is successful if there
    * is an exit from the player's current location towards the direction name. Returns
    * a description of the result: "You go DIRECTION." or "You can't go DIRECTION." */
  def go(direction: String) = {
    val destination = this.location.neighbor(direction)
    if (!destination.isDefined){
    "You can't go " + direction + "."
    
    } else if(this.location.hasOsomon&& !this.location.osomons.isEmpty) {
       if(Random.nextBoolean){
         val randomOsomoni = this.location.osomons(Random.nextInt(this.location.osomons.size))
         this.currentBattle =Some(new Encounter(this, randomOsomoni )) 
         
         "A wild "+randomOsomoni.name +" appeared! What do you do?"+"\n"+currentBattle.get.statusCheck
       }else{this.currentLocation = destination.getOrElse(this.currentLocation)
      "You go " + direction + "."
       }   
    }else {this.currentLocation = destination.getOrElse(this.currentLocation)
      "You go " + direction + "."
    }   
  }
  //Itemin käyttämistä varten
  def useItem(itemName:String) = {
    invi.get(itemName) match{
      case Some(tavara) => tavara.use
      case None => "No such item exists in you inventory."
    }
    
  }
  //Itemin nappaamista varten
  def get(itemName:String) = {
    this.currentLocation.removeItem(itemName) match{
      case Some(tavara) => this.invi += itemName->tavara
        "You pick up the "+ itemName
      case None => "There is no "+itemName +" here to pick up."
      
    } 
  }
   def discard(itemName:String) = {
    this.invi.remove(itemName) match{
      case Some(tavara) =>   "You discarded the " + tavara.name
      case None => "You cannot discard something you don't have."
    }
    
  }
   //Osomonien parantaminen tapahtuu tällä
   def heal = {
     if(this.currentLocation.hasOsomon) "There is no carecenter or hospital here. You have to go somewhere else to heal your Osomons"
     else {this.osomons.foreach(x=> x.hp =x.fullHp)
       "Your Osomons are now fully healed!"
     }
   }
   
   
  def drop(itemName:String) = {
    this.invi.remove(itemName) match{
      case Some(tavara) => this.currentLocation.addItem(tavara)
      "You drop the " + tavara.name
      case None => "You don't have that!"
    }
    
  }
  

  /** Signals that the player wants to quit the game. Returns a description of what happened within
    * the game as a result (which is the empty string, in this case). */
  def quit() = {
    this.quitCommandGiven = true
    ""
  }
  //Cheat code
def huijaa = {
  osomons += new Godsomon(10)
    currentOsomon = osomons(1)
  "aijai ei saa huijata."
}

  /** Returns a brief description of the player's state, for debugging purposes. */
  override def toString = "Now at: " + this.location.name


}


