package o1.adventure
import scala.util.Random
import scala.math._
//Uusien Osomonien lisääminen on yritetty tehdä mahdollisimman helpoksi.
class Osomon(val baseHp:Int,val baseAtk:Int,val baseDef:Int,val baseSpeed:Int, levu:Int,val name:String) {
 var level = levu
 var exp = 0
 def expCap = this.level*100
 var hp = fullHp
 var isCaptured = false
 
 //basestatsit ovat defejä helpon päivittämisen vuoksi.
 def fullHp = baseHp*(1+level/10.0) 
 def atk =baseAtk*(1+level/10.0)
 def defe = baseDef*(1+level/10.0)
 def speed = baseSpeed*(1+level/10.0)
 
 def isDead = hp<=0
 
 def levelUp = {
   this.level = this.level +1
   this.name + " grew to level: " + level
 }
 
 def addExp(amount: Int) = {
   var jaljella = exp + amount
   var levelUpTimes = 0
   while(jaljella>=expCap){
     jaljella = jaljella-expCap
     this.levelUp 
     levelUpTimes +=1
   }
   exp =jaljella
   this.name +" leveled up " + levelUpTimes + " times!"
 }
 
 
 
 def heal(amount: Int) ={ 
   if(!this.isDead){
     val lisays = min(amount, fullHp-hp)
     def modifier = if(this.isCaptured) "Your " else "Opponents "
     hp  +=lisays
    modifier +this.name + " healed for " + lisays + " hp, and it now has: "+ this.hp+ " left."
   }else "You can't heal dead Osomon"
 }
 //Hyökkäysvoima lasketaan yksinkertaisesti kaavalla 100*hyökkäys/vastustajan defense
 def attack(opponent:Osomon) = if(!this.isDead&&opponent.hp>0){
   opponent.hp = max(opponent.hp- 100*this.atk/(opponent.defe), 0 )
   def muutos = if(opponent.isCaptured) "Opposing " +this.name+ " attacked your " +opponent.name else  "Your "+ this.name+ " attacked enemy "+opponent.name + "."
   muutos
 }else "Attack failed"
 
  override def toString = "level " +level+ " " +name
 
}