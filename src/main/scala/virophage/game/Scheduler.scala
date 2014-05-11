package virophage.game

import akka.actor.{FSM, Actor}
import scala.concurrent.duration._
import virophage.game.Scheduler.Queue
import scala.concurrent.ExecutionContext

object Scheduler {

  case class Queue(action: Runnable, time: FiniteDuration)

}

class Scheduler extends Actor {


  def receive = {
    case Queue(action, time) => context.system.scheduler.schedule(0 seconds, time, action)(ExecutionContext.global)
  }

}
