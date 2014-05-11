package virophage

import akka.actor.{Identify, Props, ActorSystem}
import virophage.MyActor.{Url, DoStop, Ping}
import com.typesafe.config.ConfigFactory
import virophage.game.{EventListener, EventDispatcher, Scheduler}
import virophage.game.Scheduler.Queue
import scala.concurrent.duration._

object ScalaStart {

  def main(args: Array[String]): Unit = {
    val sys = ActorSystem("myactors", ConfigFactory.load().getConfig("myactors"))
    val myactor = sys.actorOf(Props[MyActor], "MyActor")
    println("hi")
    myactor ! Ping()
    myactor ! Url()
    val selection = sys.actorSelection("akka.tcp://myactors@127.0.0.1:1347/user/MyActor")
    val evt = new EventDispatcher
    evt.register("a", new EventListener[Any] {
      override def onEvent(evt: Any): Unit = {
        val e = evt.asInstanceOf[Int]
        println("5")
        println(e)
      }
    })
    selection ! Ping(evt)
    print(selection.pathString)
  }

}
