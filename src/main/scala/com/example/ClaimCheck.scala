package com.example

import java.util.UUID

import akka.actor._

object ClaimCheckDriver extends CompletableApp(5) {
}

case class ClaimCheck() {
  val number = UUID.randomUUID().toString

  override def toString = {
    "ClaimCheck(" + number + ")"
  }
}