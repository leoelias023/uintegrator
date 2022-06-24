# Unity Integrator

## Configurações globais

### Repositório maven

Para obter as dependências do projeto, será necessário configurar usuário e senha do repositório maven do artifactory DBA Online.

Declare as seguintes variáveis de ambiente:

```
MAVEN_USERNAME=USUARIO_AQUI
MAVEN_PASSWORD=SENHA_AQUI
```

Outra forma de realizar a configuração é através das properties, na raiz do projeto crie um arquivo nomeado `gradle.properties` e configure:

```properties
mavenUsername=USUARIO_AQUI
mavenPassword=SENHA_AQUI
```