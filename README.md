## Spring Boot Unit Testing Service Layer | JUnit & Mockito

Dans ce repo, nous allons apprendre à effectuer des tests unitaires de la couche de service de Spring Boot à l'aide de
JUnit 5 et du framework Mockito.

Afin de tester les composants de la couche Service, nous devons simuler les composants de la couche Repository à l'aide
du framework Mockito. Nous n'avons pas besoin d'utiliser une base de données pour les tests unitaires.

### Technologies
---

- Java 17
- Spring Boot 3
- Lombok
- JUnit 5
- Mockito
- AssertJ Library
- JsonPath
- Maven

### Quelques des points à noter
---

- Spring boot fournit une dépendance `spring-boot-starter-test`  pour les tests unitaires et les tests d'intégration de
  l'application Spring boot
- Notez que nous utilisons la méthode `assertThat()` pour affirmer les conditions à l'aide de la bibliothèque AssertJ
- L'annotation Mockito `@Mock` est utile lorsque nous voulons utiliser l'objet simulé à plusieurs endroits.
- Lorsque nous voulons injecter un objet moqué dans un autre objet moqué, nous pouvons utiliser
  l'annotation `@InjectMocks`. `@InjectMock` crée l'objet mock de la classe et y injecte les mocks marqués avec les
  annotations `@Mock`.

### Exécutez les tests unitaires
---

- Exécuter le test : `mvn test`

### Ressources utiles
---

- [JUnit Framework Best Practices](https://www.javaguides.net/2018/08/junit-framework-best-practices.html)
- [Best Practices for Unit Testing in Java](https://www.developer.com/java/best-practices-unit-testing-java/)