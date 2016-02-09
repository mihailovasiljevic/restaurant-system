<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

  <body>
      <c:if test="${sessionScope.user != null}" >
        Gost je prijavljen!
      </c:if>
      <c:if test="${sessionScope.user == null}" >
        Gost NIJE prijavljen!
      </c:if>
  </body>

</html>
