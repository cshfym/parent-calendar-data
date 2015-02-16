package filters

class SecurityFilters {

    private String AUTH_TOKEN = (System.getenv("AUTH_TOKEN")) ?: "c94c020f-517e-4afd-bab5-8be97a4e8bc7"

    def filters = {
        all(controller: '*', action: '*') {
            before = {
                Boolean authenticated = Boolean.TRUE
                if(!request.getHeader("Authorization") || !AUTH_TOKEN.equals(request.getHeader("Authorization"))) {
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
