/*
 * Copyright (C) 2009-2016 Typesafe Inc. <http://www.typesafe.com>
 */
package scalaguide.tests.scalatest.oneserverpersuite

import play.api.test._
import org.scalatestplus.play._
import play.api.test.Helpers.{GET => GET_REQUEST, _}
import play.api.libs.ws._
import play.api.mvc._
import Results._
import play.api.inject.guice._
import play.api.routing._
import play.api.routing.sird._
import play.api.cache.EhCacheModule

// #scalafunctionaltest-oneserverpersuite
class ExampleSpec extends PlaySpec with OneServerPerSuite {

  // Override app if you need an Application with other than
  // default parameters.
  implicit override lazy val app =
    new GuiceApplicationBuilder().disable[EhCacheModule].additionalRouter(Router.from {
      case GET(p"/") => Action { Ok("ok") }
    }).build()

  "test server logic" in {
    val wsClient = app.injector.instanceOf[WSClient]
    val myPublicAddress =  s"localhost:$port"
    val testPaymentGatewayURL = s"http://$myPublicAddress"
    // The test payment gateway requires a callback to this server before it returns a result...
    val callbackURL = s"http://$myPublicAddress/callback"
    // await is from play.api.test.FutureAwaits
    val response = await(wsClient.url(testPaymentGatewayURL).withQueryString("callbackURL" -> callbackURL).get())

    response.status mustBe (OK)
  }
}
// #scalafunctionaltest-oneserverpersuite
