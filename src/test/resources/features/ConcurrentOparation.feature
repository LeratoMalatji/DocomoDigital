Feature: Payment gateway concurrent

  Scenario Outline: Concurrent operation on same transect id

    Given  User sets the base API to request this <URL>
    When User sends the request to this <endpoint> with UUid that is valid concurrently
    Then User should be presented with  <statuscode> as response on one of the transactions


    Examples:
      | URL                   | endpoint                                               | statuscode |
      | http://localhost:8088 | /operations/501b6daa-d586-4afa-b0c5-479b0abd89cd/refund | 423        |
