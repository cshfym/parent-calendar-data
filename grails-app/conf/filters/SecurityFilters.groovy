package filters

class SecurityFilters {

  def grailsApplication

  def filters = {

    def authToken = grailsApplication.config.authentication.token

    all(controller: '*', action: '*') {
      before = {
        Boolean authenticated = Boolean.TRUE
        if(!request.getHeader("Authorization") || !authToken.equals(request.getHeader("Authorization"))) {
          authenticated = Boolean.FALSE
          response.setStatus(401)
        }
        return authenticated
      }
      after = { Map model ->

      }
      afterView = { Exception e ->

      }
    }
  }
}
