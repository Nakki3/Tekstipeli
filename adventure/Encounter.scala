package o1.adventure
import scala.util.Random 

class Encounter(player: Player, wild: Osomon)extends Battle {
    //varmistu siitä, että jokaikinen metodi palauttaa tekstiä. muuten TextUi tai textGui suuttuu
     player.currentBattle = Some(this)
     
  def playerAttack = {
       
     if(player.currentOsomon.speed>=wild.speed)  this.player.useMove(wild)+"\n"+checkWinCondition+"\n"+opponentTurn
     else opponentTurn +"\n"+this.player.useMove(wild)+"\n"+checkWinCondition
     }
  def opponentTurn = {
       if(!wild.isDead)opponentAttack
       else{ 
         player.location.osomons.foreach(x=>x.hp =x.fullHp)
         "Wild Osomon is f*cking dead!"
         }
     }
     
  def opponentAttack = {
       wild.attack(this.player.currentOsomon)+"\n"+checkWinCondition
     }
     
  def changeOsomon = this.player.nextMon +"\n"+opponentAttack
  
  def catchaa ={ 
       def mahdollinenHyokkaus = if(!this.wild.isCaptured)opponentAttack else ""
       this.player.nappaa(wild) +"\n"+ mahdollinenHyokkaus}
     
     //pakottaa vaihtamaan
  def forceChange = this.player.forceChangeMon
  //kertoo tilannetietoa
 def statusCheck = "Your " + player.currentOsomon.name + " has " + player.currentOsomon.hp + " hp." + "\n"+"And the wild "+ wild.name + " has " + wild.hp
     
 def checkWinCondition = {
       
       if(!player.currentOsomon.isDead){
         if(wild.isDead){
        player.currentBattle= None
       "You won the battle! " + this.player.currentOsomon.addExp(wild.level*50+Random.nextInt(31)+30)
         }else statusCheck
       }else {
         
       if(player.osomons.forall(_.isDead)){
          player.hasLost = true
          player.currentBattle = None
         "You blacked out!"
       }else {
         
        def tulos={  if(wild.isDead){
            player.currentBattle= None
       "You won the battle! " + this.player.currentOsomon.addExp(wild.level*50+Random.nextInt(31)+30)
         }else statusCheck}
         forceChange + "\n"+tulos
       } 
      } 
    }
  //wincondition päättyy tähän
   def run ={ this.player.currentBattle = None
       "You ran from the battle!"
     }
     
     def superPotion ={ if(player.superPotions>0){ 
       player.superPotions-=1
       this.player.currentOsomon.heal(150)+"\n"+opponentAttack 
     }else "You don't have any superPotions left!"}
     
     def potion ={ if(player.potions>0){
       player.potions-=1
       this.player.currentOsomon.heal(70)+ "\n"+opponentAttack 
     }else "You don't have any potions left!"}
     
     def help = {
       "Here are the commands that are used in a battle: \n attack-> attacks the enemy osomon. \nswitch-> switches to your next osomon.\nrun-> runs from the battle(only in encounters)\ncatch-> tries to catch the opposing osomon. doesn't work for trainer battles.\npotion-> tries to heal your current osomon with a potion\nsuperpotion-> same as potion,except better.\n"
       
     }
     
}