package virophage

import akka.actor.Actor
import virophage.MyActor.{Url, Ping, DoStop}

object MyActor {

  case class DoStop()
  case class Ping()
  case class Url()

}

class MyActor extends Actor  {

  def receive = {
    case DoStop() => context.stop(self)
    case Ping() => println("pong")
    case Url() => println(self.path)
  }

}
