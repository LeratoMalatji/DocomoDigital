Feature: Payment gateway Negative

  Scenario Outline: Validate Invalid UUID

    Given  User sets the base API to request  <URL>
    When User sends the request to this <endpoint> with UUid been Invalid
    Then User should be presented with  <statuscode> as response

    Examples:
      | URL                   | endpoint                                               | statuscode |
      | http://localhost:8088 | /operations/0668e7aa-433333cb-947a-bad3b4f11605/refund | 400        |
