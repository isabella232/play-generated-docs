import play.api.ApplicationLoader.Context
import play.api._
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.Results._
import play.api.mvc._
import play.api.routing.Router
import play.api.routing.sird._
import scala.concurrent.Future
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.inject.bind
import router.RoutingDslBuilder

//#load
class AppLoader extends ApplicationLoader {
  def load(context: Context) = {
    new MyComponents(context).application
  }
}

class MyComponents(context: Context) extends BuiltInComponentsFromContext(context) {
  lazy val router = Router.from {
       RoutingDslBuilder.getRouter().routes
  }
}
//#load
