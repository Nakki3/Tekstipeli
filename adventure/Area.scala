package o1.adventure

import scala.collection.mutable.Map
import scala.collection.mutable.Buffer

/** The class `Area` represents locations in a text adventure game world. A game world
  * consists of areas. In general, an "area" can be pretty much anything: a room, a building,
  * an acre of forest, or something completely different. What different areas have in
  * common is that players can be located in them and that they can have exits leading to
  * other, neighboring areas. An area also has a name and a description.
  * @param name         the name of the area
  * @param description  a basic description of the area (typically not including information about items) */
class Area(var name: String, var description: String, val tasokkuus: Int) {

  private val neighbors = Map[String, Area]()
  private val items = Map[String, Item]()
  val osomons = Buffer[ Osomon]()
  def hasTrainerBattle = trainer != None
  /** Returns the area that can be reached from this area by moving in the given direction. The result
    * is returned in an `Option`; `None` is returned if there is no exit in the given direction. */
  def neighbor(direction: String) = this.neighbors.get(direction)

  def hasOsomon = this.tasokkuus>0
  /** Adds an exit from this area to the given area. The neighboring area is reached by moving in
    * the specified direction from this area. */
  def setNeighbor(direction: String, neighbor: Area) = {
    this.neighbors += direction -> neighbor
  }
  def addItem(item:Item):Unit = {
  this.items += item.name -> item
  
  }
  def addOsomon(osomon:Osomon) = osomons += osomon
  
  var trainer:Option[Opponent] = None
  
  def addTrainer(opponent:Opponent) = this.trainer = Some(opponent)
  
  def removeItem(itemName:String) = {
    this.items.remove(itemName)
  }
  def contains(itemName: String) = this.items.contains(itemName)
  /** Adds exits from this area to the given areas. Calling this method is equivalent to calling
    * the `setNeighbor` method on each of the given direction--area pairs.
    * @param exits  contains pairs consisting of a direction and the neighboring area in that direction
    * @see [[setNeighbor]] */
  def setNeighbors(exits: Vector[(String, Area)]) = {
    this.neighbors ++= exits
  }
  

  /** Returns a multi-line description of the area as a player sees it. This includes a basic
    * description of the area as well as information about exits and items. The return
    * value has the form "DESCRIPTION\n\nExits available: DIRECTIONS SEPARATED BY SPACES".
    * The directions are listed in an arbitrary order. */
  def fullDescription = {
    val exitList = "\n\nExits available: " + this.neighbors.keys.mkString(" ")
    val itemList = "\nYou see here: " + this.items.keys.mkString(" ")
    val traineri = trainer match{
      case Some(traineri) => traineri.name
      case None => "No one"
    }
    if(this.items.isEmpty)this.description + exitList+"\nTrainers in the area: " +traineri
    else this.description + itemList + exitList+"\nTrainers in the area: " +traineri
  }


  /** Returns a single-line description of the area for debugging purposes. */
  override def toString = this.name + ": " + this.description.replaceAll("\n", " ").take(150)



}
