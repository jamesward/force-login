object ForceLogin extends App {

  import com.sforce.soap.partner.PartnerConnection
  import com.sforce.soap.partner.fault.LoginFault
  import com.sforce.ws.ConnectorConfig

  import scala.io.StdIn
  import scala.util.{Failure, Success, Try}

  print("Salesforce Username: ")
  val username = StdIn.readLine()

  print("Salesforce Password: ")
  val password = System.console().readPassword().mkString

  if (username == "" || password == "") {
    println("Error: You must specify both a username and password")
    sys.exit(1)
  }

  val servicesEndpointSuffix = "services/Soap/u/36.0/"
  val authEndPoint = "https://login.salesforce.com/" + servicesEndpointSuffix

  val configTry = Try {
    val config = new ConnectorConfig() {
      setUsername(username)
      setPassword(password)
      setAuthEndpoint(authEndPoint)
    }

    println("\nLogging in...\n")
    val connection = new PartnerConnection(config)

    connection.getConfig
  }

  configTry match {
    case Success(config) =>
      val instanceUrl = config.getServiceEndpoint.split(servicesEndpointSuffix).head
      val sessionId = config.getSessionId.stripLineEnd

      println(s"InstanceURL: $instanceUrl")
      println(s"SessionId: $sessionId")
    case Failure(e: LoginFault) =>
      println(s"Login Error: ${e.getExceptionMessage}")
    case Failure(e) =>
      println(s"Error: ${e.getMessage}")
  }

}
