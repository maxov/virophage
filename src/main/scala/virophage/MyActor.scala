package virophage

import akka.actor.Actor
import virophage.MyActor.{Url, Ping, DoStop}
import virophage.game.{EventDispatcher, Scheduler}
import virophage.game.Scheduler.Queue

object MyActor {

  case class DoStop()
  case class Ping(obj: EventDispatcher = null)
  case class Url()

}

class MyActor extends Actor  {

  def receive = {
    case DoStop() => context.stop(self)
    case Ping(obj: EventDispatcher) => {
      println("pong")
      if(obj != null) {
        obj.events(0)._2.onEvent(76)
      }
    }
    case Url() => println(self.path)
  }

}
