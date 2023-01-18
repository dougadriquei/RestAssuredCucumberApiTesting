 Feature: UC03 - GetAccounts
  It must be possible to get the account data from the account id

   #TODO - Possivel problema de desempenho
  Scenario: UC03.01 - Verify an Applicant's account
    Given I perform GET operation for "/accounts/62f87af90ba501353df77b95"
    Then I should see the body has name as "Candidato"
    And I also should see the body has amount as 45

  Scenario: UC03.02 -Verify a Luke's account
    Given I perform GET operation for "/accounts/630932fc327fb162f0d8e72c"
    Then I should see the body has name as "Luke Skywalkers"
    And I also should see the body has amount as 60

  Scenario: UC03.03 - Verify a Darth's account
    Given I perform GET operation for "/accounts/630933566e9f1d5128cf4c00"
    Then I should see the body has name as "Darth Vader"
    And I also should see the body has amount as 0

  #TODO - Ajustar mensagem para mostrar "account_not_found" ao invés de "qrcode_not_found"
  Scenario: UC03.04 -Verify an invalid account
    Given I perform GET operation for "/accounts/630933566e9@f1d51#28cf4c00.2.2"
    Then I need see the body has message error as "account_not_found" with status code as 400

  #TODO - Via postman nao retorna body no erro 400, no projeto de teste eu tratei via try catch quando uso o Request.get(new URI(url));
  Scenario: UC03.05 -Verify a bad request
    Given I perform GET operation for "/accounts/%%%%%%%%"
    Then I should see bad request

