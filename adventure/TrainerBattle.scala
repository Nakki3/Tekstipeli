package o1.adventure
import scala.util.Random
//kuvaa taistelua "ihmisvastustajaa" vastaan.
class TrainerBattle(player:Player,opponent:Opponent) extends Battle {
  
  val championBattle = this.opponent.isChampion
  val expaluku = opponent.osomonit.map(_.level).sum/opponent.osomonit.size
  
     player.currentBattle = Some(this)
     
   def playerAttack = {
      // this.player.useMove(this.opponent.currentOsomon)+"\n"+checkWinCondition + "\n" + opponentTurn
      if(this.player.currentOsomon.speed>=this.opponent.currentOsomon.speed)  this.player.useMove(this.opponent.currentOsomon)+"\n"+checkWinCondition+"\n"+opponentTurn
     else opponentTurn +"\n"+this.player.useMove(this.opponent.currentOsomon)+"\n"+checkWinCondition
       }
     //Kuvaa vastustajan vuoroa. valitsee arpomalla satunnaisesti jotain. Ei mikään vahvin AI.
    def opponentTurn = {
         if(!this.opponent.hasNoOsomonleft){
         val valinta = Random.nextInt(3)+1
         if(valinta ==1){opponentAttack}
         else if(valinta==2){opponent.changeMon}
         else opponent.potion
         }else opponent.name + " took a big f*cking L!"
           }   
  //vastustajan hyökkäys, jota kutsutaan opponentTurnista.
  def opponentAttack = {
       this.opponent.currentOsomon.attack(this.player.currentOsomon)+"\n"+checkWinCondition
       }
     
  def changeOsomon = this.player.nextMon + "\n" + opponentTurn
  
  def catchaa = "You cannot catch other peoples Osomon!"
  //pakottavat vaihdon healtin loppuessa
  def forceChange = this.player.forceChangeMon
  def forceEnemyChange = this.opponent.forceSwitchMon
  //antaa tilannetietoa
  def statusCheck = "Your " + player.currentOsomon.name + " has " + player.currentOsomon.hp + " hp." + "\n"+"And the opponents "+ opponent.currentOsomon.name + " has " + opponent.currentOsomon.hp
       
     //Tarkistaa, mikä taistelun tilanne on, ja onko jompikumpi taho voittanut.
  def checkWinCondition = {
       
       if(!player.currentOsomon.isDead){
         if(opponent.currentOsomon.isDead){
           if(opponent.osomonit.forall(_.isDead)){
        player.currentBattle= None
        opponent.hasLost=true
        if(championBattle){
          player.isChampion = true
          "Wow! That was intense!!"
        }else opponent.name+ " has no Osomon left. "+ "\n" + " You won the battle!" + this.player.currentOsomon.addExp(expaluku*60+Random.nextInt(41)+40)
       }else forceEnemyChange
       
         }else statusCheck
       }else {
         
       if(player.osomons.forall(_.isDead)){
          player.hasLost = true
         "You blacked out!"
       }else {
         //tarkistaa samalla tavalla kuin yläpuolella, että onko vastustajan vaihdettava, vai onko pelaaja voittanut
        def tulos={  if(opponent.currentOsomon.isDead){
          if(opponent.osomonit.forall(_.isDead) ){
            opponent.hasLost=true
            player.currentBattle= None
       if(championBattle){
          player.isChampion = true
          "Wow! That was intense!!"
        }else opponent.name+ " has no Osomon left. "+ "\n" + " You won the battle!" + this.player.currentOsomon.addExp(expaluku*60+Random.nextInt(41)+40)
          }else forceEnemyChange
        }else statusCheck}
         forceChange + "\n"+tulos
       } 
      } 
    }
  //Wincondition päättyy tuohon
  def run = "You cannot run from a trainer battle!!"
     
  def superPotion ={ if(player.superPotions>0){ 
       player.superPotions-=1
       this.player.currentOsomon.heal(150)+"\n"+opponentTurn 
     }else "You don't have any superPotions left!"}
     
  def potion ={ if(player.potions>0){
       player.potions-=1
       this.player.currentOsomon.heal(70)+ "\n"+opponentTurn
     }else "You don't have any potions left!"}
  def help = {
       "Here are the commands that are used in a battle: \n attack-> attacks the enemy osomon. \nswitch-> switches to your next osomon.\nrun-> runs from the battle(only in encounters)\ncatch-> tries to catch the opposing osomon. doesn't work for trainer battles.\npotion-> tries to heal your current osomon with a potion\nsuperpotion-> same as potion,except better.\n"
       
     }
}