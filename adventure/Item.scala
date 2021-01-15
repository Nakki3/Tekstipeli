package o1.adventure

/** The class `Item` represents items in a text adventure game. Each item has a name
  * and a  *  longer description. (In later versions of the adventure game, items may
  * have other features as well.)
  *
  * N.B. It is assumed, but not enforced by this class, that items have unique names.
  * That is, no two items in a game world have the same name.
  *
  * @param name         the item's name
  * @param description  the item's description */
class Item(val name: String, val description: String, kauttaja: Player) {

  /** Returns a short textual representation of the item (its name, that is). */
  override def toString = this.name
  def use= {
    if(this.name == "osoball container") {kauttaja.balls += 10
    "You got 10 balls! You now have " +kauttaja.balls+"." +"\n"+kauttaja.discard(this.name)
    }else if(this.name == "big box of osoballs"){kauttaja.balls += 20
      "Now you have more balls than a sea of balls! Nice!"+"\n"+kauttaja.discard(this.name)
    }else if(this.name == "jar of candies"){kauttaja.osomons.foreach(_.levelUp)
      "All your Osomon leveled up!"
    }else if(this.name == "bag of superpotions"){kauttaja.superPotions += 10
      "You got 10 Superpotions! You now have: " + kauttaja.superPotions
    }else if(this.name == "ultrahealer 2000"){kauttaja.osomons.foreach(x=> x.hp =x.fullHp)
      "Ultrahealer 2000 healed all your Osomons to full hp!!"+"\n"+kauttaja.discard(this.name)
    }else "There is no meaningful way to use this item"
    
  }

}