package o1.adventure
import scala.collection.mutable.Buffer
import scala.util.Random

class Opponent(val name:String,val osomonit:Buffer[Osomon]) {
  var osoNumber = 0
  var currentOsomon = osomonit.head
  var hasLost = false
  def hasNoOsomonleft = this.osomonit.forall(_.isDead)
  def firstOsomon:Osomon = osomonit.head
  def outOfOsomon = osomonit.isEmpty
  def removeOsomon(moni:Osomon) = osomonit.remove(osomonit.indexOf(moni))
  def isChampion = name=="Champion"
  def useMove(target:Osomon) = {
    currentOsomon.attack(target) + this.currentOsomon.name+ " used it's special move against your " +target.name+ "!"
  }
  
  def forceSwitchMon:String = {
    val vanha = currentOsomon
    do{
      if (osoNumber<osomonit.size-1){
      osoNumber+=1
      }else osoNumber = 0
      currentOsomon=osomonit(osoNumber)
    }while(currentOsomon.isDead)
    this.name+"'s " + vanha.name +" fainted because it had only "+ vanha.hp+ " hp. So "+ this.name +" had to switch to " + currentOsomon.name
  }
  
  def changeMon = {
    //Pitää olla vähintään kaksi elossaolevaa osomonia
    if(osomonit.filter(!_.isDead).size>1){
    do{
      if (osoNumber<osomonit.size-1){
      osoNumber+=1
      }else osoNumber = 0
      currentOsomon=osomonit(osoNumber)
    }while(currentOsomon.isDead)
    this.name+ " switched to "+ currentOsomon.name +"!"
    }else this.name + " tried to change Osomon for some reason, but he only has one!"
  
  }
  def potion = if(isChampion) currentOsomon.heal(150) else currentOsomon.heal(70)
  
}