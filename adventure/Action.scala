package o1.adventure


/** The class `Action` represents actions that a player may take in a text adventure game.
  * `Action` objects are constructed on the basis of textual commands and are, in effect,
  * parsers for such commands. An action object is immutable after creation.
  * @param input  a textual in-game command such as "go east" or "rest" */
class Action(input: String) {

  private val commandText = input.trim.toLowerCase
  private val verb        = commandText.takeWhile( _ != ' ' )
  private val modifiers   = commandText.drop(verb.length).trim


  /** Causes the given player to take the action represented by this object, assuming
    * that the command was understood. Returns a description of what happened as a result
    * of the action (such as "You go west."). The description is returned in an `Option`
    * wrapper; if the command was not recognized, `None` is returned. */
  def execute(actor: Player) = this.verb match {
    case "go"    => Some(actor.go(this.modifiers))
    
    case "quit"  => Some(actor.quit())
    case "get"   => Some(actor.get(this.modifiers))
    case "inventory" =>Some(actor.inventory)
    case "drop" => Some(actor.drop(this.modifiers))
    case "examine" => Some(actor.examine(this.modifiers))
   // case "encounter" => Some(actor.startEncounter)
   // case "battle" => Some(actor.startTrainerBattle)
    case "team" => Some(actor.tellInfo)
    case "challenge" => Some(actor.challenge)
    case "exp" => Some(actor.osomonExp)
    case "heal" => Some(actor.heal)
    case "balls" => Some("You have: "+actor.balls+" Osoballs left.")
    case "use" => Some(actor.useItem(this.modifiers))
    case "help" => Some(actor.help)
    case "cheat" => Some(actor.huijaa)
    case other   => None
  }

   def battle(pelaaja: Player)={
     val matsi = pelaaja.currentBattle.get
     this.verb match{
     case "attack" => Some(matsi.playerAttack)  
     case "run" => Some(matsi.run)
     case "potion" => Some(matsi.potion)
     case "superpotion" => Some(matsi.superPotion)
     case "catch" => Some(matsi.catchaa)
     case "switch" => Some(matsi.changeOsomon)
     case "help"=> Some(matsi.help)
   case other => None 
   }
   }
  /** Returns a textual description of the action object, for debugging purposes. */
  override def toString = this.verb + " (modifiers: " + this.modifiers + ")"


}

