<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

  <body>
      <c:if test="${sessionScope.user != null}" >
        Menadzer restorana je prijavljen!
      </c:if>

      <c:if test="${sessionScope.user == null}" >
        Menadzer restorana NIJE prijavljen!
      </c:if>
  </body>

</html>
