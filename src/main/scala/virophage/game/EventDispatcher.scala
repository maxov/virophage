package virophage.game

object EventDispatcher {
  case class X(var v: String)
}

class EventDispatcher {

  var events = List[(String, EventListener[Any])]()

  def dispatch[E](name: String, evt: E) = {
    events foreach {
      case (n: String, listener: EventListener[E]) =>
        if(n == name) listener.onEvent(evt)
    }
  }

  def register(name: String, listener: EventListener[Any]) = {
    events = (name, listener) :: events
  }

}
