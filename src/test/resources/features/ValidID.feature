Feature: Payment gateway Positive

  Scenario Outline: Validate valid UUID

    Given  User sets the base API request <URL>
    When User sends the request to this <endpoint> with id been valid
    Then User should be presented with  <status> as a response

    Examples:
      | URL                   | endpoint                                                | status |
      | http://localhost:8088 | /operations/0668e7aa-34aa-43cb-947a-bad3b4f11605/refund | 201    |
