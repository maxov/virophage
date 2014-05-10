package virophage

import akka.actor.{Identify, Props, ActorSystem}
import virophage.MyActor.{Url, DoStop, Ping}
import com.typesafe.config.ConfigFactory

object ScalaStart {

  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("myactors", ConfigFactory.load().getConfig("myactors"))
    val myactor = sys.actorOf(Props[MyActor], "MyActor")
    println("hi")
    myactor ! Ping()
    myactor ! Url()
    val selection = sys.actorSelection("akka.tcp://myactors@127.0.0.1:1347/user/MyActor")
    selection ! Ping()
    print(selection.pathString)
  }

}
