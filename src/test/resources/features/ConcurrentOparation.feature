Feature: Payment gateway concurrent

  Scenario Outline: Concurrent operation on same transect id

    Given  User sets the base API to request this <URL>
    When User sends the request to this <URL> <endpoint> with UUid that is valid concurrently
    Then  One of the request that was sent concurrently should return  the given status code <codestatus>
    Then User should be presented with  <statuscode> as response on one of the transactions


    Examples:
      | URL                   | endpoint                                                | statuscode | codestatus |
      | http://localhost:8088 | /operations/501b6daa-d586-4afa-b0c5-479b0abd89cd/refund | 423        | 201        |
