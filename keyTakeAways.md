# Key take aways from this project

- While having both @Autowired and @Value annotations, the value for @Value annotated instance variable will be null (as it is injected after the object creation). To handle this case, we can use @AllArgsContructor where all the values will be value and dependencies are injected while object creation only.
- For Spring Security:
  - Spring enables default security for the application as and when the dependency for spring security is added into the pom.xml file. It sets the default user (user) and a random generated password printed on the console.
  - While configuring for HttpBasic authentication, the username and password needs to pass for each request (as there is no token based authentication). There is no login method. Spring security automatically authenticates for the user using SecurityFilterChain and UserDetails from UserDetailsService.
  - While using Jwt based authentication, a seperate AuthenticationManager needs to be configured which will be used to set (in JwtFilter) and get (wherever required) the Authentication object.
