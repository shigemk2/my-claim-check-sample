package com.example

import java.util.UUID

import akka.actor._

case class Part(name: String)

case class CompositeMessage(id: String, part1: Part, part2: Part, part3: Part)

case class ProcessStep(id: String, claimCheck: ClaimCheck)

case class StepCompleted(id: String, claimCheck: ClaimCheck, stepName: String)

object ClaimCheckDriver extends CompletableApp(5) {
}

case class ClaimCheck() {
  val number = UUID.randomUUID().toString

  override def toString = {
    "ClaimCheck(" + number + ")"
  }
}

case class CheckedItem(claimCheck: ClaimCheck, businessId: String, parts: Map[String, Any])
case class CheckedPart(claimCheck: ClaimCheck, part: Any)

class ItemChecker {
  val checkedItems = scala.collection.mutable.Map[ClaimCheck, CheckedItem]()

  def checkedItemFor(businessId: String, parts: Map[String, Any]) = {
    CheckedItem(ClaimCheck(), businessId, parts)
  }

  def checkItem(item: CheckedItem) = {
    checkedItems.update(item.claimCheck, item)
  }

  def claimItem(claimCheck: ClaimCheck) = {
    checkedItems(claimCheck)
  }

  def claimPart(claimCheck: ClaimCheck, partName: String): CheckedPart = {
    val checkedItem = checkedItems(claimCheck)

    CheckedPart(claimCheck, checkedItem.parts(partName))
  }

  def removeItem(claimCheck: ClaimCheck) = {
    if (checkedItems.contains(claimCheck)) {
      checkedItems.remove(claimCheck)
    }
  }
}