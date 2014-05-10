package virophage

import akka.actor.{Props, ActorSystem}
import virophage.MyActor.{Url, DoStop, Ping}

object ScalaStart {

  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("myactors")
    val myactor = sys.actorOf(Props[MyActor], "MyActor")
    println("hi")
    myactor ! Ping()
    myactor ! Url()
    val selection = sys.actorSelection("akka.tcp://myactors@127.0.0.1:1347/user/MyActor")
    selection ! Ping()
    selection ! Url()


    sys.awaitTermination()
  }

}
